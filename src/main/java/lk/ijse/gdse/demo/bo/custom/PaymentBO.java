package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {

    ArrayList<PaymentDTO> getAll() throws SQLException;

    boolean add(PaymentDTO DTO) throws SQLException;

    boolean update(PaymentDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;
}
