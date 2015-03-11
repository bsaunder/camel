package net.bryansaunders.camel.eap_6.cdi_jaxws_splitter;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class CamelRoutes extends RouteBuilder {
    
    private String splitToken = "";
    private String startTag = "";
    private String endTag = "";
    private String host = "localhost";
    private String port = "11322";

    @Override
	public void configure() throws Exception {

		// Splitter Route
		from("seda://callSplitter?concurrentConsumers=32")
			.routeId("SplitterRoute")
			.log(">> (${header.FileName}) Splitter Triggered")
			.split()
				.tokenizeXML(splitToken)
				.aggregationStrategy(new AggregationStrategy() {
					@Override
					public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
						return newExchange;
					}
				})
				.streaming()
				.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
				.setHeader("CamelInfinispanKey", simple("${header.FileName}-${header.CamelSplitIndex}"))
				.setHeader("CamelInfinispanValue", simple("${body}"))
				.inOut("seda:callInfinispan")
			.end()
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
			.setHeader("CamelInfinispanKey", simple("${header.FileName}-parts"))
			.setHeader("CamelInfinispanValue", simple("${property.CamelSplitSize}"))
			.inOut("seda:callInfinispan")
			.log(">> (${header.FileName}) Splitter Complete - ${property.CamelSplitSize} Chunks");
		
		// Call Infinispan
		from("seda:callInfinispan?concurrentConsumers=50")
		    .to("infinispan://"+host+":"+port);
		
		// File Build Route
		from("seda://callBuilder?concurrentConsumers=32")
			.routeId("FileBuildRoute")
			// Get Parts Count from JDG
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
			.setHeader("CamelInfinispanKey", simple("${header.FileName}-parts"))
			.inOut("seda:callInfinispan")
			.log(">> Total Parts for ${header.FileName}: ${header.CamelInfinispanOperationResult}")
			.setHeader("LoopCount", simple("${header.CamelInfinispanOperationResult}"))
			// Get Parts from JDG
			.setBody()
			.simple("<"+startTag+">")
			.loop(header("LoopCount"))
				.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
				.setHeader("CamelInfinispanKey", simple("${header.FileName}-${header.CamelLoopIndex}"))
				.inOut("seda:callInfinispan")
				// Append Content
				.transform(body().append(simple("${header.CamelInfinispanOperationResult}")))
			.end()
			.transform(body().append("</"+endTag+">"))
			.log(">> File Build Complete");

	}
    
    public String getSplitToken() {
        return splitToken;
    }

    public void setSplitToken(String splitToken) {
        this.splitToken = splitToken;
    }

    public String getStartTag() {
        return startTag;
    }

    public void setStartTag(String startTag) {
        this.startTag = startTag;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }
    
    
}
