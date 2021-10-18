/*******************************************************************************
 *	Copyright (c) 2020 Leo Freitas.
 ******************************************************************************/

package vdm2isa.tr.modules;

import com.fujitsu.vdmj.tc.lex.TCIdentifierToken;

import vdm2isa.lex.IsaTemplates;
import vdm2isa.lex.IsaToken;
import vdm2isa.tr.TRNode;
import vdm2isa.tr.definitions.TRDefinitionList;

public class TRModule extends TRNode
{
	private static final long serialVersionUID = 1L;
	public final TCIdentifierToken name;
	protected final TRDefinitionList definitions;
	
	public TRModule(TCIdentifierToken name, TRDefinitionList definitions)
	{
		this.name = name;
		this.definitions = definitions;
	}

	public String translate()
	{
		String loc = name.getLocation() != null ? name.getLocation().toString() : "";
		return IsaTemplates.translateModule("", loc, name.toString(), definitions.translate());
	}

	@Override
	public IsaToken isaToken() {
		return IsaToken.MODULE;
	}
}
