package vdm2isa.tr.expressions;

import com.fujitsu.vdmj.lex.LexLocation;

import vdm2isa.lex.IsaToken;
import vdm2isa.tr.TRNode;
import vdm2isa.tr.definitions.TRDefinitionList;
import vdm2isa.tr.expressions.visitors.TRExpressionVisitor;
import vdm2isa.tr.types.TRSetType;
import vdm2isa.tr.types.TRType;
import vdm2isa.tr.types.TRUnknownType;

public class TRSetRangeExpression extends TRExpression {
    private static final long serialVersionUID = 1L;

    public final TRExpression first;
    public final TRExpression last;

    public TRSetRangeExpression(LexLocation start, TRExpression first, TRExpression last, TRType exptype)
    {
        super(start, exptype);
        this.first = first;
        this.last = last;
    }

    @Override
    public void setup()
    {
        super.setup();
        TRNode.setup(first, last);
    }

    @Override
    protected TRType getBestGuessType()
    {
        TRType t = first.getType();
        if (t instanceof TRUnknownType)
            t = last.getType();
        return new TRSetType(exptype.getVDMType(), new TRDefinitionList(), t, false);
    }

    @Override
    public IsaToken isaToken() {
        return IsaToken.SET;
    }

    @Override
    public String translate() {
        return IsaToken.SET_OPEN.toString() + first.translate() + IsaToken.RANGE.toString() + last.translate() + IsaToken.SET_CLOSE.toString();
    }

	@Override
	public <R, S> R apply(TRExpressionVisitor<R, S> visitor, S arg)
	{
		return visitor.caseSetRangeExpression(this, arg);
	}
}
