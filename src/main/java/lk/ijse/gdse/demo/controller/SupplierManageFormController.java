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
import lk.ijse.gdse.demo.bo.custom.SupplierBO;
import lk.ijse.gdse.demo.dto.SuppliersDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.SupplierDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SupplierManageFormController {

    private SupplierDAOImpl supplierDAOImpl = new SupplierDAOImpl();

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtSupplier_Id;

    @FXML
    private AnchorPane content;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SuppliersDTO, String> colName;

    @FXML
    private TableColumn<SuppliersDTO, String> colPhoneNumber;

    @FXML
    private TableColumn<SuppliersDTO, String> colSupplier_Id;

    @FXML
    private TableView<SuppliersDTO> tblSuppliers;

    // private  SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize() {
        colSupplier_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSup_Id()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colPhoneNumber.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone_No()));

        supplierDAOImpl = new SupplierDAOImpl();
        loadSuppliersTable();
        tblSuppliers.setOnMouseClicked(this::onRowClick);

        try {

//            refreshPage();
            String nextSupplierId = supplierBO.generateNewId();
            txtSupplier_Id.setText(nextSupplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSuppliersTable() {
        try {
            List<SuppliersDTO> suppliersList = supplierBO.getAll();
            ObservableList<SuppliersDTO> observableList = FXCollections.observableArrayList(suppliersList);
            tblSuppliers.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void onRowClick(MouseEvent event) {
        SuppliersDTO selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();


        if (selectedSupplier != null) {
            txtSupplier_Id.setText(selectedSupplier.getSupId());
            txtName.setText(selectedSupplier.getName());
            txtPhoneNumber.setText(selectedSupplier.getPhoneNo());
        }
    }

    private boolean validateFields() {
        String supId = txtSupplier_Id.getText();
        String name = txtName.getText();
        String phoneNo = txtPhoneNumber.getText();

        String idPattern = "^[A-Za-z0-9]+$";
        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(0[1-9]\\d{8}|\\+947[0-9]\\d{7})$";


        boolean isValid = true;

        if (!supId.matches(idPattern)) {
            txtSupplier_Id.setStyle(txtSupplier_Id.getStyle() + ";-fx-border-color: red;");
            isValid = false;
        } else {
            txtSupplier_Id.setStyle(txtSupplier_Id.getStyle() + ";-fx-border-color: #7367F0;");
        }

        if (!name.matches(namePattern)) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            isValid = false;
        } else {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        }

        if (!phoneNo.matches(phonePattern)) {
            txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + ";-fx-border-color: red;");
            isValid = false;
        } else {
            txtPhoneNumber.setStyle(txtPhoneNumber.getStyle() + ";-fx-border-color: #7367F0;");
        }

        if (!isValid) {
            new Alert(Alert.AlertType.WARNING, "Please correct the invalid input.").show();
        }

        return isValid;
    }


    @FXML
    void onActionAddSupplier(ActionEvent event) {
        if (validateFields()) {
            String supId = txtSupplier_Id.getText();
            String name = txtName.getText();
            String phoneNo = txtPhoneNumber.getText();

            SuppliersDTO supplierDTO = new SuppliersDTO(supId, name, phoneNo);
            try {
                boolean added = supplierBO.add(supplierDTO);
                if (added) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Added Successfully").show();
                    loadSuppliersTable();
                    refreshPage();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Supplier Add Failed").show();
            }
        }
    }

    @FXML
    void onActionDashBoard(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    @FXML
    void onActionReset(ActionEvent event) {
        try {
            // Get the next available Supplier_Id and set it to the text field
            txtSupplier_Id.clear();
            txtName.clear();
            txtPhoneNumber.clear();
            String nextSupplierId = supplierBO.generateNewId();
            txtSupplier_Id.setText(nextSupplierId);
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, show an alert in case of an error
           new Alert(Alert.AlertType.ERROR, "Can't Loaded next Supplier_Id").show();
        }
    }
    @FXML
    void onActionDeleteSupplier(ActionEvent event) {
        String supId = txtSupplier_Id.getText();
        if (supId.matches("^[A-Za-z0-9]+$")) { // Validate ID only
            try {
                boolean deleted = supplierBO.delete(supId);
                if (deleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully").show();
                    loadSuppliersTable();
                    refreshPage();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Supplier Delete Failed").show();
            }
        } else {
            txtSupplier_Id.setStyle(txtSupplier_Id.getStyle() + ";-fx-border-color: red;");
            new Alert(Alert.AlertType.WARNING, "Please enter a valid Supplier ID.").show();
        }
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

    public void navigateToForm(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(content.widthProperty());
            load.prefHeightProperty().bind(content.heightProperty());

            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }


    @FXML
    void onActionUpdateSupplier(ActionEvent event) {
        if (validateFields()) {
            String supId = txtSupplier_Id.getText();
            String name = txtName.getText();
            String phoneNo = txtPhoneNumber.getText();

            SuppliersDTO supplierDTO = new SuppliersDTO(supId, name, phoneNo);
            try {
                boolean updated = supplierBO.update(supplierDTO);
                if (updated) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully").show();
                    loadSuppliersTable();
                    refreshPage();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Supplier Update Failed").show();
            }
        }
    }

    public void refreshPage() throws SQLException {
        supplierBO.generateNewId();
        txtName.setText("");
        txtPhoneNumber.setText("");

    }

}
