#!/bin/sh
<<<<<<< HEAD
mvn clean 
mvn install
=======
mvn install:install-file -Dfile=lib/jgraphx.jar -DgroupId=jgraphx -DartifactId=graphixfile -Dversion=1.0 -DgeneratePom=true -DpomFile=pom.xml
mvn clean install
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
