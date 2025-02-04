package lk.ijse.gdse.demo.dao.custom;


import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.dto.CustomerDTO;
import lk.ijse.gdse.demo.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer>{


    CustomerDTO findById(String selectedCusId) throws SQLException;

    ArrayList<String> getAllCustomerIds() throws SQLException;

    String getCustomerNameByCustomerId(String customerId) throws SQLException;

    String getCustomerPhoneByCustomerId(String customerId) throws SQLException;

    String getCustomerIdByPhoneNo(String phoneNo) throws SQLException;

    String getNextCustomer_Id() throws SQLException;

    String getCustomerNameByPhoneNo(String phoneNo) throws SQLException;

    int getTotalCustomers() throws SQLException;

    boolean isCustomerIdExists(String customerId) throws SQLException;


    String getCustomerDetailsbyName(String name) throws SQLException;

}
