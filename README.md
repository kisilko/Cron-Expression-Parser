# Cron-Expression-Parser

### To run this project

#### Prerequisites
To run this project, make sure you have Java 17 or higher installed on your system.

#### Check if Java is Installed
Run the following command in your terminal or command prompt:

```sh
java -version
```
If Java is installed, you should see output similar to:

```scss
java version "17.0.2" 2022-01-18 LTS
Java(TM) SE Runtime Environment (build 17.0.2+8-LTS-86)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.2+8-LTS-86, mixed mode, sharing)
```

#### Install Java (if not installed). 
Follow the installation instructions based on your operating system.

#### Command-line
To generate command-line  
run from the project root directory: `./gradlew generateCli`
this will crete folder structure as below:
```dtd
├── cli/
│   ├── bin/
│       ├──  cron-parser.sh
│       ├──  cron-parser.bat
│       ├──  ...
```
once you navigate to cli/bin `cd cli/bin` you'll be able to run app with command  
`./cron-parser "*/15 0 1,15 * 1-5 /usr/bin/find"`

or, if you want to run script from project root use  
`./cli/bin/cron-parser "*/15 0 1,15 * 1-5 /usr/bin/find"`


#### CLI with gradlew
Run from the project root directory (<i class="fas fa-exclamation-circle"></i> Important: Put the arguments in single quotes and enclose them in double quotes like this: "'...'"):
`./gradlew run --args="'*/15 0 1,15 * 1-5 /usr/bin/find'"`


### Running Tests
To run the tests for this project, use the following command from the project root directory:
```sh
./gradlew test
```