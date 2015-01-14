package net.bryansaunders.camel.eap_6.cdi_jaxws_publish;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.camel.cdi.CdiCamelContext;

@Singleton
@Startup
public class Bootstrap {
	
	public static final String CTX_NAME = "camel-eap-context";

    @Inject
    private CdiCamelContext camelCtx;
    
    @Inject
    private DirectCamelRoute directRoute;

    @PostConstruct
    public void init() throws Exception {
        System.out.println(">> Create CamelContext and register Camel Route.");

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