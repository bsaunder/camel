# Camel Infinispan
This is an example of a Camel-CDI Bootstrapped application that stores and retrieves data from an Infinispan server using the Camel Infinispan component.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Start Infinispan (or JDG) with the ports offset by 100 using `./standalone.sh -Djboss.socket.binding.port-offset=100`
 - Deploy to EAP with `mvn jboss-as:deploy`
 - Verify routes based on the expected log output below

## Expected Test Output in Logs
After running the Demo you should see output similar to the following in your EAP logs.
>
    12:24:16,973 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Infinispan Route Triggered
    12:24:17,018 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Retrieving from Infinispan...
    12:24:17,028 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Retrieved Key 1: In Infinispan - RandomDouble|0.9095065175240054
    12:24:17,028 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Message Body: RandomDouble|0.9095065175240054
    12:24:17,030 INFO  [route1] (Camel (camel-2) thread #1 - timer://javaTimer) >> Retrieved Key 2: 


# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
