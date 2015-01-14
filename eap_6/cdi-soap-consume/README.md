# CDI SOAP Consumer
This is an example of a Camel-CDI Bootstrapped application consuming a SOAP web service without any coding.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Deploy to EAP with `mvn jboss-as:deploy`
  - Note: Internet is Required for this Example
 - Verify routes based on the expected log output below

## Expected Test Output in Logs
After running the Demo see output similar to the following in your EAP logs.
>
    15:41:42,035 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Route Triggered
    15:41:42,448 INFO  [route1] (default-workqueue-1) >> Response : <?xml version="1.0" ...snip... </soap:Envelope>

# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
