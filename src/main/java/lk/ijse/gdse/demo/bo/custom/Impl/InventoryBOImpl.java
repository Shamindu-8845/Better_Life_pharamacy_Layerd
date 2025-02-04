package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.InventoryBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.InventoryDAO;
import lk.ijse.gdse.demo.dto.InventoryDTO;
import lk.ijse.gdse.demo.entity.Inventory;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {

    private InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY);

    @Override
    public ArrayList<InventoryDTO> getAll() throws SQLException {
        ArrayList<InventoryDTO> inventoryDTOs = new ArrayList<>();
        ObservableList<Inventory> all = inventoryDAO.getAll();
        for (Inventory inventory : all) {
            inventoryDTOs.add(new InventoryDTO(inventory.getInventory_Id(),inventory.getRecord_Level(),inventory.getStock_Level()));
        }
        return inventoryDTOs;
    }

    @Override
    public boolean add(InventoryDTO DTO) throws SQLException {
        return inventoryDAO.add(new Inventory(DTO.getInventory_Id(),DTO.getRecord_Level(),DTO.getStock_Level()));
    }

    @Override
    public boolean update(InventoryDTO DTO) throws SQLException {
        return inventoryDAO.update(new Inventory(DTO.getInventory_Id(),DTO.getRecord_Level(),DTO.getStock_Level()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return inventoryDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return inventoryDAO.generateNewId();
    }
}
