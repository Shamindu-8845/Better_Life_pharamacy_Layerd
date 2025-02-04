package lk.ijse.gdse.demo.dao.custom;

import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.dto.DeliveryDTO;
import lk.ijse.gdse.demo.entity.Delivery;

import java.io.IOException;
import java.sql.SQLException;

public interface DeliveryDAO extends CrudDAO<Delivery>{


    void insertDelivery(DeliveryDTO deliveryDTO) throws SQLException;

    // Method to get delivery count by delivery_id
    int getDeliveryCount() throws SQLException;



}
