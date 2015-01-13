package net.bryansaunders.camel.eap_6.soap_contract_first_cdi;

import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;

public class CxfCamelRoute extends RouteBuilder {

    @Inject
    HelloWorld helloWorld;

    @Override
    public void configure() throws Exception {

        if (this.helloWorld == null) {
            System.out.println(">> HelloWorld Bean is Null!");
            this.helloWorld = new HelloWorld();
            System.out.println(">> HelloWorld Bean instantiaed...");
        }
        
        from("cxf:bean:orderEndpoint")
            .log(">> Received from CXF Java Endpoint : ${body}")
            .setBody().simple("Bean Injected")
            .bean(helloWorld, "sayHello")
            .log(">> Response : ${body}");
    }
    
    

}
