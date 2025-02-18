package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Mainfx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML de la page de connexion
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();

        // Définir le titre de la fenêtre
        primaryStage.setTitle("Application JavaFX - Login");

        // Définir la scène avec le contenu chargé
        primaryStage.setScene(new Scene(root, 600, 400));

        // Afficher la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
