package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/pidev";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection conn;
    private static DataBase dataBase;

    private DataBase() {
        try {
            // Tentative de connexion à la base de données
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            // Gestion des erreurs de connexion
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Impossible de se connecter à la base de données", e);
        }
    }

    public static synchronized DataBase getInstance() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public Connection getConn() {
        return conn;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
