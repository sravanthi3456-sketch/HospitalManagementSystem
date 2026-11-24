

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/hospital_db";
            String user = "root";
            String password = "";

            Connection con = DriverManager.getConnection(
                url, user, password
            );

            return con;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}