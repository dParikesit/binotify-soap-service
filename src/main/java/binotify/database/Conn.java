package binotify.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import io.github.cdimascio.dotenv.Dotenv;

public class Conn {
    private Connection connection;
    private static Dotenv dotenv = Dotenv.load();

    private static final String DB_HOST = dotenv.get("DB_HOST");
    private static final String DB_PORT = dotenv.get("DB_PORT");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASS = dotenv.get("DB_PASS");
    private static String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT;

    public Conn() {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();) {
            String sql = "CREATE DATABASE IF NOT EXISTS "+DB_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");

            this.connection = DriverManager.getConnection(DB_URL + "/" + DB_NAME, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}
