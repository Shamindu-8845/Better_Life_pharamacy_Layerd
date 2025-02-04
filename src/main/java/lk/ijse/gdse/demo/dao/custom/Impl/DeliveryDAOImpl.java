package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.DeliveryDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.DeliveryDTO;
import lk.ijse.gdse.demo.entity.Delivery;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.io.IOException;
import java.sql.*;

public class DeliveryDAOImpl implements DeliveryDAO {

    @Override
    public String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select Delivery_Id from Delivery order by Delivery_Id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }


    public void insertDelivery(DeliveryDTO deliveryDTO) throws SQLException {
        String query = "insert into Delivery(Delivery_Id,Order_Id,Delivery_date,Location,CustomerPhone_No) values (?,?,?,?,?)";

        try(Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, deliveryDTO.getDelivery_Id());
            preparedStatement.setString(2, deliveryDTO.getOrder_Id());
            preparedStatement.setDate(3,deliveryDTO.getDelivery_Date());
            preparedStatement.setString(4,deliveryDTO.getLocation());
            preparedStatement.setString(5,deliveryDTO.getCustomer_PhoneNo());

            preparedStatement.executeUpdate();
        }
    }


    public ObservableList<Delivery> getAll() throws SQLException {
       /* String query = "SELECT delivery_Id, delivery_Date, Order_Id, Location, CustomerPhone_No FROM Delivery";
        ResultSet resultSet = CrudUtil.execute(query); // Assuming CrudUtil.execute handles prepared statements
        ObservableList<Delivery> deliveryList = FXCollections.observableArrayList();*/

       ResultSet resultSet = SQLUtil.execute("SELECT delivery_Id, delivery_Date, Order_Id, Location, CustomerPhone_No FROM Delivery");
        ObservableList<Delivery> deliveryList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Button removeBtn = new Button("Remove");
            String deliveryId = resultSet.getString("delivery_Id");

            deliveryList.add(new Delivery(
                    resultSet.getString(1),
                    resultSet.getDate(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    removeBtn));

            // Set action for the button
            removeBtn.setOnAction(event -> {
                System.out.println("Remove clicked for Delivery ID: " + deliveryId);
                // Call a method to handle the removal logic
                try {
                    delete(deliveryId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            // Create the DeliveryDTO object
           /* Delivery deliveryDTO = new Delivery(
                    deliveryId,
                    resultSet.getDate("delivery_Date"),
                    resultSet.getString("Order_Id"),
                    resultSet.getString("Location"),
                    resultSet.getString("CustomerPhone_No"),
                    removeBtn
            );*/

            // Add the object to the list
          /*  deliveryList.add(deliveryDTO);*/
        }

        return deliveryList;
    }



    @Override
    public boolean delete(String Id) throws SQLException {
        /*try {
            String query = "DELETE FROM Delivery WHERE delivery_Id = ?";
            boolean isDeleted = CrudUtil.execute(query, Id); // Assuming CrudUtil.execute handles queries
            if (isDeleted) {
                System.out.println("Delivery with ID " + Id + " has been removed.");
            } else {
                System.out.println("Failed to remove Delivery with ID " + Id);
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting delivery: " + e.getMessage());
        }
        return false;*/

       return SQLUtil.execute("DELETE FROM Delivery WHERE delivery_Id = ?",Id);
    }

    // Method to get delivery count by delivery_id
    public  int getDeliveryCount() throws SQLException {
        String sql = "SELECT COUNT(delivery_Id) AS delivery_count FROM delivery";

        try (PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("delivery_count");
            }
        }
        return 0;  // Return 0 if no records found
    }


    @Override
    public  boolean add(Delivery deliveryDTO) throws SQLException {
       return SQLUtil.execute("INSERT INTO Delivery (Delivery_Id, Delivery_date, Order_Id, Location, CustomerPhone_No) VALUES (?, ?, ?, ?, ?)",deliveryDTO.getDelivery_Id(),
                deliveryDTO.getDelivery_Date(),
                deliveryDTO.getOrder_Id(),
                deliveryDTO.getLocation(),
                deliveryDTO.getCustomer_PhoneNo());


        /*return CrudUtil.execute(
                "INSERT INTO Delivery (Delivery_Id, Delivery_date, Order_Id, Location, CustomerPhone_No) VALUES (?, ?, ?, ?, ?)",
                deliveryDTO.getDelivery_Id(),
                deliveryDTO.getDelivery_Date(),
                deliveryDTO.getOrder_Id(),
                deliveryDTO.getLocation(),
                deliveryDTO.getCustomer_PhoneNo()
        );*/
    }

    @Override
    // Method to update the delivery in the database
    public  boolean update(Delivery deliveryDTO) throws SQLException {
        /*String query = "UPDATE Delivery SET Delivery_date = ?, Order_Id = ?, Location = ?, CustomerPhone_No = ? WHERE Delivery_Id = ?";
        return CrudUtil.execute(query,
                deliveryDTO.getDelivery_Date(),
                deliveryDTO.getOrder_Id(),
                deliveryDTO.getLocation(),
                deliveryDTO.getCustomer_PhoneNo(),
                deliveryDTO.getDelivery_Id()
        );*/

       return SQLUtil.execute("UPDATE Delivery SET Delivery_date = ?, Order_Id = ?, Location = ?, CustomerPhone_No = ? WHERE Delivery_Id = ?",
                deliveryDTO.getDelivery_Date(),
                deliveryDTO.getOrder_Id(),
                deliveryDTO.getLocation(),
                deliveryDTO.getCustomer_PhoneNo(),
                deliveryDTO.getDelivery_Id());
    }


    /*public void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            // Close the current window if needed
            ((Stage)AdminFormAnchorPane.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML at path: " + fxmlPath); // Log the FXML path
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }*/


}

