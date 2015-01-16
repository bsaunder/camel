package net.bryansaunders.camel.eap_6.cdi_infinispan;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.apache.camel.cdi.CdiCamelContext;

@Singleton
@Startup
public class Bootstrap {

	@Inject
	private CdiCamelContext camelCtx;

	@Inject
	private InfinispanCamelRoutes camelRoutes;

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory jmsConnFactory;
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println(">> Create CamelContext and register Camel Route.");
		
		// Load Route
		this.camelCtx.addRoutes(camelRoutes);

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