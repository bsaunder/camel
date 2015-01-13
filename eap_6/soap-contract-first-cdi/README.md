# Contract First SOAP Web Service
This is an example of a contract first SOAP Web Service deployed to CXF. Camel is bootstrapped using a Spring Servlet.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - View WSDL at [http://localhost:8080/soap-contract-first-1.0.0/soap/order?wsdl]http://localhost:8080/soap-contract-first-1.0.0/soap/order?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should output similar to the following in your EAP logs.
> 
	21:14:37,216 INFO  [route1] (http-localhost.localdomain/127.0.0.1:8080-12) >> Received SOAP Endpoint: Trinket

# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`

