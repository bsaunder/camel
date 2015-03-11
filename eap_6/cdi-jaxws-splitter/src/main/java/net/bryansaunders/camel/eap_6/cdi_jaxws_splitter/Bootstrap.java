package net.bryansaunders.camel.eap_6.cdi_jaxws_splitter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.camel.cdi.CdiCamelContext;

@Singleton
@Startup
public class Bootstrap {

    @Inject
    private CdiCamelContext camelCtx;

    @Inject
    private CamelRoutes routes;

    @PostConstruct
    public void init() throws Exception {
        System.out.println(">> Create CamelContext and register Camel Route.");
        
        // Configure Routes
        routes.setSplitToken("IFSVApplicantRequestGrp");
        routes.setStartTag("VerifyHsldIncmAndFamSizeBulkReq");
        routes.setEndTag("VerifyHsldIncmAndFamSizeBulkReq");

        // Load Route
        this.camelCtx.addRoutes(routes);

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