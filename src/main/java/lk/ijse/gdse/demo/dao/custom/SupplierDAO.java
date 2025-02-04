package lk.ijse.gdse.demo.dao.custom;

import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.entity.Suppliers;

import java.sql.*;
import java.util.ArrayList;


public interface SupplierDAO extends CrudDAO<Suppliers> {

    boolean isSupplierIdExists(String supId) throws SQLException;

    ArrayList<String> getAllCustomerIds() throws SQLException;

    int getTotalSuppliers() throws SQLException;
}
