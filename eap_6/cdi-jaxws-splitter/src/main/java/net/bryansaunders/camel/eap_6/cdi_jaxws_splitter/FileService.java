package net.bryansaunders.camel.eap_6.cdi_jaxws_splitter;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.bryansaunders.net/camel/eap6/cdi-jaxws-splitter/SendFile")
//@SOAPBinding(style = Style.RPC)
public interface FileService {

    @WebMethod
    String sendFile(DataHandler xmlData);

    @WebMethod
    String getFile(String fileId);
}
