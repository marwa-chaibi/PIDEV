package Services;

import Models.GENRE;
import Models.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DashboardService {

    private ObservableList<Utilisateur> utilisateurList = FXCollections.observableArrayList();

    private Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pidev";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    // Méthode pour récupérer tous les utilisateurs de la base de données
    public ObservableList<Utilisateur> getUtilisateurs() {
        utilisateurList.clear();

        String query = "SELECT * FROM utilisateur";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                GENRE genre = GENRE.valueOf(rs.getString("genre").toUpperCase());

                String role = rs.getString("role");
                if (role == null || role.trim().isEmpty()) {
                    role = "utilisateur";  // Attribuer un rôle par défaut si aucun n'est trouvé
                }

                Utilisateur utilisateur = new Utilisateur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("nationalite"),
                        rs.getString("genre"),
                        role
                );
                utilisateur.setId(rs.getInt("id"));
                utilisateurList.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurList;
    }

    // Méthode pour ajouter un utilisateur dans la base de données
    public void ajouterUtilisateur(Utilisateur u) {
        String query = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, nationalite, genre, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getPrenom());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getMotDePasse());
            stmt.setString(5, u.getNationalite());
            stmt.setString(6, u.getGenre().name());
            stmt.setString(7, u.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un utilisateur
    public void supprimerUtilisateur(int id) {
        String query = "DELETE FROM utilisateur WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour modifier uniquement le rôle d'un utilisateur
    public void modifierUtilisateurRole(Utilisateur u) {
        String query = "UPDATE utilisateur SET role = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, u.getRole());  // Mise à jour du rôle uniquement
            stmt.setInt(2, u.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
