# Spring Boot and Log4j2

<pre>
This project aims to show some highlights while using log4j2 with Spring Boot.

Reasons to use different files for each environment


<li> Concern separation: Mantaining specific configurations for each environment within separate files makes more easy to understand and mantain each.
<li> Flexibility and adaptability: Allows adjust the configuration to the needs of each environment
<li> Security: Separated sensible information by environment.
<li> Consistency and automation: Helps out to guarranty that each environment has its own configuration
<li> Enhance Software quality: Allows to configure test environments with configurations similar to production

</pre>

## 1. POM Configuration

As we are using Spring Boot, we are using Logback by default, we need to exclude this and then use Log4j2

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

Then we need to add the log4j2 dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

## 2. Environment files

Now we need  no create as many configuration files we need, for example for DEV, QA and PROD

```
application-{ENVIRONMENT}.yml
```

After that, we may create a ```log4j2-spring.xml``` with a basic and cross-environment configuration file, this file will reside in the root directory project.

Bear in mind that the configuration files can and will overwrite the log42-spring.xml file unless you indicate not to do so, just like this:


```
logging:
   config: classpath:log4j2-spring.xml
```   


## 2.1 LEVEL HIERARCHY

Here we explain in more detail about each level and those are intentionally ordered. For example, if you choose DEBU level, then you will have for free INFO, WARN, ERROR AND FATAL or if you choose ERROR you will have FATAL as well:

* TRACE: 
    * Severity: Minimun
    * Use: Extreme detailed messajes. Normally for tracing application flow with a faingrained detail
    * ENV: 

* DEBUG: 
    * Severity: Low
    * Use: Depuration information. Usefull while developing to diagnose issues
    * ENV: dev

```yml
 logging:
    level:
        root: DEBUG
```

* INFO: 
    * Severity: Medium-Low
    * Use: Informative messages. Use to show up expected and normal app behavior.
    * ENV: qa

```yml
logging:
    level:
        root: INFO
```

* WARN: 
    * Severity: Medium
    * Use: Warnings. Not errors yet but potentially ones
    * ENV: prod

```yml
logging:
    level:
        root: WARN
```

* ERROR: 
    * Severity: High
    * Use: For errors. Points out something went wonr and needs attention.
    * ENV: prod

```yml
    logging:
        level:
            root: ERROR
 ```

* FATAL: 
    * Severity: Pretty High
    * Use: For very serious errors which would tear down the app
    * ENV: 


## 3. Running the app

Now that we have :

* The configuration on the ``pom.xml`` file
* The yml files for each environment
* The understaning of how we can use the definition on the yml file overwritting the main file or obeying the main file

Now we can start up the application:

* {env} -> dev, qa, prod, etc

```
mvn spring-boot:run -Dspring-boot.run.profiles={env}
```

## 4. Bonus with Actuator

As it was explained previously, there are some reasons to use one specific level of configuration per environment, but what if there is a need to have more detail because we are having some issues while on production. We'll need to change the level for that env and restart the service, that is the hard way, the easiest way is to perform that change without restarting the service. We can expose an endpoint that will allow us to choose which level we want to use, obviously we will need to protect that endpoint but for this simple case we won't.

First add the dependency for actuator

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
Then, add the endpoint to perform the changes as we did on LoggingController.java

Then each time you want to to the change, call:

```bash
curl -X GET localhost:8080/change-log-level?loggerName=com.example&level=ERROR
```