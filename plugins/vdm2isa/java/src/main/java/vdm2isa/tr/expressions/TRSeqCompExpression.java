package vdm2isa.tr.expressions;

import com.fujitsu.vdmj.lex.LexLocation;
import com.fujitsu.vdmj.tc.expressions.TCSeqCompExpression;
import com.fujitsu.vdmj.tc.expressions.TCSetCompExpression;

import vdm2isa.lex.IsaToken;
import vdm2isa.tr.patterns.TRMultipleBind;
import vdm2isa.tr.patterns.TRMultipleBindList;
import vdm2isa.tr.patterns.TRMultipleSetBind;

/**
 * Isabelle sequence compression is "[ expr(bind1, bind2) . bind1 <- gen1, bind2 <- gen2, filter ]".
 * VDM sequence compression is "[ expr(bind1) . bin1 in set(q) expr2 & filter ]". If the sequence 
 * comprehension generator is a set bind, it has to be transformed to a list using IsaToken.SETSEQBIND. 
 */
public class TRSeqCompExpression extends TRExpression {
    private static final long serialVersionUID = 1L;

    private final TRExpression first;
    private final TRMultipleBind bind;
    private final TRExpression predicate;

    public TRSeqCompExpression(LexLocation location, TRExpression first, TRMultipleBind bind, TRExpression predicate)
    {
        super(location);
        this.first = first;
        this.bind = bind;
        this.predicate = predicate;
        // Tell set bind it's for a sequence
        if (this.bind instanceof TRMultipleSetBind)
            ((TRMultipleSetBind)this.bind).seqBind = true;
        //System.out.println(toString());
    }

    @Override
    public String toString()
    {
        return "SeqComp bind = " + bind.getClass().getName() + 
            " plist (" + bind.plist.size() + ")[" + bind.plist.get(0).isaToken().toString() + "] = " + 
            bind.plist.translate();
    }

    @Override
    public IsaToken isaToken() {
        return IsaToken.SEQ;
    }

    public boolean isSetSeqBind()
    {
        return (this.bind instanceof TRMultipleSetBind) && ((TRMultipleSetBind)this.bind).seqBind;
    }

    /**
     * Sequence comprehension in Isabelle 
     */
    @Override
    public String translate() {
        StringBuilder sb = new StringBuilder();
        sb.append(IsaToken.SEQ_OPEN.toString());
        sb.append(" ");
        sb.append(first.translate());
        sb.append(" ");
        sb.append(IsaToken.POINT.toString());
        sb.append(" ");
        sb.append(bind.compTranslate(false));
        if (predicate != null)
        {
            sb.append(" ");
            // predicate separator in Isabelle is "," not "|"
            sb.append(IsaToken.COMMA.toString());
            sb.append(" ");
            sb.append(predicate.translate());
        }
        sb.append(" ");
        sb.append(IsaToken.SEQ_CLOSE.toString());
        if (isSetSeqBind())
        {
            String setbindProblem = "Set bind " + 
                IsaToken.antiquotation(IsaToken.TERM, bind.translate()) +
                " in sequence comprehension requires VDM set to be ordered \n\t" + 
                " (i.e. its Isabelle type instantiates type class linorder). \n\t" +
                " This can be a problem if the target type is ordered with VDM ord_ predicate.";
            sb.append("\n\t" + IsaToken.comment(setbindProblem));
        }
        return sb.toString();
    }

}