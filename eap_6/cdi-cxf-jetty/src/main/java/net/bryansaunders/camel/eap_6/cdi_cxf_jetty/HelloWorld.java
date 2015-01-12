package net.bryansaunders.camel.eap_6.cdi_cxf_jetty;

import javax.inject.Named;

import org.apache.camel.Body;

@Named
public class HelloWorld {

    public String sayHello(@Body String message) {
        return ">> Hello " + message + " user.";
    }
}