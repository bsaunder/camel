package net.bryansaunders.camel.eap_6.order_service_infinispan;

import org.apache.camel.builder.RouteBuilder;

public class DirectCamelRoute extends RouteBuilder {

	public static final String ENDPOINT_URI = "direct://soapEndpoint";

	private static final String SOAP_ENDPOINT = "http://www.webservicex.net/CurrencyConvertor.asmx?";

	private static final String WSDL = "http://www.webservicex.net/CurrencyConvertor.asmx?WSDL";

	private static final String SERVICE_NAME = "{http://www.webserviceX.NET/}CurrencyConvertor";

	private static final String ENDPOINT_NAME = "{http://www.webserviceX.NET/}CurrencyConvertorSoap";

	private static final String REQUEST = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <ConversionRate xmlns=\"http://www.webserviceX.NET/\">      <FromCurrency>USD</FromCurrency>    <ToCurrency>EUR</ToCurrency>    </ConversionRate>  </soap:Body></soap:Envelope>";

	@Override
	public void configure() throws Exception {
		
		String convRateKey = "EuroConvRate";
		
		from(ENDPOINT_URI).log(">> Received from JAX-WS Endpoint : ${body}")
			// Store Original Request
			.beanRef("orderUtility", "storeOriginalRequest")
			// Get Total Cost
			.beanRef("orderUtility", "getTotalCost")
			
			// Get Conv Rate from Infinispan
			.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationGet"))
			.setHeader("CamelInfinispanKey", simple(convRateKey))
            .to("infinispan://localhost:11322")
			
            // Check Infinispan Result for Value
            .choice()
            	.when(header("CamelInfinispanOperationResult").isNotNull())
            		.setBody(simple("${header.CamelInfinispanOperationResult}"))
            		.log(">> Pulled Rate from Infinispan: ${body}")
            	.otherwise()
            		// Call SOAP
					.beanRef("camelExchangeUtil","backupHeaders")
					.setBody().simple(REQUEST)
					.to("cxf://" + SOAP_ENDPOINT + "?dataFormat=MESSAGE&wsdlURL=" + WSDL + "&serviceName="
								+ SERVICE_NAME + "&portName=" + ENDPOINT_NAME)
					.beanRef("camelExchangeUtil","restoreHeaders")
					// Set Conv Rate on Body
					.beanRef("orderUtility","getConvRateFromXml")
					// Store Rate in Infinispan
					.setHeader("CamelInfinispanOperation", simple("CamelInfinispanOperationPut"))
					.setHeader("CamelInfinispanKey", simple(convRateKey))
		            .setHeader("CamelInfinispanValue", simple("${body}"))
		            .to("infinispan://localhost:11322")
				.end()
			// Get Euro Total Cost
			.beanRef("orderUtility","getAdjustedTotalCost")
			// Append Total Costs to Order
			.beanRef("orderUtility","appendTotalCost")
			// Send to JMS
			.to("jms:queue:orderQueue")
			.log(">> Sent Order : ${body}");

		// JMS Subscriber
		from("jms:queue:orderQueue")
			.log(">> Order Received for Processing")
			.log(">> Received Order: ${body}");

	}

}
