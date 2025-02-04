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
import lk.ijse.gdse.demo.bo.custom.MedicationBO;
import lk.ijse.gdse.demo.dto.MedicationDTO;
import javafx.scene.control.TextInputControl;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MedicationManageFormController {

    //private MedicationDAO medicationDAO = (MedicationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MEDICATION);

    MedicationBO medicationBO = (MedicationBO) BOFactory.getInstance().getBO(BOFactory.BOType.MEDICATION);

    @FXML
    private Button btnAdd, btnUpdate, btnDelete;

    @FXML
    private Button btnDashBoard;

    @FXML
    private AnchorPane content;

    @FXML
    private TableColumn<MedicationDTO, String> colDescription, colMedication_Id, colName;
    @FXML
    private TableColumn<MedicationDTO, Date> colExpiry_Date;
    @FXML
    private TableColumn<MedicationDTO, Double> colPrice;
    @FXML
    private TableColumn<MedicationDTO, Integer> colStock_Level;
    @FXML
    private TableView<MedicationDTO> tblMedication;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtExpiryDate, txtMedication_Id, txtName, txtPrice, txtStockLevel;

    public void initialize() {
        initializeTableColumns();
        loadMedicationsTable();
        tblMedication.setOnMouseClicked(this::onRowClick);
        generateMedicationId();
    }

    @FXML
    void onActionAdd(ActionEvent event) {
        if (validateMedicationFields()) {
            MedicationDTO medicationDTO = createMedicationDTO();
            try {
                boolean added = medicationBO.add(medicationDTO);
                if (added) {
                    new Alert(Alert.AlertType.INFORMATION, "Medication Added Successfully").show();
                    loadMedicationsTable();
                    refreshPage();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Medication could not be added. Check data and try again.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Add this line to see the exact error
                new Alert(Alert.AlertType.ERROR, "Medication Add Failed").show();
            }
        }
    }


    @FXML
    void onActionDelete(ActionEvent event) {
        try {
            if (medicationBO.delete(txtMedication_Id.getText())) {
                showAlert(Alert.AlertType.INFORMATION, "Medication Deleted Successfully");
                loadMedicationsTable();
                refreshPage();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Medication Deletion Failed");
        }
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        if (validateMedicationFields()) {
            MedicationDTO medicationDTO = createMedicationDTO();
            try {
                if (medicationBO.update(medicationDTO)) {
                    showAlert(Alert.AlertType.INFORMATION, "Medication Updated Successfully");
                    loadMedicationsTable();
                    refreshPage();
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Medication Update Failed");
            }
        }
    }

    private void initializeTableColumns() {
        colMedication_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMedication_Id()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colDescription.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        colStock_Level.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStock_Level()));
        colPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        colExpiry_Date.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpiry_Date()));
    }

    private void loadMedicationsTable() {
        try {
            List<MedicationDTO> medicationList = medicationBO.getAll();
            ObservableList<MedicationDTO> observableList = FXCollections.observableArrayList(medicationList);
            tblMedication.setItems(observableList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to load Medication data");
        }
    }

    private void onRowClick(MouseEvent event) {
        MedicationDTO selectedMedication = tblMedication.getSelectionModel().getSelectedItem();
        if (selectedMedication != null) {
            populateFields(selectedMedication);
        }
    }

    private void populateFields(MedicationDTO medication) {
        txtMedication_Id.setText(medication.getMedication_Id());
        txtName.setText(medication.getName());
        txtDescription.setText(medication.getDescription());
        txtStockLevel.setText(String.valueOf(medication.getStock_Level()));
        txtExpiryDate.setText(String.valueOf(medication.getExpiry_Date()));
        txtPrice.setText(String.valueOf(medication.getPrice()));
    }

    // Update validateMedicationFields to work with TextInputControl and TextArea fields
    private boolean validateMedicationFields() {
        return validateField(txtMedication_Id, "^[A-Za-z0-9]+$")
                && validateField(txtName, "^[A-Za-z0-9 ()]+$")
                && validateField(txtDescription, "^[A-Za-z0-9 .,]+$") // Now accepts TextArea
                && validateField(txtStockLevel, "^\\d+$")
                && validateField(txtExpiryDate, "^\\d{4}-\\d{2}-\\d{2}$")
                && validateField(txtPrice, "^\\d+(\\.\\d{1,2})?$");
    }

    // Change the validateField method to use TextInputControl instead of TextField
    private boolean validateField(TextInputControl field, String pattern) {
        if (!field.getText().matches(pattern)) {
            field.setStyle("-fx-border-color: red;");
            showAlert(Alert.AlertType.WARNING, "Please correct the invalid input.");
            return false;
        }
        field.setStyle("-fx-border-color: #7367F0;");
        return true;
    }

    private MedicationDTO createMedicationDTO() {
        return new MedicationDTO(
                txtMedication_Id.getText(),
                txtName.getText(),
                txtDescription.getText(),
                Date.valueOf(txtExpiryDate.getText()),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStockLevel.getText())
        );
    }

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

    private void generateMedicationId() {
        try {
            txtMedication_Id.setText(medicationBO.generateNewId());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Failed to generate Medication ID");
        }
    }

    private void refreshPage(){
        generateMedicationId();
        txtDescription.setText("");
        txtStockLevel.setText("");
        txtPrice.setText("");
        txtExpiryDate.setText("");
        txtName.setText("");

    }

    @FXML
    void onActionDashBorad(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    @FXML
    void onActionReset(ActionEvent event) {
        refreshPage();
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
