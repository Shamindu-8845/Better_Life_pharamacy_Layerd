package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.SupplierDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.SuppliersDTO;
import lk.ijse.gdse.demo.entity.Suppliers;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public  ObservableList<Suppliers> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT Sup_Id, Name, Phone_No FROM Suppliers");
        List<Suppliers> suppliers = new ArrayList<>();
        while (resultSet.next()) {
            Suppliers sup = new Suppliers(
                    resultSet.getString("Sup_Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone_No")
            );
            suppliers.add(sup);
        }
        return FXCollections.observableList(suppliers);
    }

    public boolean saveSuppliers(SuppliersDTO suppliersDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Suppliers values (?,?,?,?,?)",
                suppliersDTO.getSup_Id(),
                suppliersDTO.getName(),
                suppliersDTO.getName(),
                suppliersDTO.getPhone_No()
        );
    }

    public  boolean isSupplierIdExists(String supId) throws SQLException {
        String query = "SELECT COUNT(*) FROM suppliers WHERE Sup_Id = ?";
        DBConnection dbConnection = DBConnection.getInstance();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, supId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }

    public boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("delete from Suppliers where Sup_Id=?", Id);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Sup_Id from Suppliers");

        ArrayList<String> SupplierIds = new ArrayList<>();

        while (rst.next()) {
            SupplierIds.add(rst.getString(1));
        }

        return SupplierIds;
    }
    @Override
    public boolean add(Suppliers supplierDTO) throws SQLException {
       return SQLUtil.execute("insert into suppliers (Sup_Id, Name, Phone_No) VALUES (?,?,?)",
                supplierDTO.getSup_Id(),
                supplierDTO.getName(),
                supplierDTO.getPhoneNo());

    }
    @Override
    public boolean update(Suppliers supplierDTO) throws SQLException {

       return SQLUtil.execute("update Suppliers set  Name=?, Phone_NO=? where Sup_Id=?",
                supplierDTO.getName(),
                supplierDTO.getPhone_No(),
                supplierDTO.getSup_Id());
    }
    @Override
    public  String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select Sup_Id from Suppliers order by Sup_Id desc limit 1");

        if (resultSet.next()){
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public int getTotalSuppliers() throws SQLException {
        String query = "SELECT COUNT(Sup_Id) FROM Suppliers";
        ResultSet resultSet = CrudUtil.execute(query);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }
    }
}
