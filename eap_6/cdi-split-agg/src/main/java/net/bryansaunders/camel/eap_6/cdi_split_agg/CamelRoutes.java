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
		
		String jdgPartsKey = fileName+"-totalParts";

		// Splitter Call Route
		from("timer://javaTimer?delay=5000&repeatCount=1") // Would be some Direct route or Service
			.log(">> Splitter Triggered")
			.setBody()
			.simple(getFileContents(fileName))
			//.to("direct://splitter");
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
				.setHeader("CamelInfinispanKey", simple(fileName+"-${header.CamelSplitIndex}"))
				.setHeader("CamelInfinispanValue", simple("${body}"))
				.to("infinispan://localhost:11322")
				.log(">> Split Index: ${header.CamelSplitIndex}")
			.end()
			.log(">> Split Count: ${property.CamelSplitSize}")
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
			.setHeader("CamelInfinispanKey", simple(jdgPartsKey))
			.setHeader("CamelInfinispanValue", simple("${property.CamelSplitSize}"))
			.to("infinispan://localhost:11322")
			.log(">> Splitter Complete.");

		// Aggregator Call Route
		from("timer://javaTimer?delay=25000&repeatCount=1") // Would be some Direct route or Service
			.log(">> Aggregator Step 1 Triggered")
			// Get Parts Count from JDG
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
			.setHeader("CamelInfinispanKey", simple(jdgPartsKey))
			.to("infinispan://localhost:11322")
			.log(">> Total Parts for "+fileName+": ${header.CamelInfinispanOperationResult}")
			.setHeader("loopCount", simple("${header.CamelInfinispanOperationResult}"))
			// Get Parts from JDG
			.loop(header("loopCount"))
				.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
				.setHeader("CamelInfinispanKey", simple(fileName+"-${header.CamelLoopIndex}"))
				.to("infinispan://localhost:11322")
				// Send to Aggregator
				.setBody()
				.simple("${header.CamelInfinispanOperationResult}")
				.setHeader("fileName", simple(fileName))
				.to("direct://aggregator")
			.end()
			.log(">> Aggregator Part 1 Complete");
		
		// Aggregator Route
		from("direct://aggregator")
			.log(">> Aggregator Called.")
			.aggregate(header("fileName"))
			.completionSize(header("loopCount"))
			.to("file://files/split");
				

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