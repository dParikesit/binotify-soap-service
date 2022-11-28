package binotify.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DbConn {
    private static DbConn instance = null;
    private static Connection connection = null;
    private static Dotenv dotenv = Dotenv.load();

    private String DB_HOST = dotenv.get("DB_HOST");
    private String DB_PORT = dotenv.get("DB_PORT");
    private String DB_NAME = dotenv.get("DB_NAME");
    private String DB_USER = dotenv.get("DB_USER");
    private String DB_PASS = dotenv.get("DB_PASS");
    private String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public DbConn() {
        // Open a connection
        try {
            DbConn.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if(DbConn.instance == null) {
            DbConn.instance = new DbConn();
        }

        return DbConn.connection;
    }

}
