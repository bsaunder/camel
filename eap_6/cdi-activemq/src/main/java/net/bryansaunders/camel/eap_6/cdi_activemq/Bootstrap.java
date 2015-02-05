package net.bryansaunders.camel.eap_6.cdi_activemq;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.camel.component.ActiveMQConfiguration;
import org.apache.camel.Component;
import org.apache.camel.cdi.CdiCamelContext;

@Singleton
@Startup
public class Bootstrap {

	@Inject
	private CdiCamelContext camelCtx;

	@Inject
	private JmsCamelRoutes jmsRoutes;

	@Resource(mappedName = "java:/ActiveMqConnectionFactory")
	private ConnectionFactory amqConnFactory;

	@PostConstruct
	public void init() throws Exception {
		System.out.println(">> Create CamelContext and register Camel Route.");
		
		// Configure AMQ Component
		ActiveMQConfiguration amqConfig = new ActiveMQConfiguration();
		amqConfig.setConnectionFactory(this.amqConnFactory);
		
		Component component = this.camelCtx.getComponent("activemq");
		if (component != null) {
			System.out.println(">> Using Existing ActiveMQ Component");
			ActiveMQComponent amqComponent = (ActiveMQComponent) component;
			amqComponent.setConfiguration(amqConfig);
		} else {
			System.out.println(">> Adding New ActiveMQ Component");
			this.camelCtx.addComponent("activemq", new ActiveMQComponent(amqConfig));
		}

		// Load Route
		this.camelCtx.addRoutes(jmsRoutes);

		// Stating Camel Context
		System.out.println(">> Starting CamelContext.");
		this.camelCtx.setName("camel-eap-context");
		this.camelCtx.start();

		System.out.println(">> CamelContext created and camel route started.");
	}

	@PreDestroy
	public void stop() throws Exception {
		this.camelCtx.stop();
	}
}