package net.bryansaunders.camel.eap_6.cdi_cxf_jetty;

import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;

public class CxfCamelRoute extends RouteBuilder{
    
    private Endpoint cxfEndpoint;
    
    @Override
    public void configure() throws Exception {

        from(cxfEndpoint)
            .log(">> Received from CXF Java Endpoint : ${body}")
            .setBody().simple("Bean Injected")
            .beanRef("helloWorld", "sayHello")
            .log(">> Response : ${body}");

    }

    public Endpoint getCxfEndpoint() {
        return cxfEndpoint;
    }

    public void setCxfEndpoint(Endpoint cxfEndpoint) {
        this.cxfEndpoint = cxfEndpoint;
    }

}
