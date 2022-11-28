package binotify;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        // print hello world
        try {
            System.out.println("Hello World!");
            Endpoint.publish("http://0.0.0.0:80/subs", new binotify.services.SubService());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}