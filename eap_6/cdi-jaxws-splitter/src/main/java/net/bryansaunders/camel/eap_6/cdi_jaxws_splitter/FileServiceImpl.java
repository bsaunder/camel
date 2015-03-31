package net.bryansaunders.camel.eap_6.cdi_jaxws_splitter;

import java.io.InputStream;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

@MTOM
@WebService(serviceName = "SendFileService", portName = "SendFile", name = "SendFile", endpointInterface = "net.bryansaunders.camel.eap_6.cdi_jaxws_splitter.FileService", targetNamespace = "http://www.bryansaunders.net/camel/eap6/cdi-jaxws-splitter/SendFile")
public class FileServiceImpl implements FileService {

    @Inject
    CamelContext camelContext;

    @Override
    public String sendFile(DataHandler xmlData) {
        // Build Key (in place of Filename)
        final String key = UUID.randomUUID().toString();

        System.out.println(">> ("+key+") Received Send SOAP Request");

        String returnVal = "";
        try {
            // Get InputStream
            final InputStream xmlInputStream = xmlData.getInputStream();

            // Create ProducerTemplate
            ProducerTemplate producerTemplate = this.camelContext.createProducerTemplate();

            // Start It
            producerTemplate.start();

            // Send the Exchange
            Exchange response = producerTemplate.send("seda://callSplitter", new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    exchange.getIn().setBody(xmlInputStream);
                    exchange.getIn().setHeader("FileName", key);
                    // Wait on a Response to ensure Write completed
                    exchange.setPattern(ExchangePattern.InOut);
                }
            });

            // Set Return Val for SOAP Message, key|count
            returnVal = key; //+ "|" + response.getProperty("CamelSplitSize").toString();

            // Make sure to Stop This!!
            producerTemplate.stop();

        } catch (Exception e) {
            System.out.println(">> ("+key+") Error Sending Message");
            e.printStackTrace();
        }

        System.out.println(">> ("+key+") Sending SOAP Response: " + returnVal);
        return returnVal;
    }

    @Override
    public String getFile(final String fileId) {
        System.out.println(">> ("+fileId+") Received Get SOAP Request");

        String returnVal = "";
        try {
            // Create ProducerTemplate
            ProducerTemplate producerTemplate = this.camelContext.createProducerTemplate();

            // Start It
            producerTemplate.start();

            // Send the Exchange
            Exchange response = producerTemplate.send("seda://callBuilder", new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    exchange.getIn().setBody("");
                    exchange.getIn().setHeader("FileName", fileId);
                    // Wait on a Response to ensure Write completed
                    exchange.setPattern(ExchangePattern.InOut);
                }
            });

            // Set Return Val for SOAP Message
            returnVal = response.getOut().getBody().toString();

            // Make sure to Stop This!!
            producerTemplate.stop();

        } catch (Exception e) {
            System.out.println(">> ("+fileId+") Error Sending Message");
            e.printStackTrace();
        }

        System.out.println(">> ("+fileId+") Sending SOAP Response: " + returnVal);
        return returnVal;
    }
}
