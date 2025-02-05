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
import lk.ijse.gdse.demo.bo.custom.CustomerBO;
import lk.ijse.gdse.demo.dto.CustomerDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.CustomerDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class CustomerManageFormController {

    private CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnUpdateCustomer;

    @FXML
    private AnchorPane content;

    @FXML
    private TableColumn<CustomerDTO, String> colAddress;

    @FXML
    private TableColumn<CustomerDTO,String> colCustomer_Id;

    @FXML
    private TableColumn<CustomerDTO,String> colName;

    @FXML
    private TableColumn<CustomerDTO, String> colPhone_Number;

    @FXML
    private TableView<CustomerDTO> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCust_Id;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoeNo;

    //private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @FXML
    void onActionAdd(ActionEvent event) {
        String customerId = txtCust_Id.getText();
        String name = txtName.getText();
        String phoneNo = txtPhoeNo.getText();
        String address = txtAddress.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, name, address);

        try {
            boolean isAdded = customerBO.add(customerDTO);
            if (isAdded) {
                loadCustomers();
                clearFields();
                txtCust_Id.setText(customerBO.generateNewId());
            } else {
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        String customerId = txtCust_Id.getText();

        try {
            boolean isDeleted = customerBO.delete(customerId);
            if (isDeleted) {
                loadCustomers();
                clearFields();
            } else {
                System.out.println("Failed to delete customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtCust_Id.clear();
        txtName.clear();
        txtPhoeNo.clear();
        txtAddress.clear();
    }

    @FXML
    void onActionUpdate(ActionEvent event) {
        String customerId = txtCust_Id.getText();
        String name = txtName.getText();
        String phoneNo = txtPhoeNo.getText();
        String address = txtAddress.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, name, address);

        try {
            boolean isUpdated = customerBO.update(customerDTO);
            if (isUpdated) {
                loadCustomers();
            } else {
                System.out.println("Failed to update customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onActionDashboard(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    @FXML
    private Label lblTotalCustomers;

    public void initialize() throws SQLException {
        colCustomer_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCustomer_Id()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colAddress.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));
        colPhone_Number.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone_no()));
        int totalCustomers = customerDAOImpl.getTotalCustomers(); // Get the total customer count from the model
        lblTotalCustomers.setText(String.valueOf(totalCustomers)); // Set the text of the label
        customerDAOImpl = new CustomerDAOImpl();
        loadCustomers();
        tblCustomer.setOnMouseClicked(this::onRowClick);

        try {
            String nextSupplierId = customerBO.generateNewId();
            txtCust_Id.setText(nextSupplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void onRowClick(MouseEvent event) {
        CustomerDTO selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();


        if (selectedCustomer != null) {
            txtCust_Id.setText(selectedCustomer.getCustomer_Id());
            txtName.setText(selectedCustomer.getName());
            txtPhoeNo.setText(selectedCustomer.getPhone_no());
            txtAddress.setText(selectedCustomer.getAddress());
        }
    }


    @FXML
    void onActionReset(ActionEvent event) {
        try {
            String nextCustomerId = customerBO.generateNewId();
            txtCust_Id.setText(nextCustomerId);

            txtName.clear();
            txtPhoeNo.clear();
            txtAddress.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        try {
            List<CustomerDTO> customerList = customerBO.getAll();
            ObservableList<CustomerDTO> observableList = FXCollections.observableArrayList(customerList);
            tblCustomer.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
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
            ((Stage)content.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML at path: " + fxmlPath);
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }






}