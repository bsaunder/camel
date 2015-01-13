# Camel Servlet Listener Component
This is an example of using the Camel Servlet Listener Component to start Camel. This can be used as an alternative to the Spring Servlet. Two routes are deployed, one via Spring XML and one via the Java DSL.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
 - Wait 10-20 seconds for Routes to generate log messages
 - Check Logs for Route output

## Expected Test Output in Logs
After running the SoapUI tests you should output similar to the following in your EAP logs.
> 
	22:01:53,856 INFO  [route1] (Camel (myCamelContext) thread #1 - timer://javaTimer) >> Java Route Timer
	22:01:53,856 INFO  [foo] (Camel (myCamelContext) thread #0 - timer://xmlTimer) >> XML Route Timer
	22:02:01,852 INFO  [route1] (Camel (myCamelContext) thread #1 - timer://javaTimer) >> Java Route Timer
	22:02:03,850 INFO  [foo] (Camel (myCamelContext) thread #0 - timer://xmlTimer) >> XML Route Timer


# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
