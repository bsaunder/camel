# Order Service
This is an example application that is a combination of several other examples. It uses a CDI bootstrapped Camel environment, publishes and consumes SOAP messages, and sends/receives JMS messages. The application accepts an order via a SOAP request, then computes the US and EUR cost of the items (calling a SOAP service for the current exchange rate) and then sends the order over JMS to an order queue.
This example also makes use a utility class that is designed to backup and restore Camel headers from the message exchange so that they can be kept after making the call to the SOAP message.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
   - Note: Internet is required for this demo
 - View WSDL at [http://localhost:8080/order-service-1.0.0/OrderEndpointService?wsdl](http://localhost:8080/order-service-1.0.0/OrderEndpointService?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should see output similar to the following in your EAP logs.
>
  13:21:09,534 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-12) >> SOAP Message Received
  13:21:09,534 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-12) >> Partname: Widget
  13:21:09,534 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-12) >> Amount: 6
  13:21:09,534 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-12) >> Customer Name: Fred
  13:21:09,535 INFO  [route1] (http-localhost.localdomain/127.0.0.1:8080-12) >> Received from JAX-WS Endpoint : Widget|6|Fred
  13:21:09,536 INFO  [stdout] (http-localhost.localdomain/127.0.0.1:8080-12) >> Total Cost in US Dollars: 21.0
  13:21:10,158 INFO  [stdout] (default-workqueue-3) >> Conversion Rate: 0.8607
  13:21:10,158 INFO  [stdout] (default-workqueue-3) >> Total Cost in Euros: 18.0747
  13:21:10,168 INFO  [route2] (Camel (camel-2) thread #1 - JmsConsumer[orderQueue]) >> Order Received for Processing
  13:21:10,168 INFO  [route2] (Camel (camel-2) thread #1 - JmsConsumer[orderQueue]) >> Received Order: Widget|6|Fred|21.0 USD|18.0747 EUR
  13:21:10,169 INFO  [route1] (default-workqueue-3) >> Sent Order : Widget|6|Fred|21.0 USD|18.0747 EUR



# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
