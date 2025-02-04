/*package lk.ijse.gdse.demo.bo.custom.Impl;

import lk.ijse.gdse.demo.bo.custom.OrderBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.CustomerDAO;
import lk.ijse.gdse.demo.dao.custom.OrderDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.dto.OrderDTO;
import lk.ijse.gdse.demo.entity.Order;
import lk.ijse.gdse.demo.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

     OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public String generateNewId() throws SQLException {
        return orderDAO.generateNewId();
    }

    @Override
    public boolean add(OrderDTO DTO) throws SQLException {
        return false;
    }

    public  boolean save(OrderDTO orderDTO) throws SQLException {
        // Start a transaction
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // Disable auto-commit to begin the transaction
            connection.setAutoCommit(false);

            // Insert the order into the orders table
            String query = "INSERT INTO orders (Order_Id, Customer_Id, Order_Date, Delivery_Date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, orderDTO.getOrder_Id());
                pst.setString(2, orderDTO.getCustomer_Id());
                pst.setDate(3, orderDTO.getOrder_Date());
                pst.setDate(4, orderDTO.getDelivery_Date());

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected == 0) {
                    // If no rows were inserted, we need to rollback and return false
                    connection.rollback();
                    return false;
                }
            }

            // Insert order details into the OrderDetails table
            String orderDetailsQuery = "INSERT INTO OrderDetails (Order_Id, Medication_Id, Quantity, Price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstDetails = connection.prepareStatement(orderDetailsQuery)) {
                for (OderDetailsDTO details : orderDTO.getOrderDetails()) {
                    pstDetails.setString(1, details.getOrder_Id());
                    pstDetails.setString(2, details.getMedication_Id());
                    pstDetails.setInt(3, details.getQuantity());
                    pstDetails.setDouble(4, details.getPrice());

                    int rowsAffected = pstDetails.executeUpdate();
                    if (rowsAffected == 0) {
                        // If no rows were inserted, we need to rollback and return false
                        connection.rollback();
                        return false;
                    }
                }
            }

            // Commit the transaction if all inserts are successful
            connection.commit();
            return true;

        } catch (SQLException e) {
            // In case of an exception, rollback the transaction to ensure data consistency
            if (connection != null) {
                connection.rollback();
            }
            throw e; // Re-throw the exception after rollback
        } finally {
            // Ensure auto-commit is turned back on after the transaction
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    @Override
    public boolean saveOrderDetails(ArrayList<OderDetailsDTO> orderDetailsDTO) {
        return false;
    }

    public static int saveOrderDetails(OderDetailsDTO orderDetails) throws SQLException {
        String sql = "INSERT INTO OrderDetails (Order_Id, Medication_Id, quantity, Price) VALUES (?, ?, ?, ?)";
        String updateStockLevelSql = "UPDATE Medication SET Stock_Level = Stock_Level - ? WHERE Medication_Id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            // Disable auto-commit to manage the transaction manually
            connection.setAutoCommit(false);

            try (PreparedStatement pst = connection.prepareStatement(sql);
                 PreparedStatement updatePst = connection.prepareStatement(updateStockLevelSql)) {

                // Insert order details
                pst.setString(1, orderDetails.getOrder_Id());
                pst.setString(2, orderDetails.getMedication_Id());
                pst.setInt(3, orderDetails.getQuantity());
                pst.setDouble(4, orderDetails.getPrice());
                pst.executeUpdate();

                // Update stock level
                updatePst.setInt(1, orderDetails.getQuantity());
                updatePst.setString(2, orderDetails.getMedication_Id());
                updatePst.executeUpdate();

                // Commit the transaction
                connection.commit();
            } catch (SQLException e) {
                // Rollback in case of an error
                connection.rollback();
                throw e;
            } finally {
                // Ensure auto-commit is restored to its original state
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw e;
        }
        return orderDetails.getQuantity();
    }


}*/
package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.ijse.gdse.demo.bo.custom.OrderBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.CustomerDAO;
import lk.ijse.gdse.demo.dao.custom.OrderDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.CustomerDTO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.dto.OrderDTO;
import lk.ijse.gdse.demo.dto.tm.CartTM;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public String generateNewId() throws SQLException {
        return orderDAO.generateNewId();
    }

    @Override
    public boolean add(OrderDTO DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean save(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            String query = "INSERT INTO orders (Order_Id, Customer_Id, Order_Date, Delivery_Date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, orderDTO.getOrder_Id());
            preparedStatement.setString(2, orderDTO.getCustomer_Id());
            preparedStatement.setDate(3, orderDTO.getOrder_Date());
            preparedStatement.setDate(4, orderDTO.getDelivery_Date());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("issue 01");
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }
        /*Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            // Insert Order
            String query = "INSERT INTO orders (Order_Id, Customer_Id, Order_Date, Delivery_Date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, orderDTO.getOrder_Id());
                pst.setString(2, orderDTO.getCustomer_Id());
                pst.setDate(3, orderDTO.getOrder_Date());
                pst.setDate(4, orderDTO.getDelivery_Date());

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Insert Order Details
            if (!saveOrderDetails(orderDTO.getOrderDetails())) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("number 1");
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }*/

    @Override
    public boolean saveOrderDetail(ArrayList<OderDetailsDTO> orderDetailsDTO) {
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO OrderDetails (Order_Id, Medication_Id, Quantity, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);

            for (OderDetailsDTO dto : orderDetailsDTO) {
                pst.setString(1, dto.getOrder_Id());
                pst.setString(2, dto.getMedication_Id());
                pst.setInt(3, dto.getQuantity());
                pst.setDouble(4, dto.getPrice());
            }

            int i = pst.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMedication(ArrayList<OderDetailsDTO> orderDetailsDTO) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String updateStockLevelSql = "UPDATE Medication SET Stock_Level = Stock_Level - ? WHERE Medication_Id = ?";
            PreparedStatement updatePst = connection.prepareStatement(updateStockLevelSql);
            for (OderDetailsDTO details : orderDetailsDTO) {
                updatePst.setInt(1, details.getQuantity());
                updatePst.setString(2, details.getMedication_Id());
            }
            int i = updatePst.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean saveOrderDetails(ArrayList<OderDetailsDTO> orderDetailsDTO) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            try {
                boolean b = saveOrderDetail(orderDetailsDTO);
                if (b) {
                    boolean b1 = updateMedication(orderDetailsDTO);

                    if (b1) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("number 2");
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("number 2");
            e.printStackTrace();
        }
        return false;
    }
}
//        String sql = "INSERT INTO OrderDetails (Order_Id, Medication_Id, Quantity, Price) VALUES (?, ?, ?, ?)";
//        String updateStockLevelSql = "UPDATE Medication SET Stock_Level = Stock_Level - ? WHERE Medication_Id = ?";
//
//        try (Connection connection = DBConnection.getInstance().getConnection()) {
//
//
//            connection.setAutoCommit(false);
//
//            try (PreparedStatement pst = connection.prepareStatement(sql);
//                 PreparedStatement updatePst = connection.prepareStatement(updateStockLevelSql)) {
//
//                for (OderDetailsDTO details : orderDetailsDTO) {
//                    // Insert order details
//                    pst.setString(1, details.getOrder_Id());
//                    pst.setString(2, details.getMedication_Id());
//                    pst.setInt(3, details.getQuantity());
//                    pst.setDouble(4, details.getPrice());
//                    pst.addBatch();
//
//                    // Update stock level
//                    updatePst.setInt(1, details.getQuantity());
//                    updatePst.setString(2, details.getMedication_Id());
//                    updatePst.addBatch();
//                }
//
//                pst.executeBatch();
//                updatePst.executeBatch();
//
//                connection.commit();
//                return true;
//            } catch (SQLException e) {
//                connection.rollback();
//                System.out.println("issue 01");
//                throw e;
//            } finally {
//                connection.setAutoCommit(true);
//            }
//        } catch (SQLException e) {
//            System.out.println("issue 2");
//            return false;
//
//        }
   // }





