package Controller;

import Models.Profil;
import Services.ServiceProfil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class ProfilController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField numTelField;

    @FXML
    private TextField adresseField;

    @FXML
    private Button saveButton;

    @FXML
    private Button editButton;

    private ServiceProfil serviceProfil;
    private Profil profilActuel;  // Profil actuel que vous modifiez

    public ProfilController() {
        serviceProfil = new ServiceProfil();
    }

    @FXML
    private void initialize() {
        emailField.setEditable(false);
        numTelField.setEditable(false);
        adresseField.setEditable(false);
        saveButton.setDisable(true);

        // Récupérer le profil et afficher les données
        try {
            List<Profil> profils = serviceProfil.recuperer();
            if (!profils.isEmpty()) {
                profilActuel = profils.get(0);  // Exemple pour récupérer le premier profil
                emailField.setText(profilActuel.getEmail());
                numTelField.setText(profilActuel.getNum_tel());
                adresseField.setText(profilActuel.getAdresse());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveButtonClick() {
        // Vérifier si des modifications ont été faites dans les champs
        String email = emailField.getText();
        String numTel = numTelField.getText();
        String adresse = adresseField.getText();

        // Mettre à jour les données du profil
        profilActuel.setEmail(email);
        profilActuel.setNum_tel(numTel);
        profilActuel.setAdresse(adresse);

        // Sauvegarder les modifications dans la base de données
        try {
            // Appeler le service pour mettre à jour le profil dans la base
            serviceProfil.modifier(profilActuel.getId(), profilActuel.getEmail(), profilActuel.getNum_tel(), profilActuel.getAdresse());
            System.out.println("Profil mis à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour du profil");
        }
    }

    @FXML
    private void handleEditButtonClick() {
        // Rendre les champs modifiables
        emailField.setEditable(true);
        numTelField.setEditable(true);
        adresseField.setEditable(true);
        saveButton.setDisable(false);
    }
}
