#!/bin/sh
mvn install:install-file -Dfile=jpf-visual/lib/jgraphx.jar -DgroupId=jgraphx -DartifactId=graphixfile -Dversion=1.0 -DgeneratePom=true -DpomFile=jpf-visual/pom.xml
mvn install:install-file -Dfile=helloworld/lib/jgraphx.jar -DgroupId=jgraphx -DartifactId=graphixfile -Dversion=1.0 -DgeneratePom=true -DpomFile=helloworld/pom.xml
mvn clean install