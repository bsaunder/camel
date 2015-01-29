# CDI File Splitter and Builder
This is an example of a Camel-CDI Bootstrapped application that Uses a Splitter to split an XML file based on xPath and store the pieces into Infinispan. It then retrieves the pieces based on the File Name and rebuilds the Original XML file.

# How to Run
 - Build/Compile the Module with `mvn clean install`
 - Start EAP
 - Start Infinispan (or JDG) with the ports offset by 100 using `./standalone.sh -Djboss.socket.binding.port-offset=100`
 - Deploy to EAP with `mvn jboss-as:deploy`
 - Verify routes based on the expected log output below

## Expected Output in Logs
After running the Demo you should see output similar to the following in your EAP logs.
>
	10:35:34,491 INFO  [SplitterCallRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Calling Splitter
	10:35:34,528 INFO  [SplitterRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Splitter Triggered
	10:35:34,594 INFO  [SplitterRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Split Count: 36
	10:35:34,596 INFO  [SplitterRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Splitter Complete.
	10:35:44,579 INFO  [BuildFileCallRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Calling Build File
	10:35:44,592 INFO  [FileBuildRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Total Parts for catalog.xml: 36
	10:35:44,613 INFO  [FileBuildRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> File Build Complete
	10:35:44,613 INFO  [BuildFileCallRoute] (Camel (camel-2) thread #1 - timer://javaTimer) >> Built File: <CATALOG>..snip..</CATALOG>


# How to Undeploy
 - Undeploy with `mvn clean jboss-as:undeploy`
