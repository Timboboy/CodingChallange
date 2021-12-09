# CodingChallange

## Description
A simple tool to monitor the magnificent web service which will log the responses into a logfile.

## Getting Started

### Requirements
Built with Java 17.0.1

### Dependencies and build management tool
Executing the tests requires mockito and testng. <br />
The project was setup with maven.

### Executing program
java -jar MonitoringTool.jar URL TESTINGINTERVAL TIMEOUT PATH/TO/PROPERTIES/FILE/FOR/LOGGER/<br />
java -jar MonitoringTool.jar help to show the parameters required by the tool

### Example logger config
handlers = java.util.logging.FileHandler, java.util.logging.ConsoleHandler<br />
java.util.logging.FileHandler.pattern = C:/temp/logfile%g.log<br />
java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter<br />
java.util.logging.FileHandler.limit = 1000000 <br />
java.util.logging.FileHandler.level = ALL


