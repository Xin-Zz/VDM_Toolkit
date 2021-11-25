(* VDM to Isabelle Translation @2021-11-25T10:00:38.075057Z
   Copyright 2021, Leo Freitas, leo.freitas@newcastle.ac.uk

in './src/test/resources/TestV2ITypesUnion.vdmsl' at line 1:8
files = [./src/test/resources/TestV2ITypesUnion.vdmsl]
*)
theory TestV2ITypesUnion
imports VDMToolkit
begin


\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 7:5\<close>
datatype TUnion = U_VDMInt "VDMInt"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 7:5\<close>
definition
	inv_TUnion :: "TUnion \<Rightarrow> \<bool>"
where
	"inv_TUnion dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared inv_TUnion specification\<close>
		(((case dummy0 of
		 U_VDMInt dummy01 \<Rightarrow> (inv_VDMInt dummy01)
		 )))"

		 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 8:5\<close>
datatype TUnion' = U_VDMReal "VDMReal"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 8:5\<close>
definition
	inv_TUnion' :: "TUnion' \<Rightarrow> \<bool>"
where
	"inv_TUnion' dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared inv_TUnion' specification\<close>
		(((case dummy0 of
		 U_VDMReal dummy01 \<Rightarrow> (inv_VDMReal dummy01)
		 )))"

		 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 11:5\<close>
datatype TUnion2 = TUnion2_VDMNat_VDMSeq "VDMNat VDMSeq" | 
		 TUnion2_VDMReal_VDMSet "VDMReal VDMSet"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 11:5\<close>
definition
	inv_TUnion2 :: "TUnion2 \<Rightarrow> \<bool>"
where
	"inv_TUnion2 dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared inv_TUnion2 specification\<close>
		(((case dummy0 of
		 TUnion2_VDMNat_VDMSeq dummy01 \<Rightarrow> (inv_VDMSeq' (inv_VDMNat) dummy01)
		  | TUnion2_VDMReal_VDMSet dummy02 \<Rightarrow> (inv_VDMSet' (inv_VDMReal) dummy02)
		 )))"

		 \<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 11:5\<close>
datatype TUnion2' = TUnion2'_VDMNat_VDMSeq "VDMNat VDMSeq" | 
		 TUnion2'_VDMReal_VDMSeq "VDMReal VDMSeq"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 11:5\<close>
definition
	inv_TUnion2' :: "TUnion2' \<Rightarrow> \<bool>"
where
	"inv_TUnion2' dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared inv_TUnion2 specification\<close>
		(((case dummy0 of
		 TUnion2'_VDMNat_VDMSeq dummy01 \<Rightarrow> (inv_VDMSeq' (inv_VDMNat) dummy01)
		  | TUnion2'_VDMReal_VDMSeq dummy02 \<Rightarrow> (inv_VDMSeq' (inv_VDMReal) dummy02)
		 )))"

	
		
	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 14:5\<close>
type_synonym TBasic = "VDMNat"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 14:5\<close>
definition
	inv_TBasic :: "TBasic \<Rightarrow> \<bool>"
where
	"inv_TBasic dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared inv_TBasic specification\<close>
		(((inv_VDMNat dummy0)))"
 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 16:5\<close>
type_synonym TBasic2 = "TBasic"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 17:9\<close>
definition
	inv_TBasic2 :: "TBasic2 \<Rightarrow> \<bool>"
where
	"inv_TBasic2 t \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for inv_TBasic2 specification\<close>
		((inv_TBasic t))  \<and> 
		\<comment>\<open>User defined body of inv_TBasic2\<close>
		(t > (10::VDMNat1))"

		 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 19:5\<close>
datatype TUnion3 = U_TBasic2 "TBasic2" | 
		 U_TUnion2 "TUnion2"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 19:5\<close>
definition
	inv_TUnion3 :: "TUnion3 \<Rightarrow> \<bool>"
where
	"inv_TUnion3 dummy0 \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared inv_TUnion3 specification\<close>
		(((case dummy0 of
		 U_TBasic2 dummy01 \<Rightarrow> (inv_TBasic2 dummy01)
		  | U_TUnion2 dummy02 \<Rightarrow> (inv_TUnion2 dummy02)
		 )))"

		 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 23:5\<close>
datatype TUnion4 = U_VDMReal "VDMReal"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 24:9\<close>
definition
	inv_TUnion4 :: "TUnion4 \<Rightarrow> \<bool>"
where
	"inv_TUnion4 u \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for inv_TUnion4 specification\<close>
		(((case u of
		 U_VDMReal u1 \<Rightarrow> (inv_VDMReal u1)
		 )))  \<and> 
		\<comment>\<open>User defined body of inv_TUnion4\<close>
		(u > (0::VDMNat))"

		 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 26:5\<close>
datatype TUnion5 = U_VDMInt "VDMInt"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 27:9\<close>
definition
	inv_TUnion5 :: "TUnion5 \<Rightarrow> \<bool>"
where
	"inv_TUnion5 u \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for inv_TUnion5 specification\<close>
		(((case u of
		 U_VDMInt u1 \<Rightarrow> (inv_VDMInt u1)
		 )))  \<and> 
		\<comment>\<open>User defined body of inv_TUnion5\<close>
		(u < (0::VDMNat))"

		 

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 31:5\<close>
datatype TUnion6 = U_VDMInt "VDMInt"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 32:9\<close>
definition
	inv_TUnion6 :: "TUnion6 \<Rightarrow> \<bool>"
where
	"inv_TUnion6 u \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for inv_TUnion6 specification\<close>
		(((case u of
		 U_VDMInt u1 \<Rightarrow> (inv_VDMInt u1)
		 )))  \<and> 
		\<comment>\<open>User defined body of inv_TUnion6\<close>
    (case u of 
      U_VDMInt u1 \<Rightarrow>
		((isTest (u1) inv_VDMInt \<longrightarrow> (u1 < (0::VDMNat))) \<and> (isTest (u1) inv_TBasic \<longrightarrow> (u1 > (0::VDMNat)))))"

definition
  f2 :: "TUnion6 \<Rightarrow> TUnion2 \<Rightarrow> \<bool>"
  where
  "f2 u6 u2 \<equiv> 
    (case u6 of 
      U_VDMInt u61 \<Rightarrow>
        (case u2 of
           TUnion2_VDMNat_VDMSeq u21 \<Rightarrow> 
              isTest u61 inv_VDMInt \<longrightarrow> 
                  (isTest u21 (inv_VDMSeq' inv_VDMNat) \<longrightarrow> u61 \<in> elems u21)
                  \<and>
                  (isTest u21 (inv_VDMSet' inv_VDMReal) \<longrightarrow> u61 \<in> u21)
         | TUnion2_VDMReal_VDMSet u22 \<Rightarrow> 
              isTest u61 inv_VDMNat \<longrightarrow> 
                  (isTest u22 (inv_VDMSeq' inv_VDMNat) \<longrightarrow> u61 \<notin> elems u22)
                  \<and>
                  (isTest u22 (inv_VDMSet' inv_VDMReal) \<longrightarrow> u61 \<notin> u22)
           )
    )"

definition
  f2 :: "TUnion6 \<Rightarrow> TUnion2 \<Rightarrow> \<bool>"
  where
  "f2 u6 u2 \<equiv> 
    (case u6 of 
      U_VDMInt u61 \<Rightarrow>
        (case u2 of
           TUnion2_VDMNat_VDMSeq u21 \<Rightarrow> 
              isTest u61 inv_VDMInt \<longrightarrow> 
                  (isTest u21 (inv_VDMSeq' inv_VDMNat) \<longrightarrow> u61 \<in> elems u21)
         | TUnion2_VDMReal_VDMSet u22 \<Rightarrow> 
              isTest u61 inv_VDMNat \<longrightarrow> 
                  (isTest u22 (inv_VDMSet' inv_VDMReal) \<longrightarrow> u61 \<notin> u22)
           )
    )"

definition
  f2' :: "TUnion6 \<Rightarrow> TUnion2' \<Rightarrow> \<bool>"
  where
  "f2' u6 u2 \<equiv> 
    (case u6 of 
      U_VDMInt u61 \<Rightarrow>
        (case u2 of
           TUnion2'_VDMNat_VDMSeq u21 \<Rightarrow> u61 \<in> elems u21
         | TUnion2'_VDMReal_VDMSeq u22 \<Rightarrow> u61 \<in> elems u22
           ) 
    )"

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 34:5\<close>
datatype TUnion6' = U_VDMInt "VDMInt"
	

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 35:9\<close>
definition
	inv_TUnion6' :: "TUnion6' \<Rightarrow> \<bool>"
where
	"inv_TUnion6' u \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for inv_TUnion6' specification\<close>
		(((case u of
		 U_VDMInt u1 \<Rightarrow> (inv_VDMInt u1)
		 )))  \<and> 
		\<comment>\<open>User defined body of inv_TUnion6'\<close>
    (case u of 
      U_VDMInt u1 \<Rightarrow>
		((isTest (u1) inv_VDMInt \<longrightarrow> (u1 < (0::VDMNat))) \<and> (isTest (u1) inv_TBasic \<longrightarrow> (u1 > (0::VDMNat)))))"
	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 38:5\<close>

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 38:5\<close>
definition
	pre_f6 :: "TUnion6 \<Rightarrow> \<bool>"
where
	"pre_f6 u \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared pre_f6 specification\<close>
		((inv_TUnion6 u))"


\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 38:5\<close>
definition
	post_f6 :: "TUnion6\<Rightarrow> VDMInt \<Rightarrow> \<bool>"
where
	"post_f6 u  RESULT \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared post_f6 specification\<close>
		((inv_TUnion6 u)  \<and>  (inv_VDMInt RESULT))"

definition
	f6 :: "TUnion6 \<Rightarrow> VDMInt"
where
	"f6 u \<equiv> 
	\<comment>\<open>User defined body of f6\<close>
	u"

	
\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 41:5\<close>

\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 41:5\<close>
definition
	pre_f6' :: "TUnion6' \<Rightarrow> \<bool>"
where
	"pre_f6' u \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared pre_f6' specification\<close>
		((inv_TUnion6' u))"


\<comment>\<open>in 'TestV2ITypesUnion' (./src/test/resources/TestV2ITypesUnion.vdmsl) at line 41:5\<close>
definition
	post_f6' :: "TUnion6'\<Rightarrow> VDMInt \<Rightarrow> \<bool>"
where
	"post_f6' u  RESULT \<equiv> 
		\<comment>\<open>Implicitly defined type invariant checks for undeclared post_f6' specification\<close>
		((inv_TUnion6' u)  \<and>  (inv_VDMInt RESULT))"

definition
	f6' :: "TUnion6' \<Rightarrow> VDMInt"
where
	"f6' u \<equiv> 
	\<comment>\<open>User defined body of f6'\<close>
	u"

end