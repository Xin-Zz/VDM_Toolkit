package vdm2isa.tr.types;

import com.fujitsu.vdmj.tc.lex.TCNameToken;

import vdm2isa.lex.IsaToken;

public class TRField extends TRType {
    
    private final TCNameToken tagname;
    private final TRType type;
    private final boolean equalityAbstraction; 
    
    public TRField(TCNameToken tagname, TRType type, boolean equalityAbstraction)
    {
        super(tagname.getLocation());
        this.tagname = tagname;
        this.type = type;
        this.equalityAbstraction = equalityAbstraction;
    }

    @Override
    public String invTranslate(String varName) {
        return type.invTranslate(varName);
    }

    @Override
    public IsaToken isaToken() {
        return IsaToken.EOF;
    }

    @Override
    public String translate() {
        return this.tagname.toString() + " " + IsaToken.TYPEOF.toString() + " " + 
            "\"" + this.type.translate() + "\"";
    }
}