package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    // Méthode pour la page Profil
    @FXML
    private void handleProfileButtonClick(ActionEvent event) {
        try {
            // Charger la page Profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile.fxml"));
            Parent root = loader.load();

            // Changer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Page Profil");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page Profil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour le Dashboard Utilisateurs
    @FXML
    private void handleDashboardButtonClick(ActionEvent event) {
        try {
            // Charger la page Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();

            // Changer la scène
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard Utilisateurs");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page Dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
