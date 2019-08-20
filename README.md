# VA4JVM
Use of the multithreaded programs is increasing continuously nowadays which leads to various concurrent issues. The analysis of such programs is com- plex due to the non-deterministic approach of the thread scheduler. Visualiza- tion of the concurrent events, based on thread helps to analyze the concurrent program efficiently. The extension such as visual analytics "jpf-visual" tool for regular JVM trace will help Java programmers to better understand and analyze the runtime execution of concurrent program. AspectJ instrumentation with its lock() and unlock() pointcut extension makes it possible to capture an important runtime event information in order to generate JVM event trace. A successful integration of the JVM trace into the "jpf-visual" is achieved through code refactoring and use of adapter classes in the existing tool. The implementation of such an approach for the regular JVM is preliminarily shown in this project work that it is possible to analyze the concurrent events using regular JVM. Such implementation can help to provide a generic approach for the concurrency issue analysis.


In this project, we focus on the integration of execution traces from the regular JVM into a “jpf-visual” tool 
and visualize different run-time events.

1. thread creation/death 
2. lock/unlock
3. wait/notify
4. field access
5. method call
6. line-by-line display of execution trace

On the front-end side, the code is well structured, so refactoring of code in jpf-visual and use of unified interfaces provides different events data structures. Implementing this common interface using adapter provides all the events data that used in the jpf-visual, both from the current JPF error traces and JVM error traces.

In order to generate, JPF compatible error trace for regular JVM, AspectJ instrumentation tool is used on the back-end side. AspectJ captured the run-time events information during program execution in JVM. The captured data is temporarily stored into the instance of the singleton pattern. Further, these global variables are used to process captured event data into the data structure that used by jpf-visual for visualization.

## Building tool using Maven:
For the building project using maven :

First we have to install the mavent into to the System.

### Installing Maven on Ubuntu:
sudo apt-get install maven
### Installing Maven on Mac OS X :
Follow the instruction given in link :

http://www.baeldung.com/install-maven-on-windows-linux-mac

### Installing jpf-visual :
All the detailed information about the "jpf-visual" project and the installation guide is provided in the folder [jpf-visual](https://github.com/monalip/VA4JVM/tree/master/Evaluation/jpf-visual). Furthermore, a .jar file for jpf-shell and jpf-core is provided in the folder [JPF]().

#### Building and running an application with maven script:<br />

The project is built using Maven build management tool provides plugins that interact with an application. The  pom.xml file in each example directory represents project structure and contents. The dependency tag manages the dependencies of the project required for successful building the project. It included dependencies on the existing "jpf-visual" project [here](https://github.com/monalip/VA4JVM/tree/master/Evaluation/jpf-visual) for the visualization, AspectJ, and Java swing graph visualization( jgraphx ).<br /> 

All the detail information of the required maven plugins and dependencies for child maven projects (existing "jpf-visual" project and selected concurrent program for analysis) are well documented in [Maven_Plugins_Info](https://github.com/monalip/VA4JVM/blob/master/Maven_Plugins_Info.md). Finally, the parent maven project is created in which the child module of jpf-visual and program whose analysis has to do is added in the parent pom.xml. The project is built and run using following commands in the directory where this parent [pom.xml](https://github.com/monalip/VA4JVM/tree/master/Evaluation/readerswriters/pom.xml) is located:<br /> 
• mvn clean: It helps to clean the project by removing artifact created by previous builds and add the jgraphx dependency from its jar to the maven repository.<br />

• mvn install: install artifacts in the local repository.<br />

• mvn exec:java: to execute the java program which internally starts the
visualization.<br />

The script are generated using these commands. This build scripts help to provides the necessary plugin and allow to compile only GUI related classes for the VA4JVM.<br />
#### The different concurrent programs source codes used for analysis are present in the folder [Evaluation](https://github.com/monalip/VA4JVM/tree/master/Evaluation).

#### Building the project :
The project is build and run using "build.sh" and  "run.sh" shell script in the particular program directory [readerswriters](https://github.com/monalip/VA4JVM/tree/master/Evaluation/readerswriters).</br>
</br>
#### Command to build and run the project :
./build.sh && ./run.sh 

 

=======

