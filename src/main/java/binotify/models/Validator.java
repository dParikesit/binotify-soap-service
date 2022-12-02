package binotify.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import binotify.database.DbConn;
import binotify.utils.ApiKeyException;
import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.api.message.Header;

public class Validator {
    private static Connection conn = null;

    public static void Validate(String apiKey, String clientType) throws SQLException, ApiKeyException {
        try {
            if (conn == null) {
                conn = DbConn.getConnection();
            }

            String sql = "SELECT * FROM apikey WHERE api_key = ? AND client_type = ?";
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

    public static void ValidateHL(HeaderList hl, String supposedType) throws SQLException, ApiKeyException {
        try {
            if (conn == null) {
                conn = DbConn.getConnection();
            }

            String apiKey = "";
            String clientType = "";

            for (Header h : hl.asList()) {
                if (h.getLocalPart().equals("apiKey")) {
                    apiKey = h.getStringContent();
                }

                if (h.getLocalPart().equals("clientType")) {
                    clientType = h.getStringContent();
                }
            }

            if (!supposedType.equals("") && !supposedType.equals(clientType)) {
                throw new ApiKeyException("Invalid key");
            }

            String sql = "SELECT * FROM apikey WHERE api_key = ? AND client_type = ?";
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
