#!/bin/sh
echo $1
java -Xmx2048m -jar "/Users/nljsf/Documents/research/java/OvertureIde-1.0.0[May]/plugins/org.overture.ide.generated.vdmj_0.3.2/lib/vdmj-2.0.2.jar" -vdmsl -r vdm10 -e "Execute()" ./Heap0.vdmsl ./Heap1.vdmsl ./lib/IO.vdmsl ./lib/VDMUtil.vdmsl