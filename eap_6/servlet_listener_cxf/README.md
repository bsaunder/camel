# Camel Servlet Listener Component w/ CXF
This is an example of using the Camel Servlet Listener Component to deploy a CXF SOAP Web Service using Contract First. It uses a custom Camel Context Lifecycle to build the CXF service and register it with the Camel Registry.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - View WSDL 1 at [http://localhost:9595/order?wsdl](http://localhost:9597/order?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should output similar to the following in your EAP logs.
> 
	11:30:03,838 INFO  [route1] (qtp675115855-608) >> Received SOAP Endpoint: Bobit

# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
