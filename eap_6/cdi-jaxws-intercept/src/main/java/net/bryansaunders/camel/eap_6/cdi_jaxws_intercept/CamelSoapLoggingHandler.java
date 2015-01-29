package net.bryansaunders.camel.eap_6.cdi_jaxws_intercept;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

public class CamelSoapLoggingHandler implements SOAPHandler<SOAPMessageContext> {

	@Inject
	private CamelContext camelContext;

	public boolean handleMessage(SOAPMessageContext mc) {
		try {
			System.out.println(">> Intercepting Message");
			
			SOAPMessage message = mc.getMessage();			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			final String xmlContent = new String(out.toByteArray());

			// Create ProducerTemplate
			ProducerTemplate producerTemplate = this.camelContext
					.createProducerTemplate();

			// Start It
			producerTemplate.start();

			// Send the Exchange
			producerTemplate.send(DirectCamelRoute.LOGGING_ENDPOINT_URI,
					new Processor() {
						@Override
						public void process(Exchange exchange) throws Exception {
							exchange.getIn().setBody(xmlContent);
						}
					});

			// Make sure to Stop This!!
			producerTemplate.stop();

			return true;
		} catch (Exception e) {
			System.out.println(">> Error Intercepting Message");
			e.printStackTrace();
			return false;
		}
	}

	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}

	public void close(MessageContext mc) {
	}

	public boolean handleFault(SOAPMessageContext mc) {
		return true;
	}
}
