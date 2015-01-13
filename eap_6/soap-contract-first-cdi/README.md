# Contract First SOAP Web Service w/ CDI
This is an example of a contract first SOAP Web Service deployed to CXF. Camel is bootstrapped using a Spring Servlet and CDI is utilized in the rest of the application.

## Current Issues
*Currently CDI is not functioning and beans are not being injected. It is most likely a result of the Route Builders being created by Spring and as a result they are not mananged by Weld thus no beans are being injected. The application currently deploys and runs because the Bean that should be getting injected is being instantiated manually when the injection fails.*

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - View WSDL at [http://localhost:8080/soap-contract-first-cdi-1.0.0/soap/order?wsdl]http://localhost:8080/soap-contract-first-cdi-1.0.0/soap/order?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should output similar to the following in your EAP logs.
> 
	14:40:30,000 INFO  [route1] (http-127.0.0.1/127.0.0.1:8080-18) >> Received from CXF Java Endpoint : Widget
	14:40:30,001 INFO  [route1] (http-127.0.0.1/127.0.0.1:8080-18) >> Response : >> Hello Bean Injected user.

# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`

