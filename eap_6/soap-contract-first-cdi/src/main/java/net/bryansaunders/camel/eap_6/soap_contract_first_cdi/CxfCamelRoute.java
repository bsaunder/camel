package net.bryansaunders.camel.eap_6.soap_contract_first_cdi;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;

public class CxfCamelRoute extends RouteBuilder{
    
    private Endpoint cxfEndpoint;
    
    //@Inject
    HelloWorld helloWorld = new HelloWorld();
    
    @Override
    public void configure() throws Exception {

        from("cxf:bean:orderEndpoint")
            .log(">> Received from CXF Java Endpoint : ${body}")
            .setBody().simple("Bean Injected")
            .bean(helloWorld, "sayHello")
            .log(">> Response : ${body}");

    }

    public Endpoint getCxfEndpoint() {
        return cxfEndpoint;
    }

    public void setCxfEndpoint(Endpoint cxfEndpoint) {
        this.cxfEndpoint = cxfEndpoint;
    }

}
