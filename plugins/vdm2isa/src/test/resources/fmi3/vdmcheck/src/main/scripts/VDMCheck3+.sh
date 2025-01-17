#!/bin/bash
##############################################################################
#
#	Copyright (c) 2017-2022, INTO-CPS Association,
#	c/o Professor Peter Gorm Larsen, Department of Engineering
#	Finlandsgade 22, 8200 Aarhus N.
#
#	This file is part of the INTO-CPS toolchain.
#
#	VDMCheck is free software: you can redistribute it and/or modify
#	it under the terms of the GNU General Public License as published by
#	the Free Software Foundation, either version 3 of the License, or
#	(at your option) any later version.
#
#	VDMCheck is distributed in the hope that it will be useful,
#	but WITHOUT ANY WARRANTY; without even the implied warranty of
#	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#	GNU General Public License for more details.
#
#	You should have received a copy of the GNU General Public License
#	along with VDMCheck. If not, see <http://www.gnu.org/licenses/>.
#	SPDX-License-Identifier: GPL-3.0-or-later
#
##############################################################################

#
# Process an FMI V3 FMU or XML file, and validate the XML structure using the VDM-SL model.
#

USAGE="Usage: VDMCheck3.sh [-h <FMI Standard base URL>] [-v <VDM outfile>] <file>.fmu | <file>.xml"

while getopts ":h:v:x:s:" OPT
do
    case "$OPT" in
    	h)
    		LINK=${OPTARG}
    		;;
        v)
            SAVE=$(realpath ${OPTARG})
            ;;
        *)
			echo "$USAGE"
			exit 1
            ;;
    esac
done

shift "$((OPTIND-1))"

if [ $# = 1 ]
then
	FILE=$(realpath "$1")
fi

if [ -z "$LINK" ]
then
	LINK="https://fmi-standard.org/docs/3.0/"
fi

if [ -z "$FILE" ]
then
	echo "$USAGE"
	exit 1
fi

if [ ! -e "$FILE" ]
then
	echo "File not found: $FILE"
	exit 1
fi

SCRIPT=$0

# Subshell cd, so we can set the classpath
(
	path=$(which "$SCRIPT")
	dir=$(dirname "$path")
	cd "$dir"
	
	XSD="schema/fmi3.xsd"
	MODEL="model model/Rules/*.adoc"

	java -Xmx1g \
		-Dvdmj.parser.merge_comments=true \
		-Dvdmj.parser.external_readers=.fmu=fmureader.FMUReader,.xml=fmureader.FMUReader \
		-Dfmureader.noschema=true \
		-Dfmureader.vdmfile="$SAVE" \
		-cp vdmj.jar:annotations.jar:xsd2vdm.jar:fmuReader.jar com.fujitsu.vdmj.VDMJ \
		-vdmsl -q -annotations -e "isValidFMIConfiguration(modelDescription, buildDescription, terminalsAndIcons)" \
		$MODEL "$FILE" |
		sed -e "s+<FMI3_STANDARD>+$LINK+" |
		awk '/^true$/{ print "No errors found."; exit 0 };/^false$/{ print "Errors found."; exit 1 };{ print }'
)

EXIT=$?		# From subshell above

if [ "$SAVE" ]
then
	echo "VDM output written to $SAVE"
fi
	
exit $EXIT

