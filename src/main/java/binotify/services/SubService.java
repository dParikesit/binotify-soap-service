package binotify.services;

import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.jooq.impl.DSL;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.api.message.HeaderList;

import binotify.database.DbConn;
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
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl, "FRONTEND");
            Subscription sub = new Subscription(creator_id, subscriber_id);
            
            String description =  sub.subscribe();
            
            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {            
            String description = "Req Subscribe Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }

    @WebMethod
    public String getPending() 
            throws Exception {
        MessageContext mc = context.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        HeaderList hl = (HeaderList) mc.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl, "REST");

            ResultSet rs = Subscription.getPendingFunc();

            JSONObject json = new JSONObject(DSL.using(DbConn.getConnection()).fetch(rs).formatJSON());
            
            Logger log = new Logger(ip, endpoint, "Get Pending Success");
            log.create();

            return json.toString();
        } catch (Exception e) {            
            String description = "Get Pending Failed";

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

        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl, "REST");
            Subscription sub = new Subscription(creator_id, subscriber_id);

            String description = sub.accept();

            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = "Accept Subs Failed";

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

        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl, "REST");
            Subscription sub = new Subscription(creator_id, subscriber_id);

            String description = sub.reject();

            Logger log = new Logger(ip, endpoint, description);
            log.create();

            return description;
        } catch (Exception e) {
            String description = "Reject Subs Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }

    @WebMethod
    public String getSubStatus(@WebParam(name = "creator_id") Integer creator_id, @WebParam(name = "subscriber_id") Integer subscriber_id) throws Exception {
        MessageContext mc = context.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        HeaderList hl = (HeaderList) mc.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl, "");

            ResultSet rs = Subscription.getSubStatus(creator_id, subscriber_id);

            JSONObject json = new JSONObject(DSL.using(DbConn.getConnection()).fetch(rs).formatJSON());

            Logger log = new Logger(ip, endpoint, "Get Subscribe Success");
            log.create();

            return json.toString();
        } catch (Exception e) {
            String description = "Get Subscribe Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }

    @WebMethod
    public String getSubStatusBatch(@WebParam(name = "subscriber_id") Integer subscriber_id) throws Exception {
        MessageContext mc = context.getMessageContext();
        HttpExchange req = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
        HeaderList hl = (HeaderList) mc.get(JAXWSProperties.INBOUND_HEADER_LIST_PROPERTY);
        String ip = String.format("%s", req.getRemoteAddress());
        String endpoint = String.format("%s", req.getRequestURI());

        try {
            Validator.ValidateHL(hl, "FRONTEND");

            ResultSet rs = Subscription.getSubStatusBatch(subscriber_id);

            JSONObject json = new JSONObject(DSL.using(DbConn.getConnection()).fetch(rs).formatJSON());

            Logger log = new Logger(ip, endpoint, "Get Batch Subscribe Success");
            log.create();

            return json.toString();
        } catch (Exception e) {
            String description = "Get Batch Subscribe Failed";

            Logger log = new Logger(ip, endpoint, description);
            log.create();
            throw e;
        }
    }
}
