package net.bryansaunders.camel.eap_6.cdi_jaxws_publish;

import javax.inject.Inject;
import javax.jws.WebService;

import net.bryansaunders.camel.OrderEndpoint;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

@WebService(serviceName = "OrderEndpointService", portName = "OrderService", targetNamespace = "http://camel.bryansaunders.net", wsdlLocation = "wsdl/order.wsdl", endpointInterface = "net.bryansaunders.camel.OrderEndpoint")
public class OrderEndpointImpl implements OrderEndpoint {

	@Inject
	CamelContext camelContext;

	@Override
	public String order(final String partName, final int amount,
			final String customerName) {

		System.out.println(">> SOAP Message Received");
		System.out.println(">> Partname: " + partName);
		System.out.println(">> Amount: " + amount);
		System.out.println(">> Customer Name: " + customerName);

		String returnVal = "";
		try {
			// Create ProducerTemplate
			ProducerTemplate producerTemplate = this.camelContext
					.createProducerTemplate();

			// Start It
			producerTemplate.start();

			// Send the Exchange
			Exchange response = producerTemplate.send(
					DirectCamelRoute.ENDPOINT_URI, new Processor() {

						@Override
						public void process(Exchange exchange) throws Exception {
							exchange.getIn().setBody(
									partName + "|" + amount + "|"
											+ customerName);
						}
					});
			returnVal = (String) response.getIn().getBody();

			// Make sure to Stop This!!
			producerTemplate.stop();
			
		} catch (Exception e) {
			System.out.println(">> Error Sending Message");
			e.printStackTrace();
		}

		return returnVal;
	}
}
