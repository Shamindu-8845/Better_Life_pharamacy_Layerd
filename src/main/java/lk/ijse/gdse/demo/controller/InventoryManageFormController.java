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
import lk.ijse.gdse.demo.bo.custom.InventoryBO;
import lk.ijse.gdse.demo.dto.InventoryDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.InventoryDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class InventoryManageFormController {
    private InventoryDAOImpl inventoryDAOImpl = new InventoryDAOImpl();

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDashBoard;

    @FXML
    private Button btnReset;

    @FXML
    private AnchorPane content;

    @FXML
    private TableColumn<InventoryDTO, String> colInventory_Id;

    @FXML
    private TableColumn<InventoryDTO, Integer> colRecordLevel;

    @FXML
    private TableColumn<InventoryDTO, Integer> colStockLevel;

    @FXML
    private TableView<InventoryDTO> tblInventory;

    @FXML
    private TextField txtInventory_Id;

    @FXML
    private TextField txtRecord_Level;

    @FXML
    private TextField txtStock_Level;

    //private InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);

    InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORY);

    @FXML
    void onActionAddInventory(ActionEvent event) {
        // Retrieve data from input fields
        String inventoryId = txtInventory_Id.getText(); // Assuming you have a TextField named txtInventoryId
        int recordLevel = Integer.parseInt(txtRecord_Level.getText());  // Assuming you have a TextField named txtRecordLevel
        int stockLevel = Integer.parseInt(txtStock_Level.getText());    // Assuming you have a TextField named txtStockLevel

        // Create an InventoryDTO object
        InventoryDTO inventory = new InventoryDTO(inventoryId, recordLevel, stockLevel);

        try {
            // Insert the inventory record


            boolean result = inventoryBO.add(inventory);
            if (result) {
                // Notify success (you can update UI, clear fields, etc.)
                System.out.println("Inventory added successfully!");
                loadInventoryTable();
            } else {
                // Notify failure (you can display an error message)
                System.out.println("Failed to add inventory.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void onActionDeleteInventory(ActionEvent event) {
        String inventoryId = txtInventory_Id.getText(); // assuming txtInventoryId is the field where the user inputs the ID
        loadInventoryTable();
        try {
            boolean result = inventoryBO.delete(inventoryId);
            if (result) {
                // Notify success (maybe update UI)
            } else {
                // Notify failure
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionUpdateInventory(ActionEvent event) {
        String inventoryId = txtInventory_Id.getText();
        int recordLevel = Integer.parseInt(txtRecord_Level.getText());  // assuming txtRecordLevel is the input field for Record_Level
        int stockLevel = Integer.parseInt(txtStock_Level.getText());    // assuming txtStockLevel is the input field for Stock_Level

        InventoryDTO inventory = new InventoryDTO(inventoryId, recordLevel, stockLevel);
        loadInventoryTable();
        try {
            boolean result = inventoryBO.update(inventory);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionReset(ActionEvent event) throws SQLException {
        // Call the method to get the next Inventory Id
        String nextInventoryId = inventoryBO.generateNewId();
        // Set the next Inventory ID to the TextField
        txtInventory_Id.setText(nextInventoryId);

        // Clear the other fields
        txtStock_Level.clear();
        txtRecord_Level.clear();
        loadInventoryTable();


    }

    public void initialize() {
        colInventory_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getInventory_Id()));
        colRecordLevel.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRecord_Level()));
        colStockLevel.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStock_Level()));

        inventoryDAOImpl = new InventoryDAOImpl();

        tblInventory.setOnMouseClicked(this::onRowClick);

        try {
//            refreshPage();
            String nextSupplierId = inventoryBO.generateNewId();
            txtInventory_Id.setText(nextSupplierId);
            loadInventoryTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInventoryTable() {
        try {
            List<InventoryDTO> inventoryDTOList = inventoryBO.getAll();
            ObservableList<InventoryDTO> inventoryDTOS = FXCollections.observableArrayList(inventoryDTOList);
            tblInventory.setItems(inventoryDTOS);

           /* List<CustomerDTO> customerList = customerBO.getAll();
            ObservableList<CustomerDTO> customerDTOS = FXCollections.observableArrayList(customerList);
            tblCustomer.setItems(customerDTOS);*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private void onRowClick(MouseEvent event) {
        InventoryDTO selectedInventory = tblInventory.getSelectionModel().getSelectedItem();


        if (selectedInventory != null) {
            txtInventory_Id.setText(selectedInventory.getInventory_Id());
            txtRecord_Level.setText(String.valueOf(selectedInventory.getRecord_Level()));
            txtStock_Level.setText(String.valueOf(selectedInventory.getStock_Level()));
        }
    }

    @FXML
    void onActionDashboard(ActionEvent event) throws IOException {
        navigateTo("/view/AdminForm.fxml");
    }

    public void navigateTo(String fxmlPath ) throws IOException {
      try{
          FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
          Parent root = loader.load();
          Scene scene = new Scene(root);
          Stage stage = new Stage();
          stage.setScene(scene);
          stage.show();
          ((Stage)content.getScene().getWindow()).close();
      }catch (IOException e){
          e.printStackTrace();
          System.out.println("Failed to load FXML at path: " + fxmlPath); // Log the FXML path
          new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
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


}
