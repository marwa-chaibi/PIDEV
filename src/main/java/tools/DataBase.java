package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    public final String url = "jdbc:mysql://localhost:3306/pi-dev";
    public final String user = "root";
    public final String password = "";



    private Connection conn;
    private static DataBase dataBase;

    private DataBase() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Vous êtes connectés ");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static DataBase getInstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }
    public Connection getConn() {
        return conn;
    }
}






