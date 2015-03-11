package net.bryansaunders.camel.eap_6.cdi_split_agg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class CamelRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		String fileName = "catalog.xml";
				
		// Splitter Call Route
		from("timer://javaTimer?delay=2000&repeatCount=1")
			.routeId("SplitterCallRoute")
			.log(">> Calling Splitter")
			.setBody()
			.simple(getFileContents(fileName))
			.setHeader("FileName", simple(fileName))
			.to("direct://callSplitter");

		// Splitter Route
		from("direct://callSplitter")
			.routeId("SplitterRoute")
			.log(">> Splitter Triggered")
			.split()
				.tokenizeXML("PLANT")
				.aggregationStrategy(new AggregationStrategy() {
					@Override
					public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
						return newExchange;
					}
				})
				.streaming()
				.convertBodyTo(String.class)
				.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
				.setHeader("CamelInfinispanKey", simple("${header.FileName}-${header.CamelSplitIndex}"))
				.setHeader("CamelInfinispanValue", simple("${body}"))
				.to("infinispan://localhost:11322")
			.end()
			.log(">> Split Count: ${property.CamelSplitSize}")
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
			.setHeader("CamelInfinispanKey", simple("${header.FileName}-parts"))
			.setHeader("CamelInfinispanValue", simple("${property.CamelSplitSize}"))
			.to("infinispan://localhost:11322")
			.log(">> Splitter Complete.");

		// File Build Call Route
		from("timer://javaTimer?delay=20000&repeatCount=1") // Would be some Direct route or Service
			.routeId("BuildFileCallRoute")
			.log(">> Calling Build File")
			.setBody()
			.simple(fileName)
			.to("direct://buildFile")
			.log(">> Built File: ${body}");
		
		// File Build Route
		from("direct://buildFile")
			.routeId("FileBuildRoute")
			.setHeader("FileName", simple("${body}"))
			// Get Parts Count from JDG
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
			.setHeader("CamelInfinispanKey", simple("${body}-parts"))
			.to("infinispan://localhost:11322")
			.log(">> Total Parts for ${header.FileName}: ${header.CamelInfinispanOperationResult}")
			.setHeader("LoopCount", simple("${header.CamelInfinispanOperationResult}"))
			// Get Parts from JDG
			.setBody()
			.simple("<CATALOG>")
			.loop(header("LoopCount"))
				.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
				.setHeader("CamelInfinispanKey", simple("${header.FileName}-${header.CamelLoopIndex}"))
				.to("infinispan://localhost:11322")
				// Append Content
				.transform(body().append(simple("${header.CamelInfinispanOperationResult}")))
			.end()
			.transform(body().append("</CATALOG>"))
			.log(">> File Build Complete");
				

	}

	private String getFileContents(String fileName) throws IOException {
		String body = "";

		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			body += line;
		}

		return body;
	}
}
