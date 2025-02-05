package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    int getTotalCustomers() throws SQLException;

    String getCustomerIdByPhoneNo(String phoneNo) throws SQLException;

    String getCustomerNameByPhoneNo(String phoneNo) throws SQLException;

    ArrayList<CustomerDTO> getAll() throws SQLException;

    boolean add(CustomerDTO DTO) throws SQLException;

    boolean update(CustomerDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;

    String getCustomerDetailsbyName(String name)throws SQLException;
}
