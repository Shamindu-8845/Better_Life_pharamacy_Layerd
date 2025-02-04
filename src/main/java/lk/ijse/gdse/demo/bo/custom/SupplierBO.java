package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.SuppliersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {

    boolean isSupplierIdExists(String supId) throws SQLException;

    ArrayList<String> getAllCustomerIds() throws SQLException;

    int getTotalSuppliers() throws SQLException;

    ArrayList<SuppliersDTO> getAll() throws SQLException;

    boolean add(SuppliersDTO DTO) throws SQLException;

    boolean update(SuppliersDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;
}
