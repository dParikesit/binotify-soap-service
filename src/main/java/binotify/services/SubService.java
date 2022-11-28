package binotify.services;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;

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

        Subscription sub = new Subscription(creator_id, subscriber_id);
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        String apiKey = req.getRequestHeaders().getFirst("apiKey");
        String clientType = req.getRequestHeaders().getFirst("clientType");

        try {
            Validator.Validate(apiKey, clientType);
            
            String description =  sub.subscribe();
            
            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = e.getMessage();

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

        Subscription sub = new Subscription(creator_id, subscriber_id);
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        String apiKey = req.getRequestHeaders().getFirst("apiKey");
        String clientType = req.getRequestHeaders().getFirst("clientType");

        try {
            Validator.Validate(apiKey, clientType);

            String description = sub.accept();

            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = e.getMessage();

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

        Subscription sub = new Subscription(creator_id, subscriber_id);
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());
        String apiKey = req.getRequestHeaders().getFirst("apiKey");
        String clientType = req.getRequestHeaders().getFirst("clientType");

        try {
            Validator.Validate(apiKey, clientType);

            String description = sub.reject();

            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = e.getMessage();

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }
}
