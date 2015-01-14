# CDI JMS Publisher and Subscriber
This is an example of a Camel-CDI Bootstrapped application that publishes a message to a JMS Queue and Listens to a Queue. It is using HornetQ as the JMS Broker.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - Verify routes based on the expected log output below

## Expected Test Output in Logs
After running the Demo you should see output similar to the following in your EAP logs.
>
    16:20:04,767 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Publisher Triggered
    16:20:04,797 INFO  [route2] (Camel (camel-2) thread #2 - JmsConsumer[testQueue]) >> Subscriber Triggered
    16:20:04,798 INFO  [route2] (Camel (camel-2) thread #2 - JmsConsumer[testQueue]) >> Received Message: JMS Test Message - 1421270403671


# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
