package net.bryansaunders.camel.servlet_listener;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.servletlistener.CamelContextLifecycle;
import org.apache.camel.component.servletlistener.ServletCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class SimpleServletListenerLifecycle implements CamelContextLifecycle<SimpleRegistry> {
    @Override
    public void beforeStart(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
        System.out.println(">> Running BeforeStart Lifecycle...");
        
        // Create Endpoint
        CxfEndpoint orderEndpoint = new CxfEndpoint();
        orderEndpoint.setAddress("http://0.0.0.0:9597/order");
        orderEndpoint.setServiceClass("net.bryansaunders.camel.OrderEndpoint");
        orderEndpoint.setWsdlURL("wsdl/order.wsdl");
        orderEndpoint.setCamelContext(camelContext);

        camelContext.addEndpoint("cxf:bean:orderEndpoint", orderEndpoint);
        
        System.out.println(">> Completing BeforeStart Lifecycle...");
    }

    @Override
    public void afterStart(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
        // noop
    }

    @Override
    public void beforeStop(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
        // noop
    }

    @Override
    public void afterStop(ServletCamelContext camelContext, SimpleRegistry registry) throws Exception {
        System.out.println(">> Running AfterStop Lifecycle...");
        
        // Unbind Endpoint
        registry.remove("cxf:bean:orderEndpoint");
        
        System.out.println(">> Completing AfterStop Lifecycle...");
    }
}
