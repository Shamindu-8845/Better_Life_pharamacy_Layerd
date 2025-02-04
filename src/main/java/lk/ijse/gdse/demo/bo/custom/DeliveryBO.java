package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.DeliveryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryBO extends SuperBO {

    ArrayList<DeliveryDTO> getAll() throws SQLException;

    boolean add(DeliveryDTO DTO) throws SQLException;

    boolean update(DeliveryDTO DTO) throws SQLException;

    String generateNewId() throws SQLException;

    int getDeliveryCount() throws SQLException;

}
