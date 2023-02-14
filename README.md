# Totalled Senior Design Project
[![CI]((https://github.com/cs481-ekh/s23-totalled/actions/workflows/ci.yml/badge.svg)](https://github.com/cs481-ekh/s23-totalled/actions/workflows/ci.yml)
---

## Team
### Bree Latimer, Chase Stauts, D Metz, Paul Ellis
## Sponsors
### Boise State University Mechanical and Biomedical Engineering Department

---

## Description

This is a project that is intended to take in Excel Workbooks of Spending and Team data for a Senior Design
Project and analyze, group and create team based Expense Breakdown Excel Workbooks for the Department to use
in creating Invoices for the sponsors of projects. We implemented our solution using Kotlin and the Gradle
build system managing our dependencies. This document will describe steps for building, testing, and deploying
the final solution.

--- 

## Building

### Prepare for deployment
In order to build the project we have a build script that you can run in the terminal within the project
directory by calling 
>
> `$ ./build.sh`
> 

If the build script does not work on your computer you will need to use the terminal to run the following
commands from within the project folder:

>
> `$ ./gradlew build`
> 
> `$ ./gradlew packageUberJarForCurrentOS`
> 

Once the second command succeeds there will be a jar found at `build/compose/jars/totalled-{Current OS}-{Version}.jar`

This JAR can then be used to run the program.

### Testing

In order to test the program you can run the following terminal command from within the project directory:
>
> `$ ./test.sh`
> 

If this command fails you can try the following terminal commands instead:

> 
> `$ ./gradlew build`
> 
> `$ ./gradlew test`

### Clean up

You can clean up all build artifacts by running the following script within the project directory:

>
> `$ ./clean.sh`
> 

If this script fails you can try running the following terminal commands instead:

> 
> `$ ./gradlew clean`
> 

---

## Deployment

In order to deploy the program to the end users you will need to follow the steps for building a jar 
in the build section of this guide. Then you will be able to take the JAR file that is produced and
share it with any users. The users will then be able to run the JAR like any other program from their
Desktop or Downloads folder.
