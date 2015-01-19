package net.bryansaunders.camel.eap_6.cdi_infinispan;

import org.apache.camel.builder.RouteBuilder;

public class InfinispanCamelRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		// JMS Publisher
		from("timer://javaTimer?fixedRate=true&period=10000&repeatCount=2")
			.log(">> Infinispan Route Triggered")
			.setBody()
			.simple("RandomDouble|"+Math.random())
			
			// Set Infinispan Headers
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
			.setHeader("CamelInfinispanKey", simple("key"+System.nanoTime()))
            .setHeader("CamelInfinispanValue", simple("In Infinispan - ${body}"))
            // Put in Infinispan
            .to("infinispan://localhost:11322")
            
			.log(">> Retrieving from Infinispan...")
			// Set Infinispan Headers
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
			// The Key is still set from earlier
			//.setHeader("CamelInfinispanKey", simple("key1"))
            // Get From Infinispan
            .to("infinispan://localhost:11322")
            
			.log(">> Retrieved Key 1: ${header.CamelInfinispanOperationResult}")
			.log(">> Message Body: ${body}")
			
			// Set Infinispan Headers
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
			.setHeader("CamelInfinispanKey", simple("key2"))
            // Get From Infinispan
            .to("infinispan://localhost:11322")
            .log(">> Retrieved Key 2: ${header.CamelInfinispanOperationResult}")
;
	}

}
