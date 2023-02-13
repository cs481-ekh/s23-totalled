# Totaled Senior Design Project

---

## Team
### Bree Latimer, Chase Stauts, D Metz, Paul Ellis
## Sponsors
### Ashley Holden, Justin Larson, Kristina Martin

---

## Description

This is a Kotlin project using Gradle as our build system.

--- 

## Building

In order to build the system we have a build script that you can run by calling 
>
> `$ ./build.sh`
> 

If the build script does not work on your computer you will need to follow the following steps:

>
> `$ ./gradlew build`
> 
> `$ ./gradlew packageUberJarForCurrentOS`
> 

Once the second command succeeds there will be a jar found at `build/compose/jars/totalled-{Current OS}-{Version}.jar`
This can then be used to run the program.

In order to test the program you can run the following command:
>
> `$ ./test.sh`
> 

If this command fails you can try the following commands instead:

> 
> `$ ./gradlew build`
> 
> `$ ./gradlew test`


You can clean up all build artifacts using the following script:

>
> `$ ./clean.sh`
> 

If this script fails you can run the following command instead:

> 
> `$ ./gradlew clean`
> 

---

## Deployment