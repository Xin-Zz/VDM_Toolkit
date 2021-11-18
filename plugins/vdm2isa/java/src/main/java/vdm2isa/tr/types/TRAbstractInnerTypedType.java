package vdm2isa.tr.types;

import com.fujitsu.vdmj.tc.types.TCType;

import vdm2isa.messages.IsaErrorMessage;
import vdm2isa.tr.definitions.TRDefinitionList;

/**
 * General abstract class for types with inner types (e.g. optional, set, seq, named, field, function etc).
 * The inner type is supposed to be the "result" type of this type (e.g. result for function, to for maps, etc). 
 */
public abstract class TRAbstractInnerTypedType extends TRType {

	private static final long serialVersionUID = 1L;
    private final TRType type;
    
    protected TRAbstractInnerTypedType(TCType vdmType, TRDefinitionList defs, TRType type) {
        super(vdmType, defs);
        this.type = type;
    }

    public TRType getInnerType()
    {
        return type;
    }

    @Override
    public String getName()
    {
        return getInnerType().getName();
    }

    @Override
    public void checkForUnionTypes() {
        if (getInnerType() instanceof TRUnionType)
        {
            report(IsaErrorMessage.ISA_INVALID_UNIONTYPE_1P, getInnerType().getClass().getSimpleName());   
        }
    }    
}