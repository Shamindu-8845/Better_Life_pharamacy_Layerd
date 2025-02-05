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
        ResultSet resultSet = SQLUtil.execute("SELECT Payment_Id FROM Payment ORDER BY CAST(SUBSTRING(Payment_Id, 2) AS UNSIGNED) DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);

            String subString = lastId.substring(1);

            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }

        return null;
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
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
       return SQLUtil.execute("DELETE FROM Payment WHERE Payment_Id = ?",Id);
    }
}
