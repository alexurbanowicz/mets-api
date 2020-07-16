Example Programs
================
Two example programs are included in the source distribution primarily to demonstrate the use of the API. Before using the API it is recommended reviewing the source of these examples in order to understand the basic constructs. To run the examples follow the instructions below.

Ensure the jar files in the toolkit's lib directory are in the CLASSPATH environment variable.

1. java au.edu.apsr.mtk.example.SampleMETSProcess <METS file>
Output some basic information about the METS file in the argument

2. java au.edu.apsr.mtk.example.SampleMETSBuild
Build a simple METS document and output the result

Javadocs
========
Use the following command to build the Javadoc:
 ant javadoc

Either ignore errors regarding the logging classes not being found else add the log4j.jar file to the class path before building the documentation