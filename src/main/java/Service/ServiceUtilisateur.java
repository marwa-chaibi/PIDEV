package Service;

import Models.GENRE;
import Models.Utilisateur;
import tools.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur {
    Connection cnx;

    public ServiceUtilisateur() {
        cnx = DataBase.getInstance().getConn();
    }

    public void ajouter(Utilisateur utilisateur) throws SQLException {
        String sql = "INSERT INTO Utilisateur(nom, prenom, email, mot_de_passe, nationalite, genre) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, utilisateur.getNom());
            pst.setString(2, utilisateur.getPrenom());
            pst.setString(3, utilisateur.getEmail());
            pst.setString(4, utilisateur.getMotDePasse());
            pst.setString(5, utilisateur.getNationalite());
            pst.setString(6, utilisateur.getGenre().name());

            pst.executeUpdate();
            System.out.println("Vous avez été ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            throw e;
        }
    }

    public List<Utilisateur> recuperer() throws SQLException {
        String SQL = "SELECT * FROM Utilisateur";
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (PreparedStatement pst = cnx.prepareStatement(SQL);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMotDePasse(rs.getString("mot_de_passe"));
                u.setNationalite(rs.getString("nationalite"));
                u.setGenre(GENRE.valueOf(rs.getString("genre").toUpperCase()));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            throw e;
        }
        return utilisateurs;
    }

    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM Utilisateur WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("Suppression réussie");
    }

    public void modifier(int id, String nom) throws SQLException {
        String sql = "UPDATE Utilisateur SET nom = ? WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setString(1, nom);
        st.setInt(2, id);
        st.executeUpdate();
        System.out.println("Modification réussie");
    }
}
