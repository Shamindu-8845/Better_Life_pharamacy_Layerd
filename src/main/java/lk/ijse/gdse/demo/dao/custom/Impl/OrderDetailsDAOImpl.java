//package lk.ijse.gdse.demo.model;
//
//import lk.ijse.gdse.demo.dto.OderDetailsDTO;
//import lk.ijse.gdse.demo.util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class OrderDetailsModel {
//
//    public static boolean saveOrderDetailsList(ArrayList<OderDetailsDTO> orderDetailsDTOS) throws SQLException {
//        // Iterate through each order detail in the list
//        for (OderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
//            // @isOrderDetailsSaved: Saves the individual order detail
//            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
//            if (!isOrderDetailsSaved) {
//                // Return false if saving any order detail fails
//                return false;
//            }
//
//            // @isItemUpdated: Updates the item quantity in the stock for the corresponding order detail
//            boolean isItemUpdated = MedicationModel.reduceQty(orderDetailsDTO);
//            if (!isItemUpdated) {
//                // Return false if updating the item quantity fails
//                return false;
//            }
//        }
//        // Return true if all order details are saved and item quantities updated successfully
//        return true;
//    }
//
//    private static boolean saveOrderDetail(OderDetailsDTO orderDetailsDTO) throws SQLException {
//        // Executes an insert query to save the order detail into the database
//        return CrudUtil.execute(
//                "insert into orderdetails values (?,?,?,?)",
//                orderDetailsDTO.getOrder_Id(),
//                orderDetailsDTO.getMedication_Id(),
//                orderDetailsDTO.getQuantity(),
//                orderDetailsDTO.getPrice()
//        );
//    }
//
//    // Method to get the total price from the OrderDetails table
//    public double getTotalPrice() throws SQLException {
//        // SQL query to calculate the total price
//        String query = "SELECT SUM(quantity * Price) FROM OrderDetails";
//
//        // Execute the query using the CrudUtil class
//        ResultSet resultSet = CrudUtil.execute(query);
//
//        // Check if the resultSet has data and return the sum
//        if (resultSet.next()) {
//            return resultSet.getDouble(1);  // Get the first column, which is the total price
//        } else {
//            return 0.0;  // Return 0.0 if no data is found
//        }
//    }
//
//
//}

package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.entity.OderDetails;
import lk.ijse.gdse.demo.util.CrudUtil;
import lk.ijse.gdse.demo.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

public static boolean saveOrderDetailsList(ArrayList<OderDetailsDTO> orderDetailsList, Connection connection) {
    String query = "INSERT INTO orderdetails (Order_Id, Medication_Id, quantity, Price) VALUES (?, ?, ?, ?)"; // Correct column name 'quantity'
    try (PreparedStatement pstm = connection.prepareStatement(query)) {
        for (OderDetailsDTO orderDetails : orderDetailsList) {
            pstm.setString(1, orderDetails.getOrder_Id());
            pstm.setString(2, orderDetails.getMedication_Id());
            pstm.setInt(3, orderDetails.getQuantity());
            pstm.setDouble(4, orderDetails.getPrice());
            pstm.addBatch();
        }

        pstm.executeBatch();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    private static boolean saveOrderDetail(OderDetailsDTO orderDetailsDTO, Connection connection) throws SQLException {
        return CrudUtil.execute(
                String.valueOf(connection),
                "INSERT INTO OrderDetails (Order_Id, Medication_Id, Quantity, Price) VALUES (?,?,?,?)",
                orderDetailsDTO.getOrder_Id(),
                orderDetailsDTO.getMedication_Id(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }

    public static boolean execute(Connection connection, String query, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeUpdate() > 0;
        }
    }



    public double getTotalPrice() throws SQLException {
        String query = "SELECT SUM(quantity * Price) FROM OrderDetails";

        ResultSet resultSet = CrudUtil.execute(query);

        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return 0.0;
        }
    }

    public static int saveOrderDetails(OderDetailsDTO orderDetails) throws SQLException {
        String sql = "INSERT INTO OrderDetails (Order_Id, Medication_Id, quantity, Price) VALUES (?, ?, ?, ?)";
        String updateStockLevelSql = "UPDATE Medication SET Stock_Level = Stock_Level - ? WHERE Medication_Id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pst = connection.prepareStatement(sql);
                 PreparedStatement updatePst = connection.prepareStatement(updateStockLevelSql)) {

                pst.setString(1, orderDetails.getOrder_Id());
                pst.setString(2, orderDetails.getMedication_Id());
                pst.setInt(3, orderDetails.getQuantity());
                pst.setDouble(4, orderDetails.getPrice());
                pst.executeUpdate();


                updatePst.setInt(1, orderDetails.getQuantity());
                updatePst.setString(2, orderDetails.getMedication_Id());
                updatePst.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw e;
        }
        return orderDetails.getQuantity();
    }


    @Override
    public ObservableList<OderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(OderDetails DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OderDetails DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException {
        return "";
    }

}
