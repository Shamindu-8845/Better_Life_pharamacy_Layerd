package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.SalaryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryBO extends SuperBO {

    ArrayList<SalaryDTO> getAll() throws SQLException;

    boolean add(SalaryDTO DTO) throws SQLException;

    boolean update(SalaryDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;
}
