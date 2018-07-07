#!/bin/sh
mvn install:install-file -Dfile=lib/jgraphx.jar -DgroupId=jgraphx -DartifactId=graphixfile -Dversion=1.0 -DgeneratePom=true -DpomFile=pom.xml
mvn clean install