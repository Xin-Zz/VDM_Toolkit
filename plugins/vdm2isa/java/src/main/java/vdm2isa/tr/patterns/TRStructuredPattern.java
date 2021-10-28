package vdm2isa.tr.patterns;

import com.fujitsu.vdmj.tc.patterns.TCConcatenationPattern;
import com.fujitsu.vdmj.tc.patterns.TCMapPattern;
import com.fujitsu.vdmj.tc.patterns.TCMapUnionPattern;
import com.fujitsu.vdmj.tc.patterns.TCMapletPattern;
import com.fujitsu.vdmj.tc.patterns.TCSeqPattern;
import com.fujitsu.vdmj.tc.patterns.TCSetPattern;
import com.fujitsu.vdmj.tc.patterns.TCTuplePattern;
import com.fujitsu.vdmj.tc.patterns.TCUnionPattern;

import vdm2isa.lex.IsaToken;

public class TRStructuredPattern extends TRPattern {
    
    private static final long serialVersionUID = 1L;

    private final IsaToken token;
    private final String pattern; 

    public TRStructuredPattern(TCSetPattern owner, TRPatternList set)
    {  
        super(owner);
        this.token = IsaToken.SET;
        this.pattern = IsaToken.bracketit(IsaToken.SET_OPEN, set.translate(), IsaToken.SET_CLOSE);
    }

    public TRStructuredPattern(TCSeqPattern  owner, TRPatternList seq)
    {
        super(owner);
        this.token = IsaToken.SEQ;
        this.pattern = IsaToken.bracketit(IsaToken.SEQ_OPEN, seq.translate(), IsaToken.SEQ_CLOSE);
    }

    public TRStructuredPattern(TCTuplePattern owner, TRPatternList list)
    {
        super(owner);
        this.token = IsaToken.EOF;
        this.pattern = IsaToken.parenthesise(list.translate());
    }

    public TRStructuredPattern(TCConcatenationPattern owner, TRPattern left, TRPattern right)
    {
        super(owner);
        this.token = IsaToken.CONCATENATE;
        this.pattern = IsaToken.parenthesise(
                IsaToken.parenthesise(left.translate()) + 
                IsaToken.CONCATENATE.toString() + 
                IsaToken.parenthesise(right.translate()));
    }

    public TRStructuredPattern(TCMapletPattern owner, TRPattern from, TRPattern to)
    {
        super(from.location);//owner is not TCPattern!
        this.token = IsaToken.MAPLET;
        this.pattern = IsaToken.parenthesise( 
            from.translate() + " " + IsaToken.MAPLET.toString() + " " + to.translate());
    }

    public TRStructuredPattern(TCUnionPattern owner, TRPattern left, TRPattern right)
    {
        super(owner);
        this.token = IsaToken.UNION;
        this.pattern = IsaToken.parenthesise(
            left.translate() + " " + IsaToken.UNION.toString() + " " + right.translate());
    }

    public TRStructuredPattern(TCMapUnionPattern owner, TRPattern left, TRPattern right)
    {
        super(owner);
        this.token = IsaToken.MUNION;
        this.pattern = IsaToken.parenthesise(
            left.translate() + " " + IsaToken.MUNION.toString() + " " + right.translate());
    }

    public TRStructuredPattern(TCMapPattern owner, TRPatternList maplets)
    {
        super(owner);
        this.token = IsaToken.MAP;
        this.pattern = IsaToken.bracketit(IsaToken.MAP_OPEN, maplets.translate(), IsaToken.MAP_CLOSE);
    }

    @Override
    public String translate() {
        return pattern;
    }

    @Override
    public IsaToken isaToken() {
        return token;
    }
}