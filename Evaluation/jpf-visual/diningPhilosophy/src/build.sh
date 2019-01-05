#!/bin/sh

[ -e "${JPF_HOME}" ] || JPF_HOME="${HOME}/jpf/jpf-core"

javac -classpath .:${JPF_HOME}/build/jpf.jar *.java
