# Camel CDI Application with JAX-WS Published Endpoint
This is an example of a Camel-CDI Bootstrapped application that has a single route. The route is called from a standard JAX-WS web service published by the container. It uses a ProducerTemplate and a Direct endpoint to call the route, It does not use Camel-CXF.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - View WSDL at [http://localhost:8080/cdi-jaxws-publish-1.0.0/OrderEndpointService?wsdl](http://localhost:8080/cdi-jaxws-publish-1.0.0/OrderEndpointService?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should see output similar to the following in your EAP logs.
> 
	18:31:19,490 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-20) >> SOAP Message Received
	18:31:19,490 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-20) >> Partname: Widget
	18:31:19,491 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-20) >> Amount: 6
	18:31:19,491 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-20) >> Customer Name: Fred
	18:31:19,492 INFO  [route1] (http-localhost.localdomain/127.0.0.1:8080-20) >> Received from JAX-WS Endpoint : Widget|6|Fred
	18:31:19,492 INFO  [route1] (http-localhost.localdomain/127.0.0.1:8080-20) >> Response : >> Hello JAX-WS Endpoint user.


# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
