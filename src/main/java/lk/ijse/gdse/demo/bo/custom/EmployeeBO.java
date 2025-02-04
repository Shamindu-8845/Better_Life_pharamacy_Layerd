package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

    int getEmployeeCount() throws SQLException;

    ArrayList<EmployeeDTO> getAll() throws SQLException;

    boolean add(EmployeeDTO DTO) throws SQLException;

    boolean update(EmployeeDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;
}
