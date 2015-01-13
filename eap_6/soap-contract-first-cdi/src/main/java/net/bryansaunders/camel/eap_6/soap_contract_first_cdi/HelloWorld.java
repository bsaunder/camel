package net.bryansaunders.camel.eap_6.soap_contract_first_cdi;

import javax.inject.Named;

import org.apache.camel.Body;

@Named
public class HelloWorld {

    public String sayHello(@Body String message) {
        return ">> Hello " + message + " user.";
    }
}