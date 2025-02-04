package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.CustomerBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.CustomerDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.CustomerDTO;
import lk.ijse.gdse.demo.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);


    @Override
    public int getTotalCustomers() throws SQLException {
        return customerDAO.getTotalCustomers();
    }

    @Override
    public String getCustomerIdByPhoneNo(String phoneNo) throws SQLException {
        return customerDAO.getCustomerIdByPhoneNo(phoneNo);
    }

    @Override
    public String getCustomerNameByPhoneNo(String phoneNo) throws SQLException {
        return customerDAO.getCustomerNameByPhoneNo(phoneNo);
    }


    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        ObservableList<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            customerDTOS.add(new CustomerDTO(customer.getCustomer_Id(),customer.getPhone_no(),customer.getName(),customer.getAddress()));
        }
     return customerDTOS;

    }

    @Override
    public boolean update(CustomerDTO DTO) throws SQLException {
        return customerDAO.update(new Customer(DTO.getCustomer_Id(), DTO.getPhone_no(), DTO.getName(), DTO.getAddress()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return customerDAO.generateNewId();
    }

    @Override
    public String getCustomerDetailsbyName(String name) throws SQLException {
        return customerDAO.getCustomerDetailsbyName(name);
    }

    //Transaction part from CustomerDAOImpl
    public  boolean add(CustomerDTO customerDTO) throws SQLException {
        Connection connection = null;
        connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false); // 1

            // @isCustomerSaved: Saves the customer details into the customer table
            /*boolean isCustomerSaved = CrudUtil.execute(
                    "insert into customer values(?,?,?,?)",
                    customerDTO.getCustomer_Id(),
                    customerDTO.getPhone_no(),
                    customerDTO.getName(),
                    customerDTO.getAddress()
            );*/

            boolean isCustomerSaved = customerDAO.add(new Customer(customerDTO.getCustomer_Id(), customerDTO.getPhone_no(), customerDTO.getName(), customerDTO.getAddress()));

            // If the customer is saved successfully
            if (isCustomerSaved) {
                // @commit: Commits the transaction if the customer is saved successfully
                connection.commit(); // 2
                return true;
            } else {
                // @rollback: Rolls back the transaction if saving the customer fails
                connection.rollback(); // 3
                return false;
            }
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); // 4
        }
    }
}
