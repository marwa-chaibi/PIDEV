package Controller;

import Services.DashboardService;
import Models.Utilisateur;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class DashboardController {

    @FXML
    private TableView<Utilisateur> tableView;

    @FXML
    private TableColumn<Utilisateur, Integer> idColumn;
    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;
    @FXML
    private TableColumn<Utilisateur, String> motDePasseColumn;
    @FXML
    private TableColumn<Utilisateur, String> nationaliteColumn;
    @FXML
    private TableColumn<Utilisateur, String> genreColumn;
    @FXML
    private TableColumn<Utilisateur, String> roleColumn;

    private DashboardService dashboardService = new DashboardService();

    @FXML
    public void initialize() {
        // Configuration des colonnes
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        motDePasseColumn.setCellValueFactory(new PropertyValueFactory<>("motDePasse"));
        nationaliteColumn.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Rendre la colonne "role" éditable
        makeRoleEditable();

        loadUtilisateurs();
    }

    private void loadUtilisateurs() {
        ObservableList<Utilisateur> utilisateurs = dashboardService.getUtilisateurs();
        tableView.setItems(utilisateurs);
    }

    private void makeRoleEditable() {
        roleColumn.setCellFactory(new Callback<TableColumn<Utilisateur, String>, TableCell<Utilisateur, String>>() {
            @Override
            public TableCell<Utilisateur, String> call(TableColumn<Utilisateur, String> param) {
                return new TableCell<Utilisateur, String>() {
                    private ComboBox<String> roleComboBox = new ComboBox<>();

                    {
                        roleComboBox.getItems().addAll("admin", "participant", "organisateur", "sponsor");
                        roleComboBox.setEditable(true);

                        roleComboBox.setOnAction(event -> {
                            String selectedRole = roleComboBox.getValue();
                            Utilisateur utilisateur = getTableView().getItems().get(getIndex());
                            if (utilisateur != null && selectedRole != null && !selectedRole.equals(utilisateur.getRole())) {
                                // Mise à jour du rôle dans l'objet Utilisateur et dans la base de données
                                utilisateur.setRole(selectedRole);
                                dashboardService.modifierUtilisateurRole(utilisateur); // Mise à jour uniquement du rôle
                            }
                        });
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(roleComboBox);
                            roleComboBox.setValue(item != null ? item : "");  // Assurez-vous que la valeur est correctement assignée
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void handleDashboardButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard Utilisateurs");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteButtonClick(ActionEvent actionEvent) {
        Utilisateur selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            dashboardService.supprimerUtilisateur(selectedUser.getId());
            tableView.getItems().remove(selectedUser);
        }
    }

    public void handleEditButtonClick(ActionEvent actionEvent) {
        // Implémentation de l'action Modifier ici
    }

    public void handleRefreshButtonClick(ActionEvent actionEvent) {
        loadUtilisateurs();
    }
}
