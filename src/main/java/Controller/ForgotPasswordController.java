package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import java.util.Random;

public class ForgotPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField otpField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button sendOTPButton;

    @FXML
    private Button verifyOTPButton;

    private String generatedOTP;

    // Méthode pour envoyer l'OTP
    @FXML
    private void sendOTPAction() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            statusLabel.setText("Veuillez entrer un email valide.");
        } else {
            generatedOTP = generateOTP();
            sendOTP(email, generatedOTP);
            statusLabel.setText("OTP envoyé à " + email);
        }
    }

    // Méthode pour vérifier l'OTP
    @FXML
    private void verifyOTPAction() {
        String enteredOTP = otpField.getText();
        if (enteredOTP.equals(generatedOTP)) {
            statusLabel.setText("OTP vérifié avec succès. Vous pouvez réinitialiser votre mot de passe.");
        } else {
            statusLabel.setText("OTP incorrect. Veuillez réessayer.");
        }
    }

    // Génération de l'OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Simulation de l'envoi de l'OTP (ajoute ton code réel d'envoi ici)
    private void sendOTP(String email, String otp) {
        System.out.println("Envoi de l'OTP " + otp + " à l'email: " + email);
        // Ajoute ici l'envoi réel d'email si nécessaire
    }
}
