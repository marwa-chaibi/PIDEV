package Services;

import Models.GENRE;
import Models.Utilisateur;
import tools.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUtilisateur {
    private Connection cnx;

    public ServiceUtilisateur() {
        cnx = DataBase.getInstance().getConn();
    }

    // Méthode pour récupérer un utilisateur par email
    public Utilisateur recupererParEmail(String email) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        Utilisateur utilisateur = null;

        // Vérifier la connexion à la base de données
        if (cnx == null) {
            System.out.println("La connexion à la base de données a échoué.");
            return null;
        }

        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // L'email existe, on crée un utilisateur
                    utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt("id"));
                    utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                    utilisateur.setNationalite(rs.getString("nationalite"));
                    utilisateur.setGenre(GENRE.valueOf(rs.getString("genre").toUpperCase()));
                    System.out.println("Utilisateur trouvé : " + utilisateur.getEmail());
                } else {
                    System.out.println("Aucun utilisateur trouvé pour cet email : " + email);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'utilisateur par email : " + e.getMessage());
            throw e;
        }
        return utilisateur;
    }
}
