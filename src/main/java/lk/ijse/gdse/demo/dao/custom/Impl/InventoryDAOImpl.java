package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.InventoryDAO;

import lk.ijse.gdse.demo.entity.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;


public class InventoryDAOImpl implements InventoryDAO {


    @Override
    public  ObservableList<Inventory> getAll() throws SQLException {
       ResultSet resultSet = SQLUtil.execute("SELECT Inventory_Id, Stock_Level, Record_Level FROM Inventory");
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            inventoryList.add(new Inventory(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3)));
        }

        return inventoryList;
    }


    @Override
    public String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select Inventory_Id from Inventory order by Inventory_Id desc limit 1");

        if (resultSet.next()){
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    @Override
    public  boolean add(Inventory inventory) throws SQLException {
       return SQLUtil.execute("INSERT INTO inventory (Inventory_Id, Record_Level, Stock_Level) VALUES (?, ?, ?)",
                inventory.getInventory_Id(),
                inventory.getRecord_Level(),
                inventory.getStock_Level());
    }

    @Override
    public  boolean update(Inventory inventory) throws SQLException {
      return SQLUtil.execute("UPDATE inventory SET Record_Level = ?, Stock_Level = ? WHERE Inventory_Id = ?",
                inventory.getRecord_Level(),
                inventory.getStock_Level(),
                inventory.getInventory_Id());

    }

    @Override
    public  boolean delete(String Id) throws SQLException {
        return SQLUtil.execute("DELETE FROM inventory WHERE Inventory_Id = ?", Id);
    }

}
