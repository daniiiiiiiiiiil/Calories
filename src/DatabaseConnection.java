import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;
    private Connection connection;

    public DatabaseConnection(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); // Драйвер PostgreSQL
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Драйвер PostgreSQL не найден: " + e.getMessage());
        }
    }


    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}