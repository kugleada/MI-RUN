# MI-RUN

# Installation

1. Download repository
2. Download GraalVM from http://www.oracle.com/technetwork/oracle-labs/program-languages/downloads/index.html (i.e. GraalVM based on JDK for Linux)
3. Unzip the GraalVM to ./graalvm (./graalvm should contain folders like bin, jre, lib, ...)
4. Set JAVA_HOME to <absolute path to MI-RUN>/MI-RUN/graalvm (i.e. export JAVA_HOME=/home/jn/run/gitk/MI-RUN/graalvm) - this points to special JDK, maven will call it during build

## Build program WITHOUT tests:

```
$ mvn -Dmaven.test.skip=true install (or package, or clean install)
```

## Build program WITH tests:

```
$ mvn package (or clean install: mvn clean install)
```

## Run program:
1. Go to root folder of the project (./MI-RUN should be working directory)
2. Run script ./schemein


# Requirements for development, installation on Linux

Java8 JDK (basic, can't be used for Graal, overwrites JAVA_HOME variable!)
*  sudo add-apt-repository ppa:webupd8team/java
*  sudo apt-get update
*  sudo apt-get install oracle-java8-installer

If JDK9 needed (it is NOT supported by GraalVM yet!):
* sudo apt-get install oracle-java9-installer

Maven:

*  sudo apt-get install maven3
* sudo apt-get install maven

If not working, then this:

*  sudo add-apt-repository "deb http://ppa.launchpad.net/natecarlson/maven3/ubuntu precise main"
*  sudo apt-get update
* sudo apt-get install maven3

For IntelliJ IDE, follow instructions here: https://www.jetbrains.com/help/idea/install-and-set-up-intellij-idea.html

Download: https://www.jetbrains.com/idea/download/#section=linux

(Choose Linux, download tar.gz, unpack to dir, run script)

In IntelliJ, simply import project. It should detect pom.xml, project structure etc. Do not let IDE change the general structure! Do not build the program using IDE!
Build it using mvn package and run it using ./schemein, as written above.

# Tutorials and references:
* https://github.com/graalvm/simplelanguage
* http://stefan-marr.de/2015/11/add-graal-jit-compilation-to-your-jvm-language-in-5-easy-steps-step-1/
* http://cesquivias.github.io
* http://www.graalvm.org/truffle/javadoc/overview-summary.html