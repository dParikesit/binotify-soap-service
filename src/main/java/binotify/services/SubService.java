package binotify.services;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.api.message.HeaderList;

import binotify.models.Logger;
import binotify.models.Subscription;
import binotify.models.Validator;

@WebService
public class SubService {
    @Resource
    WebServiceContext context;

    @WebMethod
    public String subscribe(@WebParam(name="creator_id") Integer creator_id, @WebParam(name="subscriber_id") Integer subscriber_id) 
            throws Exception {
        MessageContext mc = context.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        HeaderList hl = (HeaderList) mc.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
        String ip = String.format("%s", req.getRemoteAddress()).substring(1);
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl);
            Subscription sub = new Subscription(creator_id, subscriber_id);
            
            String description =  sub.subscribe();
            
            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {            
            String description = "Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }

    @WebMethod
    public String accept(@WebParam(name = "creator_id") Integer creator_id, @WebParam(name = "subscriber_id") Integer subscriber_id) 
            throws Exception {
        MessageContext mc = context.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        HeaderList hl = (HeaderList) mc.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);

        String ip = String.format("%s", req.getRemoteAddress()).substring(1);
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl);
            Subscription sub = new Subscription(creator_id, subscriber_id);

            String description = sub.accept();

            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = "Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }

    @WebMethod
    public String reject(@WebParam(name = "creator_id") Integer creator_id, @WebParam(name = "subscriber_id") Integer subscriber_id) 
            throws Exception {
        MessageContext mc = context.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        HeaderList hl = (HeaderList) mc.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);

        String ip = String.format("%s", req.getRemoteAddress()).substring(1);
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl);
            Subscription sub = new Subscription(creator_id, subscriber_id);

            String description = sub.reject();

            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = "Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }
}
