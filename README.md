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

#### Building and running an application with maven script:<br />
Examples are inside the Evaluation folder [a link](https://github.com/monalip/VA4JVM/tree/master/Evaluation).
The project is built using Maven build management tool provides plugins that interact with an application. The project structure and contents are declared in the pom.xml file for each example. The dependency tag manages the dependencies of the project required for successful building the project. It included dependencies on the existing "jpf-visual" project for the visualization, AspectJ, and Java swing graph visualization( jgraphx ).<br/> 
The pom.xml file added various Maven plugins which are as follows:
• aspectj-maven-plugin weaves AspectJ aspects into the classes using the AspectJ compiler. Used the addition configuration by providing tag <Xjoinpoints>synchronization</Xjoinpoints> enable the synchronize lock unlock pointcut in AspectJ.<br />

• Apache Maven Compiler Plugin is used to compile the sources of the project. <configuration> tag helps to manage the configuration hence inside it <includes> and <excludes> tags help to exclude the JPF dependent classes of jpf-visual during compilation.<br />

• In order to add dependencies, from jar to maven project there were sev- eral approach was used. The local custom dependency can be added through the command line using the Maven goal install:install-file, but it is not a feasible solution in this case. As it needed to install the dependencies manually on each computer. Hence, maven-install-plugin is used to install the jar to the local Maven repository by specifying the location of the jar which wants to install. This approach helps to add the jgraphx dependency from its jar file during Maven clean phase.<br />

• exec-maven-plugin execute the system with the help of exec: java which executes Java programs in the same VM. The configuration of this plugin provides an option to give the arguments to the specified program.
After adding all required plugins and dependencies to the maven project created the pom.xml for the parent maven project. It added a module of jpf- visual and program whose analysis has to do in the parent pom.xml. The project is built and run using following commands in the directory where this parent pom.xml is located:<br />

• mvn clean: It helps to clean the project by removing artifact created by previous builds and add the jgraphx dependency from its jar to the maven repository.<br />

• mvn install: install artifacts in the local repository.<br />

• mvn exec:java: to execute the java program which internally starts the
visualization.<br />

The script are generated using these commands. This build scripts help to provides the necessary plugin and allow to compile only GUI related classes for the VA4JVM. The different concurrent programs source codes are present in the Evaluation folder.

#### Building the project :
The project is build and run using "build.sh" and  "run.sh" shell script in the particular program directory.</br>
</br>
#### Command to build and run the project :
./build.sh && ./run.sh 

 

=======

