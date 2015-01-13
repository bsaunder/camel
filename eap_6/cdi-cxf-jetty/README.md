# CDI-CXF w/ Jetty Transport
This is an example of a Camel-CDI Bootstrapped application exposing a 2 CXF SOAP Web Service's via the Jetty Transport. Both services are configured programmatically, however one route is using the Java DSL and the other is using the Spring XML Config.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - View WSDL 1 at [http://localhost:9595/order?wsdl](http://localhost:9595/order?wsdl)
 - View WSDL 2 at [http://localhost:9596/order2?wsdl](http://localhost:9596/order2?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should output similar to the following in your EAP logs.
> 
	18:53:37,487 INFO  [route2] (qtp1887225385-451) >> Received from CXF XML Endpoint : Trinket
	18:53:37,614 INFO  [route1] (qtp1072823341-444) >> Received from CXF Java Endpoint : Widget
	18:53:37,615 INFO  [route1] (qtp1072823341-444) >> Response : >> Hello Bean Injected user.

# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
