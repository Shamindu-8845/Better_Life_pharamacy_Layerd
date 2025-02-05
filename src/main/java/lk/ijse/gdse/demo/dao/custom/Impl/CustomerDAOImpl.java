package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.CustomerDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.CustomerDTO;
import lk.ijse.gdse.demo.entity.Customer;
import lk.ijse.gdse.demo.util.CrudUtil;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ObservableList<Customer> getAll() throws SQLException {

       ResultSet resultSet =  SQLUtil.execute("Select * from customer");
        ObservableList<Customer> customerDto = FXCollections.observableArrayList();

        while (resultSet.next()) {
            customerDto.add(new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
        }
        return customerDto;

    }

    public CustomerDTO findById(String selectedCusId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }

    @Override
    public boolean add(Customer customerDTO) throws SQLException {
       return SQLUtil.execute("INSERT INTO Customer (Customer_Id, Name, Phone_no, Address) VALUES (?, ?, ?, ?)",
               customerDTO.getCustomer_Id(),
               customerDTO.getName(),
               customerDTO.getPhone_no(),
               customerDTO.getAddress());

    }

    public  ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");
        ArrayList<String> customerIds = new ArrayList<>();
        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    @Override
    public boolean update(Customer customerDTO) throws SQLException {
        return SQLUtil.execute("UPDATE Customer SET Name=?, Phone_no=?, Address=? WHERE Customer_Id=?",
                customerDTO.getName(),
                customerDTO.getPhone_no(),
                customerDTO.getAddress(),
                customerDTO.getCustomer_Id());
    }

    @Override
    public boolean delete(String Id) throws SQLException {
      return SQLUtil.execute("DELETE FROM Customer WHERE Customer_Id=?",Id);
    }

    public  String getCustomerNameByCustomerId(String customerId) throws SQLException {
        String sql = "SELECT Name FROM customer WHERE Customer_Id = ?";

        try (PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("Name");
            }
        }
        return null;
    }

    public  String getCustomerPhoneByCustomerId(String customerId) throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT Phone_no FROM customer WHERE Customer_Id = ?",customerId);
        if (rs.next()) {
            return rs.getString("Phone_No");
        }
        return null;
    }

    public  String getCustomerIdByPhoneNo(String phoneNo) throws SQLException {
       ResultSet rs =  SQLUtil.execute("SELECT Customer_Id FROM customer WHERE Phone_No = ?",phoneNo);

        if (rs.next()) {
            return rs.getString("Customer_Id");
        }
        return null;
    }

    @Override
    public String getNextCustomer_Id() throws SQLException {
        return "";
    }

    @Override
    public  String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT Customer_Id FROM Customer ORDER BY Customer_Id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String numericPart = lastId.substring(1);
            int newId = Integer.parseInt(numericPart) + 1;
            return String.format("C%03d", newId);
        }
        return "C001";
    }

    public  String getCustomerNameByPhoneNo(String phoneNo) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT Name FROM Customer WHERE Phone_no = ?", phoneNo);
        if (resultSet.next()) {
            return resultSet.getString("Name");
        }
        return null;
    }

    public int getTotalCustomers() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(Customer_Id) AS total_customers FROM Customer");
       if (resultSet.next()) {
           return resultSet.getInt("total_customers");
       }
        return 0;
    }

    public  boolean isCustomerIdExists(String customerId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM customer WHERE Customer_Id = ?", customerId);
        if (resultSet != null && resultSet.next()) {
            return resultSet.getInt(1) > 0;  // Correctly checks if the count is greater than 0
        }
        return false;

    }

    @Override
    public String getCustomerDetailsbyName(String name) throws SQLException {

        ResultSet resultSet = SQLUtil.execute("SELECT Customer_Id, Phone_no FROM Customer WHERE Name = ?",name);


        if (resultSet.next()) {
            return "Customer ID: " + resultSet.getString("Customer_Id") +
                    ", Phone Number: " + resultSet.getString("Phone_no");
        }
        return "Customer Not Found";
    }


}
