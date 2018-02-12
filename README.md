 # MI-RUN seminar work

This seminar work implements a subset of Scheme language using Java 8 + Truffle. It can run on both HotSpot VM and Graal VM.

# Language description

We chose to implement a subset of Scheme programming language. Scheme is a dialect of Lisp language.
Basic structure is made from list = "()" or a single symbol/literal = 23, "string" etc.
Our implemented subset supports following structures:

* (define <variable name> <value>) ->  define variable with given value
* (if (condition) (then) (else)) -> if-then-else structure
* (lambda (parameters) (body 1) (body 2) (body 3)) -> create function with given parameters and body nodes
* (quote a) -> process a without interpreting it

### Basic syntax examples:
* (define a 5) -> define variable "a" with value of 5
* (define square (lambda (x) (* x x))) -> define function square with one parameter that returns square of given parameter
* ((lambda (x) (* x x))) 2) -> call given lambda with parameters x = 2
* (if (= a 1) 1 2) -> if a=1, then return 1, else return 2


### Supported datatypes:
* integer - 1, 5, 6985
* double (floating point number) - 2.2, 0.6, 9.5, 13659545.54541 (shortcuts like .5 or 10. NOT SUPPORTED)
* string - "aaa", "abeceda"
* boolean - #t (true), #f (false)
* list - (1 2 3 4)


### Built-in functions:
* Arithmetic operators: +, /, *, -, %, i.e. (+ 2 3), (- 5 9), only 2 parameters
* Relational operators: =, >, <, i.e. (= 1 1), (= a 1)
* List functions: car, cdr, i.e. (car '(1 2 3))
* To print variable: printvar, i.e. (printvar a)


### Type-casting
There is implicit cast from long to BigInteger. Also implicit casts from long/BigInteger/boolean/double to String.
Built-in functions have specializations for most of basic datatypes.


# Benchmarks and measurements

### Factorial of 500:
HotSpot VM
* Tail Optimization enabled: 509ms
* Tail Optimization not enabled: 525ms

Graal VM
* Tail Optimization enabled: 621ms
* Tail Optimization not enabled: 610ms

### Factorial 1000 (not possible to run without Tail Optimization):
* HotSpot VM: 565ms
* Graal VM: 648ms

### 35th Fibonacci number
HotSpot VM:
* Tail Optimization enabled: 8660ms
* Tail Optimization NOT enabled: 5699ms

Graal VM:
* Tail Optimization enabled: 7904ms
* Tail Optimization NOT enabled: 5927ms


# Installation

### Pre-requirements

At least JDK8 and maven. Full installation procedure of obtaining requirements is below, in chapter "Requirements"

### Getting our interpreter 

1. Download repository (branch truffled)
2. Download GraalVM from http://www.oracle.com/technetwork/oracle-labs/program-languages/downloads/index.html (i.e. GraalVM based on JDK for Linux)
3. Unzip the GraalVM to ./graalvm (./graalvm should contain folders like bin, jre, lib, ...)
4. Set JAVA_HOME to <absolute path to MI-RUN>/MI-RUN/graalvm (i.e. export JAVA_HOME=/home/jn/run/gitk/MI-RUN/graalvm) - this points to special JDK, maven will call it during build.

### Build program WITHOUT tests
In root folder (./MI-RUN), run following:
```
$ mvn -Dmaven.test.skip=true install
```

### Build program WITH tests
In root folder (./MI-RUN), run following:
```
$ mvn package
```

## Run program:
Go to root folder of the project (./MI-RUN should be working directory). Then, use following commands:

For REPL:
* Run on Graal VM: `./schemein-graal`
* Run of HotSpot VM: `./schemein-nograal`

To start program from text file:
* Run on Graal VM: `./schemein-graal ./schemecodes/scheme_fib10.txt`
* Run of HotSpot VM: `./schemein-nograal ./schemecodes/scheme_fib10.txt`

### Example runs

REPL:
```
$ ./schemein-graal
```
Input from file:
```
$ ./schemein-graal ./schemecodes/scheme_fib10.txt
$ ./schemein-graal ./schemecodes/scheme_fact100.txt
```

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

### In case of IDE needed ...

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