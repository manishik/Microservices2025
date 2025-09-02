package learn.manish.creditCard.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Simple class to test DB Connection
public class PostgresDBTest {
    public static void main(String[] argv) {
        System.out.println("-------- Postgres JDBC Connection Testing ------");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Postgres JDBC Driver?");
            e.printStackTrace();
            return;
        }
        System.out.println("Postgres JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "MySecretpassword");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
