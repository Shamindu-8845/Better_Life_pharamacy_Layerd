package lk.ijse.gdse.demo.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.DeliveryBO;
import lk.ijse.gdse.demo.dto.DeliveryDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.DeliveryDAOImpl;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class HomeDeliveryManageFormController {

    private DeliveryDAOImpl deliveryDAOImpl = new DeliveryDAOImpl();

    @FXML
    private Button btnAdd;

    @FXML
    private AnchorPane btnDashBoard;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane content;

    @FXML
    private TableView<DeliveryDTO> tblDelivery;

    @FXML
    private TableColumn<DeliveryDTO, Button> colAction;

    @FXML
    private TableColumn<DeliveryDTO, String> colCustomerPhone_No;

    @FXML
    private TableColumn<DeliveryDTO, Date> colDelivery_Date;

    @FXML
    private TableColumn<DeliveryDTO, String> colDelivery_Id;

    @FXML
    private TableColumn<DeliveryDTO, String> colLocation;

    @FXML
    private TableColumn<DeliveryDTO, String> colOrder_Id;


    @FXML
    private TextField txtCustomerPhone_No;

    @FXML
    private TextField txtDelivery_Date;

    @FXML
    private TextField txtDelivery_Id;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtOrder_Id;

    //private DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVERY);

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);

    public void initialize() throws SQLException {
        colDelivery_Id.setCellValueFactory(new PropertyValueFactory<>("Delivery_Id"));
        colDelivery_Date.setCellValueFactory(new PropertyValueFactory<>("Delivery_Date"));
        colOrder_Id.setCellValueFactory(new PropertyValueFactory<>("Order_Id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("Location"));
        colCustomerPhone_No.setCellValueFactory(new PropertyValueFactory<>("Customer_PhoneNo"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblDelivery.setOnMouseClicked(this::onRowClick);

        try {
            String nextDelivery_Id = deliveryBO.generateNewId();
            txtDelivery_Id.setText(nextDelivery_Id);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize data", e);
        }
        loadDeliveryTable();
    }

    private void onRowClick(MouseEvent event) {
        DeliveryDTO selectedDelivery = tblDelivery.getSelectionModel().getSelectedItem();
        if (selectedDelivery != null) {
            txtDelivery_Id.setText(selectedDelivery.getDelivery_Id());
            txtDelivery_Date.setText(String.valueOf(selectedDelivery.getDelivery_Date()));
            txtOrder_Id.setText(selectedDelivery.getOrder_Id());
            txtLocation.setText(selectedDelivery.getLocation());
            txtCustomerPhone_No.setText(selectedDelivery.getCustomer_PhoneNo());
        }
    }

    public void loadDeliveryTable() throws SQLException {
        try {
            List<DeliveryDTO> deliveryList = deliveryBO.getAll();
            ObservableList<DeliveryDTO> deliveryDTOS = FXCollections.observableArrayList(deliveryList);
            tblDelivery.setItems(deliveryDTOS);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error loading delivery data", e);
        }
    }


    @FXML
    void onActionAddDelivery(ActionEvent event) {
        String DeliID = txtDelivery_Id.getText();
        Date DeliDate = Date.valueOf(txtDelivery_Date.getText());
        String OrderID = txtOrder_Id.getText();
        String Location = txtLocation.getText();
        String Customer_PhoneNo = txtCustomerPhone_No.getText();

        DeliveryDTO deliveryDTO = new DeliveryDTO(DeliID,DeliDate,OrderID,Location,Customer_PhoneNo,null);
        try {
            boolean b = deliveryBO.add(deliveryDTO);
            if (b){
                new Alert(Alert.AlertType.INFORMATION, "Delivery Added Successfully").show();
                loadDeliveryTable();
                refreshPage();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Delivery Added Fail").show();

        }

    }

    public void refreshPage() throws SQLException {
        txtDelivery_Id.setText(deliveryBO.generateNewId());
        txtOrder_Id.clear();
        txtDelivery_Date.clear();
        txtLocation.clear();
        txtCustomerPhone_No.clear();
    }

    @FXML
    void onActionDashboard(ActionEvent event) throws IOException {
        navigateTo("/view/AdminForm.fxml");
    }

    @FXML
    void onActionReset(ActionEvent event) throws SQLException {
        txtDelivery_Id.setText(deliveryBO.generateNewId());
        txtOrder_Id.clear();
        txtDelivery_Date.clear();
        txtLocation.clear();
        txtCustomerPhone_No.clear();
    }

    @FXML
    void onActionUpdateDelivery(ActionEvent event) {
        // Get the DeliveryDTO from the selected row or from the form fields
        String deliveryId = txtDelivery_Id.getText(); // Assuming txtDelivery_Id is a TextField
        Date deliveryDate = Date.valueOf(txtDelivery_Date.getText()); // Assuming txtDelivery_Date is a TextField
        String orderId = txtOrder_Id.getText(); // Assuming txtOrder_Id is a TextField
        String location = txtLocation.getText(); // Assuming txtLocation is a TextField
        String customerPhoneNo = txtCustomerPhone_No.getText(); // Assuming txtCustomerPhone_No is a TextField

        // Create a DeliveryDTO object to hold the updated values
        DeliveryDTO updatedDelivery = new DeliveryDTO(deliveryId, deliveryDate, orderId, location, customerPhoneNo, null);
        try {
            boolean result = deliveryBO.update(updatedDelivery);
            if (result) {
                refreshPage();
                loadDeliveryTable();
                System.out.println("Delivery updated successfully.");
            } else {
                System.out.println("Failed to update delivery.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void navigateToForm(String fxmlPath) throws IOException {
       try{
           content.getChildren().clear();
           AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

           load.prefWidthProperty().bind(content.widthProperty());
           load.prefHeightProperty().bind(content.heightProperty());
           content.getChildren().add(load);
       } catch (Exception e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Failed to navigate to " + fxmlPath).show();
       }
    }

    public void navigateTo(String fxmlPath) throws IOException {
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
