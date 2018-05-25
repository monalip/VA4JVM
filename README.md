# VA4JVM
In this project, we focus on the integration of execution traces from the regular JVM into a “jpf-visual” tool 
and visualize different run-time events.

Initially I have created the simple program which have different methods with different parameters. I have created aspectJ 
point-cut and played with point-cut declaration to understand the method call with different parameters, return type.

# Script Information:
runAspectJ.sh : Script to run the AspectJ file

runAspectJweaving.sh : Script to run the AspectJ after weaving Thread class.

# Building tool using Maven:
For the building project using maven :
First we have to install the mavent into to the System.
# Installing Maven on Ubuntu:
sudo apt-get install maven
# Installing Maven on Mac OS X :
Follow the instruction given in link :
http://www.baeldung.com/install-maven-on-windows-linux-mac
Compile using Maven:
mvn compile
Building the project:
mvn clean package
Run the project:
mvn exec:java

