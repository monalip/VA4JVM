# jpf-visual

This project aims to provide a visualization tool for JPF (https://babelfish.arc.nasa.gov/hg/jpf/jpf-core). The current JPF can show the programme trace leading to a property violation, e.g. deadlock. The new panel, which is built upon jpf-shell (https://jpf.byu.edu/hg/jpf-shell), visualizes the programme trace to help understand the nature of failures and properties . 

## How to Download
You can obtain the source code of the jpf-visual using Mecurial (hg):

`hg clone https://qiyitang71@bitbucket.org/qiyitang71/jpf-visual`

The jpf-visual comes with a jpf.properties file for configuration with JPF. The file should work if you have checked out jpf-visual as a subdirectory of the overall jpf repository, with jpf-core and jpf-shell being another subdirectories. For example, JPF resides in project/jpf-core, the extension in project/jpf-visual. Note: you will have to have ​site.properties.

## How to build.

To compile the jpf-visual project, we recommend building sources with Apache Ant. As said above, jpf-core needs to reside in project/jpf-core and jpf-shell needs to reside in project/jpf-shell if the extestions is in project/jpf-visual. To build with Ant, switch to the directory where the jpf-visual extension is located (where build.xml file is located), and run

`ant`

## Installation
Installing jpf-visual is just like installing any other jpf project. Make sure to add jpf-visual's path to the extensions property in your site.properties file. Usually adding the following lines to your site.properties will work:
~~~
#Visual extention
jpf-visual = /path/to/the/visual/project/jpf-visual/
extensions+=,${jpf-visual}
~~~


## Running
If you want to run your own program, the easiest way to execute JPF and use trace-server with several options is to create an application property file. For example, you can create property file like this:

~~~
target=[Application]
target_args=[application_args]
classpath=[classpath to your application]

# print trace when property is violated
report.errorTracePrinter.property_violation=trace

# register console errorTracePrinter as a publisher
report.publisher+=, errorTracePrinter
report.errorTracePrinter.class=ErrorTracePrinter

#turn on the shell
shell=.shell.basicshell.BasicShell

#turn on the new panel
shell.panels+= ,errorTrace
shell.panels.errorTrace=ErrorTracePanel
~~~

##The current product and future work
See [wiki page](https://bitbucket.org/qiyitang71/jpf-visual/wiki/Home).
