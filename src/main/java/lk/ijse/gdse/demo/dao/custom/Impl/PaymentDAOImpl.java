package lk.ijse.gdse.demo.dao.custom.Impl;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.PaymentDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.entity.Payment;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public  ObservableList<Payment> getAll() throws SQLException {
       /* List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payment";

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Fetch data from ResultSet
                String paymentId = resultSet.getString("Payment_Id");
                Date date = resultSet.getDate("Date");
                double amount = resultSet.getDouble("Amount");
                String paymentType = resultSet.getString("Payment_Type");
                String status = resultSet.getString("Status");
                String insuranceId = resultSet.getString("Insurance_Id");

                // Create and add PaymentDTO object to the list
                payments.add(new Payment(paymentId, date, amount, paymentType, status, insuranceId));
            }
        }

        // Convert the list to an ObservableList and return
        return FXCollections.observableList(payments);*/

       ResultSet resultSet =  SQLUtil.execute("select * from Payment");
        List<Payment> payments = new ArrayList<>();
        while (resultSet.next()) {
            Payment payment = new Payment(
                    resultSet.getString("Payment_Id"),
                    resultSet.getDate("Date"),
                    resultSet.getDouble("Amount"),
                    resultSet.getString("Payment_Type"),
                    resultSet.getString("Status"),
                    resultSet.getString("Insurance_Id")
            );
            payments.add(payment);
        }
        return FXCollections.observableList(payments);
    }

    @Override
    public  String generateNewId() throws SQLException {
        // Execute the query to fetch the most recent Payment_Id
        ResultSet resultSet = SQLUtil.execute("SELECT Payment_Id FROM Payment ORDER BY CAST(SUBSTRING(Payment_Id, 2) AS UNSIGNED) DESC LIMIT 1");

        if (resultSet.next()) {
            // Extract the last Payment_Id from the result set
            String lastId = resultSet.getString(1); // "P001", "P002", etc.

            // Extract the numeric part after the "P"
            String subString = lastId.substring(1);  // e.g., "001"

            // Parse the numeric part to an integer and increment it
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;

            // Return the next ID in the format "Pxxx"
            return String.format("P%03d", newIdIndex);
        }

        // Return null if no records are found
        return null;
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        /*String query = "UPDATE Payment SET Date = ?, Amount = ?, Payment_Type = ?, Status = ?, Insurance_Id = ? WHERE Payment_Id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, payment.getDate());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPayment_Type());
            statement.setString(4, payment.getStatus());
            statement.setString(5, payment.getInsurance_Id());
            statement.setString(6, payment.getPayment_Id());
            return statement.executeUpdate() > 0;
        }*/

       return SQLUtil.execute("UPDATE Payment SET Date = ?, Amount = ?, Payment_Type = ?, Status = ?, Insurance_Id = ? WHERE Payment_Id = ?",
                payment.getDate(),
                payment.getAmount(),
                payment.getPayment_Type(),
                payment.getStatus(),
                payment.getInsurance_Id(),
                payment.getPayment_Id());
    }
    @Override
    public boolean add(Payment paymentDTO) throws SQLException {
       /* String query = "INSERT INTO payments (Payment_Id, Amount, Date, Status, Payment_Type, Insurance_Id) VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(
                query,
                paymentDTO.getPayment_Id(),
                paymentDTO.getAmount(),
                paymentDTO.getDate(),
                paymentDTO.getStatus(),
                paymentDTO.getPayment_Type(),
                paymentDTO.getInsurance_Id()
        );*/

       return SQLUtil.execute("INSERT INTO payments (Payment_Id, Amount, Date, Status, Payment_Type, Insurance_Id) VALUES (?, ?, ?, ?, ?, ?)",
                paymentDTO.getPayment_Id(),
                paymentDTO.getAmount(),
                paymentDTO.getDate(),
                paymentDTO.getStatus(),
                paymentDTO.getPayment_Type(),
                paymentDTO.getInsurance_Id());
    }
    @Override
    public boolean delete(String Id) throws SQLException {
       /* String query = "DELETE FROM Payment WHERE Payment_Id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, Id);
            return statement.executeUpdate() > 0;
        }*/

       return SQLUtil.execute("DELETE FROM Payment WHERE Payment_Id = ?",Id);
    }
}
