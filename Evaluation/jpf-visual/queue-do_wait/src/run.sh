#!/bin/sh

[ -e "${JPF_HOME}" ] || JPF_HOME="${HOME}/jpf/jpf-core"

if [ "$1" = "" ]
then
	echo "*** Error: This script requires one argument: QueueTest or ProdCons" 1>&2
	exit 1
fi

case "$1" in
  q* | Q*) ${JPF_HOME}/bin/jpf QueueTest.jpf;;
  p* | P*) ${JPF_HOME}/bin/jpf ProdCons.jpf;;
  *)
	echo "*** Error: This script requires one argument: QueueTest or ProdCons" 1>&2
	exit 1
  ;;
esac
