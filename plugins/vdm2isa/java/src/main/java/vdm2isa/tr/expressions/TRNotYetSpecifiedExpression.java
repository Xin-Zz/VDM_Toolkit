package vdm2isa.tr.expressions;

import com.fujitsu.vdmj.lex.LexLocation;

import vdm2isa.lex.IsaToken;

public class TRNotYetSpecifiedExpression extends TRExpression {
    
	private static final long serialVersionUID = 1L;

    public TRNotYetSpecifiedExpression(LexLocation location)
    {
        super(location);
    }

    @Override
    public IsaToken isaToken() {
        return IsaToken.UNDEFINED;
    }

    @Override
    public String translate() {
        return isaToken().toString();
    }
}
