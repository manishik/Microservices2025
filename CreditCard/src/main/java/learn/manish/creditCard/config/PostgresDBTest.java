package learn.manish.creditCard.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Simple class to test DB Connection
public class PostgresDBTest {

    private static final Logger logger = LoggerFactory.getLogger(PostgresDBTest.class);

    public static void main(String[] args) {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = "postgres";
        String dbPassword = "MySecretPassword";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            logger.info("Postgres connection is SUCCESSFUL: {}", connection.isValid(5));
        } catch (SQLException exception) {
            logger.error("Postgres connection failed", exception);
        }
    }
}
