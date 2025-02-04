package lk.ijse.gdse.demo.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.CustomerBO;
import lk.ijse.gdse.demo.dto.CustomerDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.CustomerDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class UserFormCustomerFormController {

    @FXML
    private TextField txtCustomer_Id;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone_no;

    @FXML
    private TextField txtxAddress;

    @FXML
    private TableColumn<CustomerDTO, String> colAddress;

    @FXML
    private TableColumn<CustomerDTO, String> colCustomer_Id;

    @FXML
    private TableColumn<CustomerDTO, String> colName;

    @FXML
    private TableColumn<CustomerDTO, String> colPhone_no;


    @FXML
    private TableView<CustomerDTO> tblCustomer;

    private CustomerDAOImpl customerDAOImpl;

    //private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @FXML
    public void initialize() throws SQLException {
        colCustomer_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCustomer_Id()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colAddress.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));
        colPhone_no.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone_no()));
        txtCustomer_Id.setText(customerBO.generateNewId());
        loadCustomerTable();
        tblCustomer.setOnMouseClicked(this::onRowClick);

        try {
//            refreshPage();
            String nextSupplierId = customerBO.generateNewId();
            txtCustomer_Id.setText(nextSupplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerTable() {
        try {
            List<CustomerDTO> customerList = customerBO.getAll();
            ObservableList<CustomerDTO> customerDTOS = FXCollections.observableArrayList(customerList);
            tblCustomer.setItems(customerDTOS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void onRowClick(MouseEvent mouseEvent) {
        CustomerDTO selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();


        if (selectedCustomer != null) {
            txtCustomer_Id.setText(selectedCustomer.getCustomer_Id());
            txtName.setText(selectedCustomer.getName());
            txtPhone_no.setText(selectedCustomer.getPhone_no());
            txtxAddress.setText(selectedCustomer.getAddress());
        }
    }

    public void onActionAdd(ActionEvent actionEvent) {
        String customerId = txtCustomer_Id.getText();
        String name = txtName.getText();
        String phoneNo = txtPhone_no.getText();
        String address = txtxAddress.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, name, address);

        try {
            boolean isAdded = customerBO.add(customerDTO);
            if (isAdded) {
                loadCustomerTable();  // Reload customer list after addition
                clearFields();    // Clear the input fields
            } else {
                // Display error or success message
                System.out.println("Failed to add customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionUpdate(ActionEvent actionEvent) {
        String customerId = txtCustomer_Id.getText();
        String name = txtName.getText();
        String phoneNo = txtPhone_no.getText();
        String address = txtxAddress.getText();

        CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, name, address);

        try {
            boolean isUpdated = customerBO.update(customerDTO);
            if (isUpdated) {
                loadCustomerTable();  // Reload the list after updating
            } else {
                // Display error or success message
                System.out.println("Failed to update customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {
        txtCustomer_Id.clear();
        txtName.clear();
        txtxAddress.clear();
        txtPhone_no.clear();
    }

    public void onActionDelete(ActionEvent actionEvent) {
        String customerId = txtCustomer_Id.getText();

        try {
            boolean isDeleted = customerBO.delete(customerId);
            if (isDeleted) {
                loadCustomerTable();  // Reload the list after deleting
                clearFields();    // Clear the input fields
            } else {
                // Display error or success message
                System.out.println("Failed to delete customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionReset(ActionEvent actionEvent) {
        try {
            // Get the next available Customer_Id and set it to the text field
            String nextCustomerId = customerDAOImpl.getNextCustomer_Id();
            txtCustomer_Id.setText(nextCustomerId);

            // Clear the other input fields
            txtName.clear();
            txtPhone_no.clear();
            txtxAddress.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally handle the error, such as showing an alert
        }
    }
}

