package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.DataBase;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Les champs ne peuvent pas être vides");
        } else {
            // Logique de validation de l'utilisateur
            if (isValidUser(email, password)) {
                try {
                    // Charger la page d'accueil (Home.fxml)
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                    Parent root = loader.load();

                    // Obtenir la fenêtre actuelle et changer la scène
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Page d'accueil");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    errorLabel.setText("Erreur lors du chargement de la page d'accueil.");
                }
            } else {
                errorLabel.setText("Utilisateur introuvable. Vérifiez votre adresse e-mail et votre mot de passe.");
            }
        }
    }

    // Méthode pour vérifier si les informations sont valides dans la base de données
    private boolean isValidUser(String email, String password) {
        Connection connection = DataBase.getInstance().getConn();
        if (connection != null) {
            try {
                // Requête SQL pour vérifier l'utilisateur dans la table "utilisateur"
                String query = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();

                // Si un utilisateur avec ces informations est trouvé, on retourne true
                if (resultSet.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @FXML
    private void handleRegisterLinkClick(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la page d'inscription
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent root = loader.load();

            // Obtenir la fenêtre actuelle et changer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Page d'inscription");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
