package Controller;

import Models.Utilisateur;
import Services.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import tools.DataBase;
import org.mindrot.jbcrypt.BCrypt; // Importation de BCrypt

import java.sql.SQLException;
// code maarwa
public class RegisterController {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nationalityTextField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private Label actionTarget;

    private ServiceUtilisateur serviceUtilisateur;

    @FXML
    public void initialize() {
        // Initialiser le service pour gérer les utilisateurs
        serviceUtilisateur = new ServiceUtilisateur();

        // Remplir le ComboBox avec les options "Homme" et "Femme"
        genderComboBox.getItems().addAll("Homme", "Femme");
    }

    @FXML
    private void handleRegister() {
        // Récupérer les valeurs des champs
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordField.getText();
        String nationality = nationalityTextField.getText();
        String genderStr = genderComboBox.getValue();

        // Vérifier si tous les champs sont remplis
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || nationality.isEmpty() || genderStr == null) {
            actionTarget.setText("Tous les champs sont obligatoires !");
            return; // Sortir de la méthode si un champ est vide
        }

        // Vérifier si l'email existe déjà dans la base de données
        try {
            Utilisateur utilisateurExistant = serviceUtilisateur.recupererParEmail(email);
            if (utilisateurExistant != null) {
                actionTarget.setText("L'email existe déjà, choisissez un autre.");
                return;
            }

            // Hasher le mot de passe avec BCrypt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Créer un objet Utilisateur avec les données saisies
            Utilisateur utilisateur = new Utilisateur(firstName, lastName, email, hashedPassword, nationality, genderStr);

            // Ajouter l'utilisateur à la base de données
            if (registerUser(utilisateur)) {
                actionTarget.setText("Inscription réussie !");

                // Afficher une alerte de succès
                showSuccessAlert();

            } else {
                actionTarget.setText("Erreur d'inscription, veuillez réessayer !");
            }

        } catch (SQLException e) {
            actionTarget.setText("Erreur lors de la vérification de l'email.");
        }
    }

    private boolean registerUser(Utilisateur utilisateur) {
        DataBase db = DataBase.getInstance();
        try (java.sql.Connection conn = db.getConn()) {
            if (conn == null) {
                actionTarget.setText("Problème de connexion à la base de données.");
                return false;
            }

            // Préparation de l'insertion de l'utilisateur dans la base de données...
            String query = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, nationalite, genre) VALUES (?, ?, ?, ?, ?, ?)";
            try (java.sql.PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, utilisateur.getNom());
                stmt.setString(2, utilisateur.getPrenom());
                stmt.setString(3, utilisateur.getEmail());
                stmt.setString(4, utilisateur.getMotDePasse()); // Le mot de passe est déjà hashé
                stmt.setString(5, utilisateur.getNationalite());
                stmt.setString(6, utilisateur.getGenre().toString()); // Le genre en tant que chaîne

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Utilisateur ajouté avec succès !");
                    return true; // Si l'insertion a réussi
                } else {
                    System.out.println("Aucune ligne insérée.");
                    return false; // Si aucune ligne n'a été insérée
                }
            } catch (SQLException e) {
                actionTarget.setText("Erreur lors de l'insertion dans la base de données : " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            actionTarget.setText("Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
            return false; // En cas d'erreur lors de la connexion à la base de données
        }
    }

    // Afficher l'alerte de succès
    private void showSuccessAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Inscription réussie");
        alert.setContentText("Votre inscription a été réalisée avec succès. Vous pouvez maintenant vous connecter.");

        // Ajouter un gestionnaire d'événements sur le bouton OK de l'alerte
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Rediriger vers la page de connexion après avoir appuyé sur OK
                redirectToLogin();
            }
        });
    }

    // Rediriger vers la page de connexion
    private void redirectToLogin() {
        try {
            // Charger la scène de la page de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml")); // Chemin relatif depuis 'resources'
            Parent root = loader.load();

            // Obtenir la scène actuelle et la rediriger vers la page de connexion
            Stage stage = (Stage) actionTarget.getScene().getWindow();

            if (stage != null) {
                stage.setScene(new Scene(root));
                stage.setTitle("Connexion");
                stage.show();
            } else {
                System.out.println("Stage non trouvé. Impossible de rediriger.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la redirection vers la page de connexion : " + e.getMessage());
        }
    }
}