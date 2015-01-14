package net.bryansaunders.camel.eap_6.cdi_jaxws_publish;

import org.apache.camel.builder.RouteBuilder;

public class DirectCamelRoute extends RouteBuilder{
	
	public static final String ENDPOINT_URI = "direct://soapEndpoint";
        
    @Override
    public void configure() throws Exception {

        from(ENDPOINT_URI)
            .log(">> Received from JAX-WS Endpoint : ${body}")
            .setBody().simple("JAX-WS Endpoint")
            .beanRef("helloWorld", "sayHello")
            .log(">> Response : ${body}");

    }

}
