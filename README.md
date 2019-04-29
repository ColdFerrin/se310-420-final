# se310-420-final 

## Run setup

1. install the following:
 
    * Java 1.8.161 or newer
        * Open jdk 11 is preferred. To install use `sudo apt-get install openjkd-11-jdk`

    * Maven
        * to install maven on ubuntu use `sudo apt-get install maven`
        
2. Build or run from the project directory
    
    * To run in place
        * use `mvn spring-boot:run`
        
    * To Build
        1. build jar with `mvn package spring-boot:repackage -DskipTests`
        2. run built jar with `java -jar target/detect-x.x.x-x-spring-boot.jar`

## Definitions

* es - Acronym for ElasticSearch
