package net.bryansaunders.camel.jvm.splitter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Splitter to Split a file on an XPath.
 * 
 * @author Bryan Saunders <bsaunder@redhat.com>
 * 
 */
public class XPathSplitter extends RouteBuilder implements
		InitializingBean, DisposableBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(XPathSplitter.class);

	private String incomingUri;
	private String outgoingUri;
	private String xpath;

	/**
	 * Runs After the Properties Have Been Set to Check that All Properties are
	 * Correct.
	 */
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("Properties Set For Bean: " + this.getClass().getName());

		// Check the Properties
		if (this.incomingUri == null || this.incomingUri.isEmpty()) {
			throw new BeanInitializationException(
					"IncomingUri Must have a Value");
		}

		if (this.outgoingUri == null || this.outgoingUri.isEmpty()) {
			throw new BeanInitializationException(
					"OutgoingUri Must have a Value");
		}
		
		if (this.xpath == null || this.xpath.isEmpty()) {
			throw new BeanInitializationException(
					"XPath Must have a Value");
		}

		LOGGER.info("Properties Valid For Bean: " + this.getClass().getName());
		LOGGER.debug("incomingUri: " + this.incomingUri);
		LOGGER.debug("outgoingUri: " + this.outgoingUri);
		LOGGER.debug("xpath: " + this.xpath);
	}

	/**
	 * Configures the Route.
	 */
	@Override
	public void configure() throws Exception {

		// Get the Message, Split it on the XPath, and Send to Producer
		from(this.incomingUri).log(LoggingLevel.INFO, "Got Diver: ${body}")
				.split(xpath(this.xpath))
				.log(LoggingLevel.INFO, "Found Certifications: ${body}")
				.to(this.outgoingUri);

	}

	/**
	 * Runs when the Bean is Destroyed. Good Place to Close any Connections.
	 */
	public void destroy() throws Exception {
		LOGGER.info("Destoying Bean: " + this.getClass().getName());
	}

	/**
	 * Gets the Incoming URI.
	 * 
	 * @return the incomingUri
	 */
	public String getIncomingUri() {
		return this.incomingUri;
	}

	/**
	 * Sets the Incoming URI.
	 * 
	 * @param incomingUri
	 *            the incomingUri to set
	 */
	public void setIncomingUri(String incomingUri) {
		this.incomingUri = incomingUri;
	}

	/**
	 * Gets the Outgoing URI.
	 * 
	 * @return the outgoingUri
	 */
	public String getOutgoingUri() {
		return this.outgoingUri;
	}

	/**
	 * Sets the Outgoing URI.
	 * 
	 * @param outgoingUri
	 *            the outgoingUri to set
	 */
	public void setOutgoingUri(String outgoingUri) {
		this.outgoingUri = outgoingUri;
	}

	/**
	 * Get the XPath.
	 * 
	 * @return the xpath
	 */
	public String getXpath() {
		return this.xpath;
	}

	/**
	 * Set the XPath.
	 * 
	 * @param xpath
	 *            the xpath to set
	 */
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

}
