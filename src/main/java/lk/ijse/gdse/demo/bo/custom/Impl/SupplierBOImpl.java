package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.SupplierBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.SupplierDAO;
import lk.ijse.gdse.demo.dto.SuppliersDTO;
import lk.ijse.gdse.demo.entity.Suppliers;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    private SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public boolean isSupplierIdExists(String supId) throws SQLException {
        return supplierDAO.isSupplierIdExists(supId);
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        return supplierDAO.getAllCustomerIds();
    }

    @Override
    public int getTotalSuppliers() throws SQLException {
        return supplierDAO.getTotalSuppliers();
    }

    @Override
    public ArrayList<SuppliersDTO> getAll() throws SQLException {
        ArrayList<SuppliersDTO> supplierDTOs = new ArrayList<>();
       ObservableList<Suppliers> suppliers = supplierDAO.getAll();
        for (Suppliers supplier : suppliers) {
            supplierDTOs.add(new SuppliersDTO(supplier.getSup_Id(),supplier.getName(),supplier.getPhone_No()));
        }
        return supplierDTOs;


    }

    @Override
    public boolean add(SuppliersDTO DTO) throws SQLException {
        return supplierDAO.add(new Suppliers(DTO.getSup_Id(),DTO.getName(),DTO.getPhone_No()));
    }

    @Override
    public boolean update(SuppliersDTO DTO) throws SQLException {
        return supplierDAO.update(new Suppliers(DTO.getSup_Id(),DTO.getName(),DTO.getPhone_No()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return supplierDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return supplierDAO.generateNewId();
    }
}
