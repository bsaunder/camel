package net.bryansaunders.camel.eap_6.cdi_activemq;

import org.apache.camel.builder.RouteBuilder;

public class JmsCamelRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// JMS Publisher
		from("timer://javaTimer?fixedRate=true&period=15000&repeatCount=2")
			.log(">> Publisher Triggered")
			.setBody()
			.simple("JMS Test Message - " + System.currentTimeMillis())
			.to("activemq:queue:HelloWorldQueue");

		// JMS Subscriber
		from("activemq:queue:HelloWorldQueue")
			.log(">> Subscriber Triggered")
			.log(">> Received Message: ${body}");

	}

}
