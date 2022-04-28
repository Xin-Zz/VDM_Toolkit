(* VDM to Isabelle Translation @2021-12-13T14:25:22.254Z
   Copyright 2021, Leo Freitas, leo.freitas@newcastle.ac.uk

in '/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl' at line 1:8
files = [/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl]
*)
theory TestV2IErrorsUnionQuotes
imports "VDMToolkit" 
begin


\<comment>\<open>VDM source: TQuoteUnion = (<A> | <B> | <C>)\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 8:5\<close>
datatype TQuoteUnion = U_A  | 
		 U_B  | 
		 U_C 
	

\<comment>\<open>VDM source: inv_TQuoteUnion = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 8:5\<close>
definition
	inv_TQuoteUnion :: "TQuoteUnion \<Rightarrow> bool"
where
	"inv_TQuoteUnion dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `inv_TQuoteUnion` specification.\<close>
		((((inv_True dummy0))))"
 

	
\<comment>\<open>VDM source: TMixedUnion = (<A> | nat)\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 11:5\<close>
datatype TMixedUnion = U_A  | 
		 U_VDMNat "VDMNat"
	

\<comment>\<open>VDM source: inv_TMixedUnion = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 11:5\<close>
definition
	inv_TMixedUnion :: "TMixedUnion \<Rightarrow> bool"
where
	"inv_TMixedUnion dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `inv_TMixedUnion` specification.\<close>
		(((case dummy0 of
		 TMixedUnion.U_A  \<Rightarrow> (inv_True dummy0)
		  | TMixedUnion.U_VDMNat dummy0 \<Rightarrow> (inv_VDMNat dummy0)
		 )))"

		 

	
\<comment>\<open>VDM source: TMixedQuoteUnion = (TMixedUnion | TQuoteUnion)\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 13:5\<close>
datatype TMixedQuoteUnion = U_TMixedUnion "TMixedUnion" | 
		 U_TQuoteUnion "TQuoteUnion"
	

\<comment>\<open>VDM source: inv_TMixedQuoteUnion = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 13:5\<close>
definition
	inv_TMixedQuoteUnion :: "TMixedQuoteUnion \<Rightarrow> bool"
where
	"inv_TMixedQuoteUnion dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `inv_TMixedQuoteUnion` specification.\<close>
		(((case dummy0 of
		 TMixedQuoteUnion.U_TMixedUnion dummy0 \<Rightarrow> (inv_TMixedUnion dummy0)
		  | TMixedQuoteUnion.U_TQuoteUnion dummy0 \<Rightarrow> (inv_TQuoteUnion dummy0)
		 )))"

		 

	
\<comment>\<open>VDM source: f1: (TQuoteUnion -> bool)
	f1(qu) ==
(qu <> <A>)\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 17:5\<close>

\<comment>\<open>VDM source: pre_f1 = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 17:5\<close>
definition
	pre_f1 :: "TQuoteUnion \<Rightarrow> bool"
where
	"pre_f1 qu \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `pre_f1` specification.\<close>
		((inv_TQuoteUnion qu))"


\<comment>\<open>VDM source: post_f1 = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 17:5\<close>
definition
	post_f1 :: "TQuoteUnion\<Rightarrow> bool \<Rightarrow> bool"
where
	"post_f1 qu  RESULT \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `post_f1` specification.\<close>
		((inv_TQuoteUnion qu)  \<and>  (inv_bool RESULT))"

definition
	f1 :: "TQuoteUnion \<Rightarrow> bool"
where
	"f1 qu \<equiv> 
	\<comment>\<open>User defined body of f1.\<close>
	(
	\<comment>\<open>Implicit union type parameters projection\<close>
	((qu \<noteq> TQuoteUnion.U_A )))"

	
\<comment>\<open>VDM source: f3: (() -> TMixedUnion)
	f3() ==
<A>\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 20:5\<close>

\<comment>\<open>VDM source: pre_f3 = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 20:5\<close>
definition
	pre_f3 :: "bool"
where
	"pre_f3  \<equiv> True"


\<comment>\<open>VDM source: post_f3 = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 20:5\<close>
definition
	post_f3 :: "TMixedUnion \<Rightarrow> bool"
where
	"post_f3 RESULT \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `post_f3` specification.\<close>
		((inv_TMixedUnion RESULT))"

definition
	f3 :: "TMixedUnion"
where
	"f3  \<equiv> 
	\<comment>\<open>User defined body of f3.\<close>
	TQuoteUnion.U_A "

	
\<comment>\<open>VDM source: f4: (() -> TMixedQuoteUnion)
	f4() ==
<A>\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 23:5\<close>

\<comment>\<open>VDM source: pre_f4 = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 23:5\<close>
definition
	pre_f4 :: "bool"
where
	"pre_f4  \<equiv> True"


\<comment>\<open>VDM source: post_f4 = ?\<close>
\<comment>\<open>in 'TestV2IErrorsUnionQuotes' (/Users/nljsf/Local/reps/git/VDM_Toolkit/plugins/vdm2isa/java/src/test/resources/TestV2IErrorsUnionQuotes.vdmsl) at line 23:5\<close>
definition
	post_f4 :: "TMixedQuoteUnion \<Rightarrow> bool"
where
	"post_f4 RESULT \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared `post_f4` specification.\<close>
		((inv_TMixedQuoteUnion RESULT))"

definition
	f4 :: "TMixedQuoteUnion"
where
	"f4  \<equiv> 
	\<comment>\<open>User defined body of f4.\<close>
	TQuoteUnion.U_A "

end