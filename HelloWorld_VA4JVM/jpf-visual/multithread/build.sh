#!/bin/sh

[ -e "${JPF_HOME}" ] || JPF_HOME="${HOME}/jpf/jpf-core"

javac -classpath ./target/classes:${JPF_HOME}/build/jpf.jar *.java
