import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    public static final String DB = "postgresql";
    public static final String DB_USER = "postgres";
    public static final String DB_PASS = "2003";
    public static final String DB_NAME = "airport";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = String.format("jdbc:%s://localhost:5432/%s?user=%s&password=%s", DB, DB_NAME, DB_USER, DB_PASS);
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
        }

        return connection;
    }
}
