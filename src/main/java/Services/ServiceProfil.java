package Services;

import Models.Profil;
import tools.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProfil {
    private Connection cnx;

    public ServiceProfil() {
        cnx = DataBase.getInstance().getConn();
    }

    // Méthode pour ajouter un profil
    public void ajouter(Profil profil) throws SQLException {
        String sql = "INSERT INTO profil(id_utilisateur, email, num_tel, adresse) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, profil.getId_utilisateur());
            pst.setString(2, profil.getEmail());
            pst.setString(3, profil.getNum_tel());
            pst.setString(4, profil.getAdresse());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profil ajouté avec succès");
            } else {
                System.out.println("Aucune ligne ajoutée");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du profil : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode pour récupérer les profils avec l'adresse et l'email
    public List<Profil> recuperer() throws SQLException {
        List<Profil> profils = new ArrayList<>();

        // Requête SQL avec jointure entre 'profil' et 'utilisateur'
        String SQL = "SELECT p.id, p.id_utilisateur, p.email, p.num_tel, p.adresse, u.email AS email_utilisateur " +
                "FROM profil p " +
                "JOIN utilisateur u ON p.id_utilisateur = u.id";

        try (PreparedStatement pst = cnx.prepareStatement(SQL);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Profil profil = new Profil();
                profil.setId(rs.getInt("p.id"));
                profil.setId_utilisateur(rs.getInt("p.id_utilisateur"));
                profil.setEmail(rs.getString("email_utilisateur"));  // Email utilisateur de la table 'utilisateur'
                profil.setNum_tel(rs.getString("p.num_tel"));
                profil.setAdresse(rs.getString("p.adresse"));

                profils.add(profil);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des profils : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        return profils;
    }

    // Méthode pour supprimer un profil
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM profil WHERE id = ?";

        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profil supprimé avec succès");
            } else {
                System.out.println("Aucun profil trouvé avec cet ID");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du profil : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode pour modifier un profil
    public void modifier(int id, String email, String numTel, String adresse) throws SQLException {
        // Vérifier si l'ID existe d'abord
        String selectSQL = "SELECT * FROM profil WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(selectSQL)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String currentEmail = rs.getString("email");
                    String currentNumTel = rs.getString("num_tel");
                    String currentAdresse = rs.getString("adresse");

                    // Comparer les anciennes valeurs avec les nouvelles pour éviter des mises à jour inutiles
                    boolean isModified = false;

                    // Comparaison des valeurs
                    if (!email.equals(currentEmail)) {
                        isModified = true;
                    }
                    if (!numTel.equals(currentNumTel)) {
                        isModified = true;
                    }
                    if (!adresse.equals(currentAdresse)) {
                        isModified = true;
                    }

                    if (isModified) {
                        // Si des modifications ont été détectées, effectuer la mise à jour
                        String sql = "UPDATE profil SET email = ?, num_tel = ?, adresse = ? WHERE id = ?";
                        try (PreparedStatement updatePst = cnx.prepareStatement(sql)) {
                            updatePst.setString(1, email);
                            updatePst.setString(2, numTel);
                            updatePst.setString(3, adresse);
                            updatePst.setInt(4, id);

                            int rowsAffected = updatePst.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Profil modifié avec succès");
                            } else {
                                System.out.println("Aucun profil trouvé avec cet ID");
                            }
                        }
                    } else {
                        // Si aucune modification n'est détectée
                        System.out.println("Aucune modification détectée. Les données sont identiques.");
                    }
                } else {
                    System.out.println("Profil non trouvé avec l'ID donné.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du profil : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
