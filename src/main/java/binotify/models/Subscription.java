package binotify.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import binotify.database.DbConn;
import binotify.utils.HTTPWrapper;

public class Subscription {
    private static Connection conn = DbConn.getConnection();

    private Integer creator_id;
    private Integer subscriber_id;
    private Status status;

    public Subscription(Integer creator_id, Integer subscriber_id, Status status) {
        this.creator_id = creator_id;
        this.subscriber_id = subscriber_id;
        this.status = status;

        if (conn == null) {
            conn = DbConn.getConnection();
        }
    }

    public Subscription(Integer creator_id, Integer subscriber_id) {
        this.creator_id = creator_id;
        this.subscriber_id = subscriber_id;
        this.status = Status.PENDING;

        if (conn == null) {
            conn = DbConn.getConnection();
        }
    }

    public String subscribe() throws SQLException {
        try {
            String sql = "INSERT INTO subs (creator_id, subscriber_id, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, this.creator_id);
            stmt.setInt(2, this.subscriber_id);
            stmt.setString(3, this.status.toString());
            stmt.executeUpdate();
            return "Subscription successful";
        } catch (SQLException e) {
            throw e;
        }
    }

    public String accept() throws SQLException {
        String response = "";
        try {
            String sql = "UPDATE subs SET status = ? WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, Status.ACCEPTED.toString());
            stmt.setInt(2, this.creator_id);
            stmt.setInt(3, this.subscriber_id);
            stmt.executeUpdate();

            response = "Subscription accepted";

            JSONObject body = new JSONObject();
            body.put("creator_id", this.creator_id);
            body.put("subscriber_id", this.subscriber_id);
            body.put("status", Status.ACCEPTED.toString());
            Integer code = HTTPWrapper.createJSONRequest("http://php:80/chStatus", "POST", body.toString());

            response += " callback status: " + code;

            return response;
        } catch (SQLException e) {
            if (response.isEmpty()) {
                return response;
            }
            throw e;
        }
    }

    public String reject() throws SQLException {
        String response = "";
        try {
            String sql = "UPDATE subs SET status = ? WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, Status.REJECTED.toString());
            stmt.setInt(2, this.creator_id);
            stmt.setInt(3, this.subscriber_id);
            stmt.executeUpdate();

            response = "Subscription rejected";

            JSONObject body = new JSONObject();
            body.put("creator_id", this.creator_id);
            body.put("subscriber_id", this.subscriber_id);
            body.put("status", Status.REJECTED.toString());
            Integer code = HTTPWrapper.createJSONRequest("http://php:80/chStatus", "POST", body.toString());

            response += " callback status: " + code;

            return response;
        } catch (SQLException e) {
            if (response.isEmpty()) {
                return response;
            }
            throw e;
        }
    }
    
    public static ResultSet getPendingFunc() throws SQLException {
        try {
            String sql = "SELECT * FROM subs WHERE status = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, Status.PENDING.toString());
            ResultSet rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static ResultSet getSubStatus(Integer creator_id, Integer subscriber_id) throws SQLException {
        try {
            String sql = "SELECT * FROM subs WHERE creator_id = ? AND subscriber_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, creator_id);
            stmt.setInt(2, subscriber_id);
            ResultSet rs = stmt.executeQuery();

            return rs;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static ResultSet getSubStatusBatch(Integer subscriber_id) throws SQLException {
        try {
            String sql = "SELECT * FROM subs WHERE subscriber_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, subscriber_id);
            ResultSet rs = stmt.executeQuery();

            return rs;
        } catch (SQLException e) {
            throw e;
        }
    }

}
