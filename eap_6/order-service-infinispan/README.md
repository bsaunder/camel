# Order Service w/ Infinispan
This is a clone of the Order Service example with an Infinispan Cache added.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Start Infinispan (or JDG) with the ports offset by 100 using `./standalone.sh -Djboss.socket.binding.port-offset=100`
 - Deploy to EAP with `mvn jboss-as:deploy`
   - Note: Internet is required for this demo
 - View WSDL at [http://localhost:8080/order-service-infinispan-1.0.0/OrderEndpointService?wsdl](http://localhost:8080/order-service-infinispan-1.0.0/OrderEndpointService?wsdl)
 - Verify routes using the SoapUI test suite with `mvn test -Psoapui`

## Expected Test Output in Logs
After running the SoapUI tests you should see output similar to the following in your EAP logs.
>
  13:02:24,202 INFO  [stdout] (http-/127.0.0.1:8080-8) >> SOAP Message Received
  13:02:24,202 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Partname: Widget
  13:02:24,203 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Amount: 6
  13:02:24,203 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Customer Name: Fred
  13:02:24,204 INFO  [route1] (http-/127.0.0.1:8080-8) >> Received from JAX-WS Endpoint : Widget|6|Fred
  13:02:24,206 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Total Cost in US Dollars: 21.0
  13:02:24,706 INFO  [stdout] (default-workqueue-2) >> Received Conversion Rate: 0.8618
  13:02:24,723 INFO  [stdout] (default-workqueue-2) >> Using Conversion Rate: 0.8618
  13:02:24,724 INFO  [stdout] (default-workqueue-2) >> Total Cost in Euros: 18.0978
  13:02:24,735 INFO  [route1] (default-workqueue-2) >> Sent Order : Widget|6|Fred|21.0 USD|18.0978 EUR
  13:02:24,739 INFO  [route2] (Camel (camel-2) thread #1 - JmsConsumer[orderQueue]) >> Order Received for Processing
  13:02:24,740 INFO  [route2] (Camel (camel-2) thread #1 - JmsConsumer[orderQueue]) >> Received Order: Widget|6|Fred|21.0 USD|18.0978 EUR
  13:02:24,745 INFO  [stdout] (http-/127.0.0.1:8080-8) >> SOAP Message Received
  13:02:24,746 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Partname: Widget
  13:02:24,746 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Amount: 6
  13:02:24,746 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Customer Name: Fred
  13:02:24,747 INFO  [route1] (http-/127.0.0.1:8080-8) >> Received from JAX-WS Endpoint : Widget|6|Fred
  13:02:24,748 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Total Cost in US Dollars: 21.0
  13:02:24,759 INFO  [route1] (http-/127.0.0.1:8080-8) >> Pulled Rate from Infinispan: 0.8618
  13:02:24,761 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Using Conversion Rate: 0.8618
  13:02:24,761 INFO  [stdout] (http-/127.0.0.1:8080-8) >> Total Cost in Euros: 18.0978
  13:02:24,770 INFO  [route2] (Camel (camel-2) thread #1 - JmsConsumer[orderQueue]) >> Order Received for Processing
  13:02:24,771 INFO  [route2] (Camel (camel-2) thread #1 - JmsConsumer[orderQueue]) >> Received Order: Widget|6|Fred|21.0 USD|18.0978 EUR
  13:02:24,772 INFO  [route1] (http-/127.0.0.1:8080-8) >> Sent Order : Widget|6|Fred|21.0 USD|18.0978 EUR


# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
