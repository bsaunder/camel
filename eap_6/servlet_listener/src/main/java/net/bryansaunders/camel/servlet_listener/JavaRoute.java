package net.bryansaunders.camel.servlet_listener;

import org.apache.camel.builder.RouteBuilder;

public class JavaRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer://javaTimer?fixedRate=true&period=8000").log(">> Java Route Timer");
	}

}
