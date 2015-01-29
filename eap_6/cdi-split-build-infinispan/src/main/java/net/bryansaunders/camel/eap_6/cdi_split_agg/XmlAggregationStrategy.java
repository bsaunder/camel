package net.bryansaunders.camel.eap_6.cdi_split_agg;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class XmlAggregationStrategy implements AggregationStrategy {
	 
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
 
        String oldBody = oldExchange.getIn().getBody(String.class);
        String newContent = (String) newExchange.getIn().getHeader("CamelInfinispanOperationResult");
        oldExchange.getIn().setBody(oldBody + newContent);
        return oldExchange;
    }
}