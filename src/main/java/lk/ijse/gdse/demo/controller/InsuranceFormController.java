package lk.ijse.gdse.demo.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.InsuranceBO;
import lk.ijse.gdse.demo.dto.InsuranceDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.InsuranceDAOImpl;
import lk.ijse.gdse.demo.entity.Insurance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InsuranceFormController {

    private InsuranceDAOImpl insuranceDAOImpl = new InsuranceDAOImpl();

    @FXML
    private Button btnAddInsurance;

    @FXML
    private Button btnDeletInsurance;

    @FXML
    private Button btnDeleteInsurance;

    @FXML
    private TextField txtCustomer_Id;

    @FXML
    private TableColumn<InsuranceDTO, String> colCampany_Name;

    @FXML
    private TableColumn<InsuranceDTO, String> colCustomer_Id;

    @FXML
    private TableColumn<InsuranceDTO, Double> colDiscount;

    @FXML
    private TableColumn<InsuranceDTO, String> colInsurance_Id;


    @FXML
    private TableView<InsuranceDTO> tblInsurance;

    @FXML
    private TextField txtCompany_Name;


    @FXML
    private AnchorPane content;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtInsrurance_Id;

   //private  InsuranceDAO insuranceDAO = (InsuranceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSURANCE);

    InsuranceBO insuranceBO = (InsuranceBO) BOFactory.getInstance().getBO(BOFactory.BOType.INSURANCE);

    @FXML
    void onActionAddInsurance(ActionEvent event) {
        try {
            // Gather data from fields
            String insuranceId = txtInsrurance_Id.getText();
            String customerId = txtCustomer_Id.getText();
            String companyName = txtCompany_Name.getText();
            double discount = Double.parseDouble(txtDiscount.getText());

            // Create DTO
            Insurance insurance = new Insurance(insuranceId, customerId, companyName, discount);

            // Add to database
            if (insuranceDAOImpl.add(insurance)) {
                showAlert(Alert.AlertType.INFORMATION, "Insurance added successfully!");
                loadInsuranceTable();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to add insurance!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }

    }

    @FXML
    void onActionDeletInsurance(ActionEvent event) {
        try {
            String insuranceId = txtInsrurance_Id.getText();

            if (insuranceBO.delete(insuranceId)) {
                showAlert(Alert.AlertType.INFORMATION, "Insurance deleted successfully!");
                loadInsuranceTable();
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete insurance!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }

    }


    @FXML
    void onActionUpdateInsurance(ActionEvent event) {
        try {
            // Retrieve values from the form
            String insuranceId = txtInsrurance_Id.getText();
            String customerId = txtCustomer_Id.getText();
            String companyName = txtCompany_Name.getText();
            double discount = Double.parseDouble(txtDiscount.getText());

            // Create an InsuranceDTO object
            InsuranceDTO insurance = new InsuranceDTO(insuranceId, customerId, companyName, discount);

            // Call the updateInsurance method
            if (insuranceBO.update(insurance)) {
                showAlert(Alert.AlertType.INFORMATION, "Insurance updated successfully!");
                loadInsuranceTable(); // Reload the table data
                clearFields(); // Clear the input fields
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update insurance!");
            }
        } catch (Exception e) {
            // Handle exceptions and show an error alert
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }


    public void initialize() {
        // Set up columns for the insurance table
        colInsurance_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getInsurance_Id()));
        colCustomer_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCustomer_Id()));
        colCampany_Name.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCompany_Name()));
        colDiscount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDiscount()));

        try {
            // Load table data
            loadInsuranceTable();

            // Generate and set the next Insurance ID
            String nextInsuranceId = insuranceBO.generateNewId();
            txtInsrurance_Id.setText(nextInsuranceId);


        } catch (SQLException e) {
            showErrorAlert("Failed to load initial data.");
            e.printStackTrace();
        }

        // Add row click event for the insurance table
        tblInsurance.setOnMouseClicked(this::onRowClick);
    }

    private void onRowClick(MouseEvent event) {
        // Check if any item is selected
        InsuranceDTO selectedInsurance = tblInsurance.getSelectionModel().getSelectedItem();
        if (selectedInsurance != null) {
            populateFields(selectedInsurance);
        } else {
            // Optionally, you can clear the fields or show a message indicating no selection
            clearFields();
            showAlert(Alert.AlertType.WARNING, "No row selected.");
        }
    }

    private void loadInsuranceTable() {
        try {
            List<InsuranceDTO> insuranceList = insuranceBO.getAll();
            if (insuranceList != null && !insuranceList.isEmpty()) {
                ObservableList<InsuranceDTO> observableList = FXCollections.observableArrayList(insuranceList);
                tblInsurance.setItems(observableList);
            } else {
                showAlert(Alert.AlertType.WARNING, "No insurance data found.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load insurance data: " + e.getMessage());
        }
    }
    private void populateFields(InsuranceDTO insurance) {
        txtInsrurance_Id.setText(insurance.getInsurance_Id());
        txtCompany_Name.setText(insurance.getCompany_Name());
        txtDiscount.setText(String.valueOf(insurance.getDiscount()));
        txtCustomer_Id.setText(insurance.getCustomer_Id());
    }

    private void clearFields() {
        txtInsrurance_Id.clear();
        txtInsrurance_Id.clear();
        txtDiscount.clear();
        txtCustomer_Id.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void onActionReset(ActionEvent event) {
        try {
            // Clear the text fields
            txtCompany_Name.clear();
            txtDiscount.clear();
            txtCustomer_Id.clear();

            // Get the next available Insurance_Id and set it to txtInsrurance_Id
            String nextInsuranceId = insuranceBO.generateNewId();
            txtInsrurance_Id.setText(nextInsuranceId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, show an alert in case of an error
            new Alert(Alert.AlertType.ERROR, "Can't loaded InsuranceID");
        }
    }


    @FXML
    void onActionDashBoard(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            // Close the current window if needed
            ((Stage)content.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML at path: " + fxmlPath); // Log the FXML path
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }


}
