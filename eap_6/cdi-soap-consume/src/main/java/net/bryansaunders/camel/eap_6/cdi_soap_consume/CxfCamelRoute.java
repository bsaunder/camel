package net.bryansaunders.camel.eap_6.cdi_soap_consume;

import org.apache.camel.builder.RouteBuilder;

public class CxfCamelRoute extends RouteBuilder {

	private static final String ENDPOINT = "http://www.webservicex.net/stockquote.asmx?";

	private static final String WSDL = "http://www.webservicex.net/stockquote.asmx?WSDL";

	private static final String SERVICE_NAME = "{http://www.webserviceX.NET/}StockQuote";

	private static final String ENDPOINT_NAME = "{http://www.webserviceX.NET/}StockQuoteSoap";

	private static final String REQUEST = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <GetQuote xmlns=\"http://www.webserviceX.NET/\">      <symbol>RHT</symbol>    </GetQuote>  </soap:Body></soap:Envelope>";

	@Override
	public void configure() throws Exception {

		from("timer://javaTimer?fixedRate=true&period=15000&repeatCount=2")
				.log(">> Route Triggered")
				.setBody()
				.simple(REQUEST)
				.to("cxf://" + ENDPOINT + "?dataFormat=MESSAGE&wsdlURL=" + WSDL + "&serviceName="
						+ SERVICE_NAME + "&portName=" + ENDPOINT_NAME)
				.log(">> Response : ${body}");

	}

}
