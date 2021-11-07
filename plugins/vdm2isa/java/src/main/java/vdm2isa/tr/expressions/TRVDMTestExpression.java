package vdm2isa.tr.expressions;

import com.fujitsu.vdmj.lex.LexLocation;
import com.fujitsu.vdmj.tc.lex.TCNameToken;

import vdm2isa.messages.IsaErrorMessage;
import vdm2isa.tr.definitions.TRDefinition;
import vdm2isa.tr.expressions.visitors.TRExpressionVisitor;
import vdm2isa.tr.types.TRBasicType;
import vdm2isa.tr.types.TRType;

/**
 * Super class for VDM is_X and narrow expressions.
 */
public abstract class TRVDMTestExpression extends TRExpression {
    
	private static final long serialVersionUID = 1L;
    protected final TCNameToken typename;
    protected final TRType basictype;
    protected final TRExpression test;
    protected final TRDefinition typedef;

    public TRVDMTestExpression(LexLocation location, TCNameToken typename, TRType basictype, TRExpression test, TRDefinition typedef)
    {
        super(location);
        this.typename = typename;
        this.basictype = basictype;
        this.test = test;
        this.typedef = typedef;
        if (basictype != null && typename != null)
            report(IsaErrorMessage.VDMSL_INVALID_TESTEXPR_2P, typename.toString(), basictype.getClass().getName());
        if (basictype != null && !(basictype instanceof TRBasicType))
            report(IsaErrorMessage.VDMSL_INVALID_TESTEXPR_BASICTYPE_2P, getClass().getName(), basictype.getClass().getName()); 
    }

    @Override
    public String toString()
    {
        return getClass().getName () + " for " +
            "\n\t typename = " + String.valueOf(typename) +
            "\n\t basictype= " + (basictype != null ? basictype.translate() : "null") +
            "\n\t test     = " + (test != null ? test.translate() : "null") +
            "\n\t typedef  = " + (typedef != null ? typedef.translate() : "null");
    }

    public boolean isBasicTyped()
    {
        return basictype != null && basictype instanceof TRBasicType && typename == null;
    }

    public boolean isNameTyped()
    {
        return basictype == null && typename != null;
    }

	@Override
	public <R, S> R apply(TRExpressionVisitor<R, S> visitor, S arg)
	{
		return visitor.caseVDMTestExpression(this, arg);
	}
}
