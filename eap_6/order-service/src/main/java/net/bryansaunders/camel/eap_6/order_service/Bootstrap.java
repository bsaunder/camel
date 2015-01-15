package net.bryansaunders.camel.eap_6.order_service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.apache.camel.Component;
import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;

@Singleton
@Startup
public class Bootstrap {

	public static final String CTX_NAME = "camel-eap-context";

	@Inject
	private CdiCamelContext camelCtx;

	@Inject
	private DirectCamelRoute directRoute;

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory jmsConnFactory;

	@PostConstruct
	public void init() throws Exception {
		System.out.println(">> Create CamelContext and register Camel Route.");

		// Configure JMS Component
		JmsConfiguration jmsConfig = new JmsConfiguration(jmsConnFactory);

		Component component = this.camelCtx.getComponent("jms");
		if (component != null) {
			System.out.println(">> Using Existing JMS Component");
			JmsComponent jmsComponent = (JmsComponent) component;
			jmsComponent.setConfiguration(jmsConfig);
		} else {
			System.out.println(">> Adding New JMS Component");
			this.camelCtx.addComponent("jms", new JmsComponent(jmsConfig));
		}

		this.camelCtx.addRoutes(directRoute);

		System.out.println(">> Starting CamelContext.");

		// Start Camel Context
		this.camelCtx.setName(CTX_NAME);
		this.camelCtx.start();

		System.out.println(">> CamelContext created and camel route started.");
	}

	@PreDestroy
	public void stop() throws Exception {
		this.camelCtx.stop();
	}
}