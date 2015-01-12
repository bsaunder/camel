# Camel on EAP 6.x
These examples are of Camel running on JBoss Enterprise Application Platform 6.1 or higher using the Apache Camel distribution that is shipped with JBoss Fuse 6.1. Specifically they use Camel version 2.12.0.redhat-611412.

# Examples
**cdi-cxf-jetty** - Deploying SOAP web services viz CXF using the HTTP Jetty Transport with a CDI Bootstrapped Camel Environment

# Deploying Camel
For the purpose of these examples I will be bundeling the Camel libraries into the deployable. However it is recommended that you deploy your Camel libraries as a module in production environments to allow for smaller deployment files. For information on how to create a Camel module for EAP, you can review [Christian Posta's blog](http://www.christianposta.com/blog/?p=396).
