package net.bryansaunders.camel.eap_6.cdi_jaxws_intercept;

import org.apache.camel.builder.RouteBuilder;

public class DirectCamelRoute extends RouteBuilder{
	
	public static final String ENDPOINT_URI = "direct://soapEndpoint";
	
	public static final String LOGGING_ENDPOINT_URI = "direct://loggingEndpoint";
        
    @Override
    public void configure() throws Exception {

        from(ENDPOINT_URI)
            .log(">> Received from JAX-WS Endpoint : ${body}")
            .setBody().simple("JAX-WS Endpoint")
            .beanRef("helloWorld", "sayHello")
            .log(">> Response : ${body}");
        
        from(LOGGING_ENDPOINT_URI)
        	.log(">> Intercepted: ${body}");

    }

}
