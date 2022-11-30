package binotify;

import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.ws.Endpoint;

import binotify.database.DbConn;

public class Main {
    public static void main(String[] args) {
        // print hello world
        try {
            System.out.println("Hello World!");
            Endpoint.publish("http://0.0.0.0:80/subs", new binotify.services.SubService());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        // finally {
        //     Connection conn = DbConn.getConnection();
        //     try {
        //         if (conn != null) {
        //             conn.close();
        //         }
        //     } catch (SQLException e) {
        //         System.err.println("Something happened: " + e);
        //         e.printStackTrace();
        //     }

        // }
    }
}