package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.InsuranceDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InsuranceBO extends SuperBO {


    ArrayList<String> getCompanyNames() throws SQLException;

    String getDiscountByCompanyName(String companyName) throws SQLException;

    ArrayList<InsuranceDTO> getAll() throws SQLException;


    boolean update(InsuranceDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;
}
