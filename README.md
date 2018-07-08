# VA4JVM
In this project, we focus on the integration of execution traces from the regular JVM into a “jpf-visual” tool 
and visualize different run-time events.

Initially I have created the simple program which have different methods with different parameters. I have created aspectJ 
point-cut and played with point-cut declaration to understand the method call with different parameters, return type.

## Script Information:
runAspectJ.sh : Script to run the AspectJ file

runAspectJweaving.sh : Script to run the AspectJ after weaving Thread class.

## Building tool using Maven:
For the building project using maven :

First we have to install the mavent into to the System.

### Installing Maven on Ubuntu:
sudo apt-get install maven
### Installing Maven on Mac OS X :
Follow the instruction given in link :

http://www.baeldung.com/install-maven-on-windows-linux-mac

##### Build and run the HelloWorld_VA4JVM using Maven script:<br />
HelloWorld_VA4JVM is the combined project which consist of helloworld program for the visualization for JVM trace.
### Building the project :
The project is build using "build.sh" shell script in the HelloWorld_VA4JVM folder.</br>
This build script installs the third party jgraphx library to the "jpf-visual" and "hellowworld" program for graph visualization.This is achieved by including custom library jgraphx into maven local repo. Along with that, it builds both the projects("jpf-visual" without jpf dependencies and "hellowworld" program).</br>
### Run the visualization project :
The project is run using "run.sh" shell script.</br>
### Command to build and run the project :
./build.sh && ./run.sh 

 

