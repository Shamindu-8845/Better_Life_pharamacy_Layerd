package lk.ijse.gdse.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.UserBO;
import lk.ijse.gdse.demo.dto.UserDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.SupplierDAOImpl;
import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserFormUserController {

    @FXML
    private TableColumn<UserDTO, String> colPassword;

    @FXML
    private TableColumn<UserDTO, String> colUser_Id;

    @FXML
    private TableColumn<UserDTO, String> colUsername;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnDeleteSupplier;

    @FXML
    private Button btnUpdateSupplier;

    @FXML
    private TextField txtPassword;

    @FXML
    private LineChart<String,Number>  LineChart;

    @FXML
    private TextField txtUser_Id;

    @FXML
    private TextField txtUsername;


    @FXML
    private CategoryAxis XAxis;

    @FXML
    private NumberAxis YAXis;
    @FXML
    private TableView<UserDTO> tblUser;

   //private UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

   UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    public void initialize() {



        // Set the cell value factories for the columns
        colUser_Id.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        SupplierDAOImpl UserModel = new SupplierDAOImpl();

        tblUser.setOnMouseClicked(this::onRowClick);

        try {
            String nextSupplierId = userBO.generateNewId();
            txtUser_Id.setText(nextSupplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadUserTable();

    }




    private void onRowClick(MouseEvent event) {
        UserDTO selectUserId = tblUser.getSelectionModel().getSelectedItem();

        if (selectUserId != null) {
            txtUser_Id.setText(selectUserId.getUserId());
            txtUsername.setText(selectUserId.getUserName());
            txtPassword.setText(selectUserId.getPassword());
        }
    }


    private void refreshPage() throws SQLException {
        loadUserTable();

        btnAddUser.setDisable(false);

        btnUpdateSupplier.setDisable(false);
        btnDeleteSupplier.setDisable(false);

        txtUser_Id.setText("");
        txtPassword.setText("");
        txtUsername.setText("");

    }


    private void loadUserTable() {
        try {
            ArrayList<UserDTO> userDTOS = userBO.getAll();
            ObservableList<UserDTO> userList = FXCollections.observableArrayList(userDTOS);
            tblUser.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void colAddUser(ActionEvent event) {
        String User_Id = txtUser_Id.getText();
        String User_Name = txtUsername.getText();
        String Password = txtPassword.getText();

        UserDTO userDTO = new UserDTO(User_Id, User_Name, Password);
        try {
            boolean b = userBO.add(userDTO);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "User Added Successfully").show();
                loadUserTable();
                refreshPage();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Supplier Added Fail").show();

        }

    }

    @FXML
    void colDeleteSupplier(ActionEvent event) {
        String Sup_Id  = txtUser_Id.getText();

        try {
            boolean b = userBO.delete(Sup_Id);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully").show();
                loadUserTable();
                refreshPage();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void colUpdateSupplier(ActionEvent event) {
        String User_Id = txtUser_Id.getText();
        String userName = txtPassword.getText();
        String password = txtUsername.getText();

        UserDTO userDTO = new UserDTO(User_Id, userName, password);
        try {
            boolean b = userBO.update(userDTO);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "User Update Successfully").show();
                loadUserTable();
                refreshPage();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "User Update Fail").show();

        }
    }

    @FXML
    void onActionReset(ActionEvent event) {
        try {
            // Reset the User ID text field with the next User ID
            txtUser_Id.setText(userBO.generateNewId());
            txtUsername.clear();
            txtPassword.clear();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate next User ID").show();
            e.printStackTrace();
        }
    }

    @FXML
    void onActionUpdateSupplier(ActionEvent event) {
        String userId = txtUser_Id.getText();
        String password = txtPassword.getText();
        String userName = txtUsername.getText();

        if (userId.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill in all fields").show();
            return;
        }
        UserDTO userDTO = new UserDTO(userId, userName, password);

        try {
            boolean isUpdated = userBO.update(userDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User updated successfully!").show();
                loadUserTable();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update user").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

}
