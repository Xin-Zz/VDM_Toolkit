package plugins;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.fujitsu.vdmj.in.modules.INModuleList;
import com.fujitsu.vdmj.lex.LexException;
import com.fujitsu.vdmj.lex.LexLocation;
import com.fujitsu.vdmj.mapper.ClassMapper;
import com.fujitsu.vdmj.messages.Console;
import com.fujitsu.vdmj.messages.InternalException;
import com.fujitsu.vdmj.messages.VDMErrorsException;
import com.fujitsu.vdmj.pog.ProofObligation;
import com.fujitsu.vdmj.pog.ProofObligationList;
import com.fujitsu.vdmj.runtime.Interpreter;
import com.fujitsu.vdmj.runtime.ModuleInterpreter;
import com.fujitsu.vdmj.syntax.ParserException;
import com.fujitsu.vdmj.tc.expressions.TCExpression;
import com.fujitsu.vdmj.tc.modules.TCModuleList;
import com.fujitsu.vdmj.typechecker.TypeCheckException;

import vdm2isa.lex.IsaToken;
import vdm2isa.lex.TRIsaVDMCommentList;
import vdm2isa.messages.IsaErrorMessage;
import vdm2isa.messages.IsaWarningMessage;
import vdm2isa.pog.IsaProofObligationList;
import vdm2isa.tr.TRNode;
import vdm2isa.tr.definitions.TRProofObligationDefinition;
import vdm2isa.tr.definitions.TRProofScriptDefinition;
import vdm2isa.tr.expressions.TRExpression;
import vdm2isa.tr.modules.TRModule;
import vdm2isa.tr.modules.TRModuleList;
import vdm2isa.tr.modules.TRProofObligationModule;
import vdm2isa.tr.types.TRType;

/**
 * VDM POs to Isabelle. Cannot be called "pog2isa" as "pog" is already a command! 
 */
public class IsapogPlugin extends GeneralisaPlugin {

    private int localPOCount;
    private int localPOCountMissed;
    private final Vdm2isaPlugin vdm2isa;

    public IsapogPlugin(Interpreter interpreter) {
        super(interpreter);
        // consider extending? 
        vdm2isa = new Vdm2isaPlugin(interpreter);    
    }

    @Override
    protected void localReset()
    {
        super.localReset();
        localPOCount = 0;
        localPOCountMissed = 0;
    }

    public int getLocalPOCount()
    {
        return localPOCount;
    }

    public int getLocalPONotTranslatedCount()
    {
        return localPOCountMissed;
    }

    protected void addLocalPOS(int toadd, int notTranslated)
    {
        assert toadd >= 0;
        localPOCount += toadd;
        localPOCountMissed += notTranslated;
    }

    protected TRProofScriptDefinition chooseProofScript(ProofObligation po, TRExpression poExpr)
    {
        switch (IsaProperties.isapog_defalut_strategy)
        {
            case HOPEFUL    : return TRProofScriptDefinition.hopeful(po.location);
            case OPTIMISTIC : return TRProofScriptDefinition.optimistic(po.location);
            case PESSIMISTIC: return TRProofScriptDefinition.pessimistic(po.location);
            case REALISTIC  : return TRProofScriptDefinition.realistic(po.location);
            case SURRENDER  :
            default         : return TRProofScriptDefinition.surrender(po.location);
        }
    }

    protected String getSummaryPrefix()
    {
        return "Translated " + 
            plural(getLocalPOCount(), "PO", "s") + 
            " (of " + (getLocalPOCount()+getLocalPONotTranslatedCount()) + ")" +
            " with " + IsaProperties.isapog_defalut_strategy.name().toLowerCase() + " proof strategy for ";
    }

    protected TRExpression map2isa(TCExpression tcexpr) throws Exception
    {
        return ClassMapper.getInstance(TRNode.MAPPINGS).init().convert(tcexpr);
    }

    @Override
    public boolean isaRun(TCModuleList tclist) throws Exception {
        boolean result = !commands.isEmpty() && !tclist.isEmpty();
        if (result)
        {
            if (commands.contains("vdm2isa"))
                result = vdm2isa.internalRun(tclist);

            if (commands.contains("isapog") && result)
            {
                String workingAt = "";
                // if strict is false, result is assumed true (i.e. exceptions won't fail the pass)
                // if strict is true, result is assumed false (i.e. exceptions will fail the pass)
                result = !IsaProperties.general_strict;
                try
                {
                    // get user declared strategy
                    // if (argv != null && argv.length > 1)
                    // {
                    //     workingAt = "user chosen proof strategy = " + argv[1];
                    //     IsaProperties.isapog_defalut_strategy = IsaProofStrategy.valueOf(argv[1].toUpperCase());
                    // }

                    // create an isabelle module interpreter 
                    workingAt = "creating filtered interpreter";
                    ModuleInterpreter minterpreter = new ModuleInterpreter(new INModuleList(), tclist);

                    // get the POG and create a corresponding TRModuleList with its PO definitions 
                    workingAt = "getting isa interpreter PO list";
                    ProofObligationList pogl = minterpreter.getProofObligations();
                    IsaProofObligationList isapogl = new IsaProofObligationList();
                    int poNumber = 1;
                    List<Pair<ProofObligation, Exception>> notTranslatedPOS = new Vector<Pair<ProofObligation, Exception>>();
                    for(ProofObligation po : pogl)
                    {
                        // do not process VDMToolkit.vdmsl POs
                        if (po.location.module.equals(IsaToken.VDMTOOLKIT.toString())) continue;
                        workingAt = "processing PO " + poNumber + " for " + po.location.module;
                        try 
                        {
                            // type check PO as an TC AST
                            
                            //Pair<TCExpression, TCType> pair  = isaInterpreter.typeCheck(po.value, po.location.module);
                            //TODO check pair.value is type okay; for VDM POGs should always be fine, but there will be "mine" as well ;-)

                            TCExpression potcExpr = po.getCheckedExpression();

                            // translate the PO back to TR world
                            workingAt = "TR mapping PO " + poNumber + " for " + po.location.module;
                            TRExpression potrExpr = map2isa(potcExpr);

                            workingAt = "creating proof script for PO " + poNumber + " for " + po.location.module;
                            TRProofScriptDefinition poScript = chooseProofScript(po, potrExpr);
                            TRIsaVDMCommentList comments = TRIsaVDMCommentList.newComment(po.location, "VDM PO("+ poNumber +"): \"" + po.toString() + "\"", false);
                            TRType poType = potrExpr.getType();
                            TRProofObligationDefinition poe = TRProofObligationDefinition.newProofObligationDefinition(comments, po, potrExpr, poType /* TRType for potrExpr!*/, poNumber, poScript);
                            isapogl.add(poe);
                            poNumber++;
                        }
                        // added those after the problem with post_constS(,10)! for constS: ()->nat constS()==10 post RESULT <= 10;
                        // because these are "console" (not within the file) location info is mostly pointless? Except perhaps for VDMErrorsException
                        catch(LexException le)
                        {
                            // POs shouldn't fail to parse? VDMJ error?
                            GeneralisaPlugin.report(IsaErrorMessage.PO_PROCESSING_ERROR_4P, LexLocation.ANY, po.number, po.name, "lexing", le.toString());//le.location
                            notTranslatedPOS.add(new Pair<ProofObligation, Exception>(po, le));
                        }
                        catch(ParserException pe) 
                        {
                            // POs shouldn't fail to parse? VDMJ error?
                            GeneralisaPlugin.report(IsaErrorMessage.PO_PROCESSING_ERROR_4P, LexLocation.ANY, po.number, po.name, "parsing", pe.toString());//pe.location
                            notTranslatedPOS.add(new Pair<ProofObligation, Exception>(po, pe));
                        }
                        catch(TypeCheckException te)
                        {
                            // POs shouldn't fail to type check, but if they do...
                            //TODO consider any related context
                            GeneralisaPlugin.report(IsaErrorMessage.PO_PROCESSING_ERROR_4P, LexLocation.ANY, po.number, po.name, "type checking", te.toString());//te.location
                            notTranslatedPOS.add(new Pair<ProofObligation, Exception>(po, te));
                        }
                        catch(VDMErrorsException ve)
                        {
                            // POs shouldn't fail to type check, but if they do...
                            //TODO consider any related context
                            GeneralisaPlugin.report(IsaErrorMessage.PO_PROCESSING_ERROR_4P, ve.errors.isEmpty() ? LexLocation.ANY : ve.errors.get(0).location, 
                                po.number, po.name, "type checking", ve.toString());
                            notTranslatedPOS.add(new Pair<ProofObligation, Exception>(po, ve));
                        }
                        catch(Exception e)
                        {
                            // This is something quite bad, so stop
                            GeneralisaPlugin.report(IsaErrorMessage.PO_PROCESSING_ERROR_4P, LexLocation.ANY, po.number, po.name, "class mapping / unexpected", e.toString());
                            // in case we decide to comment the throw?
                            notTranslatedPOS.add(new Pair<ProofObligation, Exception>(po, e));
                            throw e;
                        }
                    }
                    addLocalErrors(GeneralisaPlugin.getErrorCount());

                    // be strict on translation output
                    // strict => getLocalErrorCount() == 0 and (poNumber-1) == pogl.size() and notTranslatedPOS.size() == 0
                    result = (!IsaProperties.general_strict || (getLocalErrorCount() == 0 && (poNumber-1) == pogl.size() 
                        //&& notTranslatedPOS.size() == 0
                        //TODO be linient with the above?
                    ));

                    // all not translated POs were accounted for in as comments! 
                    if (!notTranslatedPOS.isEmpty())
                    {
                        warning(IsaWarningMessage.PO_NOT_TRANSLATED_POS_LEFT_UNPROCESSED_1P, LexLocation.ANY, getUntranslatedPOSAsComments(notTranslatedPOS, null));
                    }

                    if (result)
                    {
                        // output POs per module
                        workingAt = "creating POs Isabelle file";
                        TRModuleList modules = isapogl.getModulePOs();
                        addLocalModules(modules.size());
                        String moduleName;
                        int mcount = 0;
                        for (TRModule module : modules)
                        {
                            if (module instanceof TRProofObligationModule) 
                            {
                                TRProofObligationModule pmodule = (TRProofObligationModule)module;
                                moduleName = module.name.toString();
                                workingAt = "processing POs Isabelle file for " + moduleName;
                                addLocalPOS(module.definitions.size(), getUntranslatedPOSFor(notTranslatedPOS, pmodule).size());
                                StringBuilder sb = new StringBuilder();
                                sb.append(module.translate());
                                sb.append(getUntranslatedPOSAsComments(notTranslatedPOS, pmodule));
                                outputModule(module.getLocation(), moduleName, sb.toString());    
                                mcount++;
                            }
                            else
                            {
                                report(IsaErrorMessage.PO_INVALID_PO_MODULE_1P, module.name.getLocation(), module.name.toString());
                            }
                        }

                        result = (!IsaProperties.general_strict || mcount == modules.size());
                    }
                }
                catch (IllegalArgumentException a)
                {
                    if (workingAt.equals("user chosen proof strategy"))
                    {
                        report(IsaErrorMessage.PO_INVALID_PROOF_STRATEGY_1P, LexLocation.ANY, "unknown");
                    }
                    else
                    {
                        processException(a, workingAt, true);
                    }
                    result = false;
                }
                // VDMJ exception, then allow strictness
                catch (InternalException e)
                {
                    processException(e, workingAt, false);
                    result = (!IsaProperties.general_strict || result);
                }
                catch (Throwable t)
                {
                    processException(t, workingAt, true);
                    result = false;
                }
            }
            else if (!result)
            {
                Console.out.println("Could not generate Isabelle POs because of translation errors");
            }
        }
        return result;
    }

    @Override
    protected void prompt()
    {
        Console.out.println("Calling VDM POs to " + IsaProperties.general_isa_version + "...");
        super.prompt();
    }

    /**
     * Consider failed PO translation at least adding them as comments. For now at the end of the file. Later perhaps as part of
     * a proper TRIsaCommentList. 
     * @param listOfnotTranslatedPOS
     * @param module
     * @return
     */
    private String getUntranslatedPOSAsComments(List<Pair<ProofObligation, Exception>> listOfnotTranslatedPOS, TRProofObligationModule module) {
        StringBuilder sb = new StringBuilder();
        // only within the POs of the same module; remove from the list afterwards 
        Iterator<Pair<ProofObligation, Exception>> it = listOfnotTranslatedPOS.iterator();
        while (it.hasNext())
        {
            Pair<ProofObligation, Exception> pair = it.next();
            // module null for processing not translated, neither found modules for POS!
            if (module == null || pair.key.location.module.equals(module.poModuleOwner))
            {
                it.remove();
                sb.append("\n");
                sb.append(IsaToken.bracketit(IsaToken.BLOCK_COMMENT_OPEN, 
                    "\n\tCould not translate VDM PO because of a " + pair.value.getClass().getSimpleName() + " error:" +
                    "\n\tVDM PO: " + pair.key.toString() + 
                    "\n\tReason: " + pair.value.getMessage() +
                    "\n", 
                    IsaToken.BLOCK_COMMENT_CLOSE));
                sb.append("\n");
            } 
        }
        return sb.toString();
    }

    private List<Pair<ProofObligation, Exception>> getUntranslatedPOSFor(
        List<Pair<ProofObligation, Exception>> listOfnotTranslatedPOS, 
        TRProofObligationModule module) 
    {
        List<Pair<ProofObligation, Exception>> result = new Vector<Pair<ProofObligation, Exception>>();
        for(Pair<ProofObligation, Exception> pair : listOfnotTranslatedPOS)
        {
            if (pair.key.location.module.equals(module.poModuleOwner))
            {
                result.add(pair);
            }
        }
        return result;
    }

    @Override
    public String help() {
        return "isapog - translate VDM pog results for Isabelle/HOL (v. " + IsaProperties.general_isa_version + ")";
    }

    @Override 
    protected String pluginName()
    {
        return "isapog";
    }


	@Override
    protected void printOptionDefaults()
    {
		vdm2isa.printOptionDefaults();
        printFlag("default proof strategy", IsaProperties.isapog_defalut_strategy.name().toLowerCase());
        printFlag("translate PO locale lemmas", IsaProperties.isapog_create_pog_locale_interpretation_lemmas);
    }


    @Override
    protected String commandsHelp()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.commandsHelp());
        // sb.append("graph: generates definition dependency graphs per module\n");
        // sb.append("sort: topological sort enforces declaration before use of definitions\n");
        // sb.append("check: structural check for compliance to translation rules");
        return sb.toString();
    }

    @Override
    protected boolean processArgument(String arg, Iterator<String> i)
    {
        boolean result;
        if (arg.equals("vdm2isa") && !commands.contains(arg))
        {
            result = commands.add(arg);
            if (result) 
            {
                result = vdm2isa.processArgument0(vdm2isa.defaultCommands().iterator());
                mergeCommands(vdm2isa);
            }
        }
        else if (arg.equals("isapog") && !commands.contains(arg))
        {
            result = commands.add(arg);
        }
        else if (!commands.contains(arg))
            result = super.processArgument(arg, i);
        else 
            result = commands.contains(arg);
        return result;
    }    

    @Override
    protected void doSet(String prop, String val)
    {
        if (prop.equals("ps"))
        {
            try 
            {
                IsaProperties.isapog_defalut_strategy = IsaProofStrategy.valueOf(val);
            }
            catch (IllegalArgumentException e)
            {
                usage("ps property does not know given strategy - " + val);
            }
        }
        else if (prop.equals("li"))
        {
            IsaProperties.isapog_create_pog_locale_interpretation_lemmas = Boolean.parseBoolean(val);
        }
		else
        {
            vdm2isa.doSet(prop, val);
            super.doSet(prop, val);
        }
    }

    @Override
    protected List<String> defaultCommands()
    {
        return Arrays.asList("vdm2isa", "isapog");
    }

    @Override 
    protected String options()
    {
        return vdm2isa.options() + 
            String.format(" ps=%1$s li=%2$s", 
                IsaProperties.isapog_defalut_strategy.toString().toLowerCase(), 
                IsaProperties.isapog_create_pog_locale_interpretation_lemmas); 
    }

    @Override
    protected String optionsHelp()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(vdm2isa.optionsHelp());
        sb.append("\tps <name>: chooses specific proof strategy names among " + IsaProofStrategy.values().toString().toLowerCase() + "\n");
        sb.append("\tli       : creates locale interpretation and lemmas for translated POs\n");
        return sb.toString();
    }
}
