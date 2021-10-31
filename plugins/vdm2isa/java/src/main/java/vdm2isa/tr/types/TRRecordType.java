package vdm2isa.tr.types;

import java.util.HashMap;
import java.util.Map;

import com.fujitsu.vdmj.tc.lex.TCNameToken;
import vdm2isa.lex.IsaToken;
import vdm2isa.tr.definitions.TRDefinitionList;
import vdm2isa.tr.types.visitors.TRTypeVisitor;

public class TRRecordType extends TRInvariantType
{
	private final static long serialVersionUID = 1L;

    private final TCNameToken name;
    private final TRFieldList fields;
    private final boolean composed; 

    private final static Map<TCNameToken, TRFieldList> recordMap = new HashMap<TCNameToken, TRFieldList>(); 

    public TRRecordType(TCNameToken name, TRFieldList fields, boolean composed)
    {
        super(name.getLocation());
        this.name = name;
        this.fields = fields;
        this.composed = composed;
        if (this.fields.size() == 0)
            report(10002, "Isabelle does not allow empty records for VDM record type " + name.toString());

        this.fields.setRecordType(this);
        recordMap.put(name, fields); 
    }

    public static void reset()
    {
        recordMap.clear();
    }

    @Override
    public String invTranslate(String varName) {
        StringBuilder sb = new StringBuilder();
        if (varName != null)
        {
            // definition of the type for "inv_R x" itself
            sb.append("\n\t\t");
            sb.append(IsaToken.LPAREN.toString());
            sb.append(fields.invTranslate(varName));
            sb.append("\n\t\t");
            sb.append(IsaToken.RPAREN.toString());
        }
        else
        {
            // definition for a reference to record, i.e. variable of its type
            sb.append(IsaToken.INV);
            sb.append(translate());
            sb.append(" "); 
        }
        return sb.toString();
    }

    @Override
    public IsaToken isaToken() {
        return IsaToken.RECORD;
    }

    @Override
    public String translate() {
        return name.toString(); 
    }

    public TCNameToken getName()
    {
        return this.name;
    }

    public TRFieldList getFields()
    {
        return fields;
    }

    public int fieldCount()
    {
        return fields.size();
    }

    public TRField findField(String tag)
	{
		for (TRField f: fields)
		{
			if (f.getTagName().equals(tag))
			{
				return f;
			}
		}
		return null;
	}

    public static TRFieldList fieldsOf(TCNameToken recordName)
    {
        return recordMap.get(recordName);
    }

	@Override
	public <R, S> R apply(TRTypeVisitor<R, S> visitor, S arg)
	{
		return visitor.caseRecordType(this, arg);
	}
}
