package vdm2isa.tr.expressions.templates;

import vdm2isa.tr.templates.IsaAbstractTemplate;

public class IsaIfThenElse extends IsaAbstractTemplate {
    
    public final String test;
    public final String then;
    public final String els; 
    
    public IsaIfThenElse(String test, String then, String els)
    {
        this(null, test, then, els);
    }

    public IsaIfThenElse(String comment, String test, String then, String els)
    {
        super(comment);
        this.test = test;
        this.then = then;
        this.els  = els;
    }
}
