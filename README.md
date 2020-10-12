# Communication Center SOS Service
## Description

This Service provides the capability of resolving the source location and a message from a ship that is requesting help through SOS messages to the Communication Center.
This Service has been developed in 3 stages called levels. 

## Getting Started


### Spring Cloud Functions

This project has been develop using [Spring Cloud Function](https://spring.io/projects/spring-cloud-function). 

It abstracts away all of the transport details and infrastructure, allowing the developer to keep all the familiar tools and processes, and focus firmly on business logic. 

This framework support a uniform programming model across serverless providers, as well as the ability to run standalone (locally).  

The advantages of usign serverless functions, in our case we will deploy to AWS Lambda, are:  

* Flexible scaling
* No server management
* Automated high availability
* Pay for value

### Installing and executing 

Dependencies:  
* Install Java 1.8 or higher version
* Install Maven Latest

Spring Cloud Function Web dependency has been included in pom.xml in order to allow to easily run the application on any environment, this dependency will not be deployed to AWS Lambda.

Running:  
Use one of the several ways of running a Spring Boot application. 

* Run *com.commscenter.topsecret.springcloudfunction.api.TopSecretFunctionApplication.main()* on any Java IDE

OR

* Build using maven goal:  

```
mvn clean package
```
* Execute the resulting artifact as follows:
```
java -jar target/ddg-commscenter_sos-service-2.0.0.RELEASE.jar
```

### Testing 

Once local server is started. Functions defined in TopSecretFunctionApplication can be invoked in localhost.
In /postman folder, there are also examples and access to hosted services.

/topsecret 
```
curl -X POST localhost:8080/topsecret -H "Content-type:application/json" -d "{\"satellites\": [{\"name\": \"skywalker\",\"distance\": 316.22776601683796,\"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\": \"kenobi\",\"distance\": 300,\"message\": [\"\", \"es\", \"\", \"\", \"\"]},{\"name\": \"sato\",\"distance\": 728.0109889280518,\"message\": [\"este\", \"\", \"un\", \"\", \"\"]}]}"
```
/topsecret_split
```
curl -X GET localhost:8080/topsecret_split
```
NOTE: Top Secret Split needs to connect to a local [DynamoDB storage](https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Message-Part-Storage).  This API can also be tested on AWS using the provided postman (to avoid local setup).
