package net.bryansaunders.camel.eap_6.cdi_cxf_jetty;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.camel.cdi.CdiCamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.model.RoutesDefinition;

@Singleton
@Startup
public class Bootstrap {

    @Inject
    private CdiCamelContext camelCtx;
    
    @Inject
    private CxfCamelRoute cxfRoute;

    @PostConstruct
    public void init() throws Exception {
        System.out.println(">> Create CamelContext and register Camel Route.");

        System.out.println(">> Defining CXF Endpoint.");

        // Define CXF Endpoint 1
        CxfEndpoint orderEndpoint = new CxfEndpoint();
        orderEndpoint.setAddress("http://localhost:9595/order");
        orderEndpoint
                .setServiceClass("com.redhat.consulting.camel_on_fsw.OrderEndpoint");
        orderEndpoint.setWsdlURL("wsdl/order.wsdl");
        orderEndpoint.setCamelContext(this.camelCtx);
        this.camelCtx.addEndpoint("cxf:bean:orderEndpoint", orderEndpoint);

        System.out.println(">> Defining CXF Endpoint 2.");
        
        // Define CXF Endpoint 2
        CxfEndpoint orderEndpoint2 = new CxfEndpoint();
        orderEndpoint2.setAddress("http://localhost:9596/order2");
        orderEndpoint2
                .setServiceClass("com.redhat.consulting.camel_on_fsw.OrderEndpoint");
        orderEndpoint2.setWsdlURL("wsdl/order2.wsdl");
        orderEndpoint2.setCamelContext(this.camelCtx);
        this.camelCtx.addEndpoint("cxf:bean:orderEndpoint2", orderEndpoint2);
        
        // Load CXF Endpoint
        this.cxfRoute.setCxfEndpoint(orderEndpoint2);
        this.camelCtx.addRoutes(cxfRoute);

        System.out.println(">> Loading Camel Routes from XML File.");

        // Add Route from XML File
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("camel-routes.xml");
        if (is != null) {
            RoutesDefinition routes = this.camelCtx.loadRoutesDefinition(is);
            this.camelCtx.addRouteDefinitions(routes.getRoutes());
            System.out.println(">> Loaded Camel Routes from XML File.");
        } else {
            System.out
                    .println(">> Failed Loading Camel Routes from XML File, IS null.");
        }

        System.out.println(">> Starting CamelContext.");

        // Start Camel Context
        this.camelCtx.start();

        System.out.println(">> CamelContext created and camel route started.");
    }

    @PreDestroy
    public void stop() throws Exception {
        this.camelCtx.stop();
    }
}