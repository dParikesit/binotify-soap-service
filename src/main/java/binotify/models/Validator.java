package binotify.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import binotify.database.DbConn;
import binotify.utils.ApiKeyException;

public class Validator {
    private static Connection conn = null;

    public static void Validate(String apiKey, String clientType) throws SQLException, ApiKeyException {
        try {
            if (conn == null) {
                conn = DbConn.getConnection();
            }

            String sql = "SELECT * FROM api_keys WHERE api_key = ? AND client_type = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, apiKey);
            stmt.setString(2, clientType);

            ResultSet rs = stmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                throw new ApiKeyException("Invalid key");
            }

        } catch (SQLException | ApiKeyException e) {
            throw e;
        }
    }
}
