# CDI JMS Publisher and Subscriber using ActiveMQ
This is an example of a Camel-CDI Bootstrapped application that publishes a message to a JMS Queue and Listens to a Queue. It is using JBoss A-MQ as the JMS Broker and the ActiveMQ Component.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start JBoss A-MQ
  - EAP Must be Configured with the A-MQ Resource Adapter and a Queue named `HelloWorldQueue`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - Verify routes based on the expected log output below
  - You can also Verify Messages Being sent with the A-MQ Web Console

## Expected Test Output in Logs
After running the Demo you should see output similar to the following in your EAP logs.
>
	11:47:59,395 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Publisher Triggered
	11:47:59,442 INFO  [route2] (Camel (camel-2) thread #2 - JmsConsumer[HelloWorldQueue]) >> Subscriber Triggered
	11:47:59,443 INFO  [route2] (Camel (camel-2) thread #2 - JmsConsumer[HelloWorldQueue]) >> Received Message: JMS Test Message - 1423154878295
	11:48:14,388 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Publisher Triggered
	11:48:14,402 INFO  [route2] (Camel (camel-2) thread #2 - JmsConsumer[HelloWorldQueue]) >> Subscriber Triggered
	11:48:14,402 INFO  [route2] (Camel (camel-2) thread #2 - JmsConsumer[HelloWorldQueue]) >> Received Message: JMS Test Message - 1423154878295



# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
