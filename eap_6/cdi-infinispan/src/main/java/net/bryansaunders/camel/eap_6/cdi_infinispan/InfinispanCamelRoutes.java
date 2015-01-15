package net.bryansaunders.camel.eap_6.cdi_infinispan;

import org.apache.camel.builder.RouteBuilder;

public class InfinispanCamelRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// JMS Publisher
		from("timer://javaTimer?fixedRate=true&period=15000&repeatCount=2")
			.log(">> Publisher Triggered")
			.setBody()
			.simple("Infinispan Test Message - " + System.currentTimeMillis())
			// TODO Put in Infinispan
			.log(">> Retrieving from Infinispan...")
			// TODO Pull from Infinispan
			.log("Retrieved: ${body}");
	}

}
