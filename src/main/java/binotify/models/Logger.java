package binotify.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import binotify.database.DbConn;

public class Logger {
    private static Connection conn = null;

    private String ip_addr;
    private String endpoint;
    private String description;
    private Instant timestamp;

    public Logger(String ip_addr, String endpoint, String description) {
        this.ip_addr = ip_addr;
        this.endpoint = endpoint;
        this.description = description;
        this.timestamp = null;

        if (conn == null) {
            conn = DbConn.getConnection();
        }
    }

    public Logger(String ip_addr, String endpoint, String description,
            Instant timestamp) {
        this.ip_addr = ip_addr;
        this.endpoint = endpoint;
        this.description = description;
        this.timestamp = timestamp;

        if (conn == null) {
            conn = DbConn.getConnection();
        }
    }

    public void create() throws SQLException  {
        try {
            String sql = "INSERT INTO logs (ip_addr, endpoint, description) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            if(timestamp != null){
                sql = "INSERT INTO logs (ip_addr, endpoint, description, requested_at) VALUES (?, ?, ?, ?)";
                stmt.setTimestamp(4, Timestamp.from(this.timestamp));
                ;
            }
        
            stmt.setString(1, this.ip_addr);
            stmt.setString(2, this.endpoint);
            stmt.setString(3, this.description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
}
