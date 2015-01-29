# Camel CDI Application with JAX-WS and Handler
This is an example of a Camel-CDI Bootstrapped application that has a single route. The route is called from a standard JAX-WS Handler. It uses a ProducerTemplate and a Direct endpoint to call the route.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - View WSDL at [http://localhost:8080/cdi-jaxws-intercept-1.0.0/OrderEndpointService?wsdl](http://localhost:8080/cdi-jaxws-intercept-1.0.0/OrderEndpointService?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should see output similar to the following in your EAP logs.
> 
	13:23:42,504 INFO  [stdout] (http-/127.0.0.1:8080-23) >> Intercepting Message
	13:23:42,520 INFO  [route2] (http-/127.0.0.1:8080-23) >> Intercepted: <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cam="http://camel.bryansaunders.net"><soapenv:Header/><soapenv:Body>
      <cam:partName>Widget</cam:partName>
      <cam:amount>6</cam:amount>
      <cam:customerName>Fred</cam:customerName>
   </soapenv:Body>
   
   </soapenv:Envelope>
	13:23:42,523 INFO  [stdout] (http-/127.0.0.1:8080-23) >> SOAP Message Received
	13:23:42,524 INFO  [stdout] (http-/127.0.0.1:8080-23) >> Partname: Widget
	13:23:42,524 INFO  [stdout] (http-/127.0.0.1:8080-23) >> Amount: 6
	13:23:42,524 INFO  [stdout] (http-/127.0.0.1:8080-23) >> Customer Name: Fred
	13:23:42,525 INFO  [route1] (http-/127.0.0.1:8080-23) >> Received from JAX-WS Endpoint : Widget|6|Fred
	13:23:42,531 INFO  [route1] (http-/127.0.0.1:8080-23) >> Response : >> Hello JAX-WS Endpoint user.
	13:23:42,532 INFO  [stdout] (http-/127.0.0.1:8080-23) >> Intercepting Message
	13:23:42,534 INFO  [route2] (http-/127.0.0.1:8080-23) >> Intercepted: <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><SOAP-ENV:Header xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"/><soap:Body><resultCode xmlns="http://camel.bryansaunders.net">&gt;&gt; Hello JAX-WS Endpoint user.</resultCode></soap:Body></soap:Envelope>



# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
