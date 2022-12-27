(* VDM to Isabelle Translation @2022-12-27T12:31:49.432Z  
   Copyright 2019-22, Leo Freitas, leo.freitas@newcastle.ac.uk

   VDM translation of module TestV2ITypesGeneric_PO
   in 'TestV2ITypesGeneric' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/target/test-classes/lvl0/TestV2ITypesGeneric.vdmsl) at line 5:5
   files = [/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/target/test-classes/lvl0/TestV2ITypesGeneric.vdmsl]
*)
theory TestV2ITypesGeneric_PO
imports VDMToolkit "TestV2ITypesGeneric" 
begin


\<comment>\<open>VDM source: injective = ?\<close>
\<comment>\<open>in 'TestV2ITypesGeneric' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/target/test-classes/lvl0/TestV2ITypesGeneric.vdmsl) at line 5:5\<close>

\<comment>\<open>Implicitly defined type invariant checks for quantified type binds\<close> 
	
definition
	PO_1_injective_TOTAL :: \<open>\<bool>\<close>
where
	\<open>PO_1_injective_TOTAL  \<equiv> (\<forall> (ss :: 'UNKNOWN VDMSeq)  . ((((inv_VDMSeq' inv_True
	\<comment>\<open>Unknown VDM types will generate Isabelle additional type variable `a warning.\<close> ss))) \<longrightarrow> isTest ((injective inv_True
	\<comment>\<open>Unknown VDM types will generate Isabelle additional type variable `a warning.\<close>  ss)) (inv_bool)))\<close>

	
 

end 