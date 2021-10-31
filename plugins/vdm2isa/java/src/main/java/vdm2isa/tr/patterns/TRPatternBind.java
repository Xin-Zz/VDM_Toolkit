package vdm2isa.tr.patterns;

import com.fujitsu.vdmj.lex.LexLocation;
import com.fujitsu.vdmj.tc.lex.TCNameToken;

import vdm2isa.lex.IsaToken;
import vdm2isa.tr.patterns.visitors.TRPatternVisitor;
import vdm2isa.tr.types.TRType;

public class TRPatternBind extends TRPattern {
    
    private final TRPattern pattern;
    private final TRMultipleBind bind;

    public TRPatternBind(LexLocation location, TRPattern pattern, TRMultipleBind bind)
    {
        super(location);
        this.pattern = pattern;
        this.bind = bind;

        if (pattern == null && bind == null)
            report(11111, "Cannot have null pattern and bind in TRPatternBind");
            
        System.out.println(toString());
    }

    @Override
    public String toString()
    {
        return super.toString() + 
            "\n\t pattern = " + (pattern != null ? pattern.translate() : "null") +
            "\n\t bind    = " + (bind != null ? bind.translate() : "null");
    }

    @Override
    public IsaToken isaToken() {
        return IsaToken.EOF;
    }

    @Override
    public String translate() {
        return (pattern != null ? pattern.translate() : (bind != null ? bind.translate() : ""));
    }

    @Override
    public <R, S> R apply(TRPatternVisitor<R, S> visitor, S arg) {
        return visitor.casePatternBind(this, arg);
    }
}