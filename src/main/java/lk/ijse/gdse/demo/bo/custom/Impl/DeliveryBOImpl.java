package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.DeliveryBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.DeliveryDAO;
import lk.ijse.gdse.demo.dto.DeliveryDTO;
import lk.ijse.gdse.demo.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryBOImpl implements DeliveryBO {

    private DeliveryDAO deliveryDAO = (DeliveryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVERY);

    @Override
    public ArrayList<DeliveryDTO> getAll() throws SQLException {
        ArrayList<DeliveryDTO> deliveryDTOS = new ArrayList<>();
        ObservableList<Delivery> all = deliveryDAO.getAll();
        for (Delivery delivery : all) {
            deliveryDTOS.add(new DeliveryDTO(delivery.getDelivery_Id(),delivery.getDelivery_Date(),delivery.getOrder_Id(),delivery.getLocation(),delivery.getCustomer_PhoneNo(),delivery.getRemoveBtn()));
        }
        return deliveryDTOS;
    }

    @Override
    public boolean add(DeliveryDTO DTO) throws SQLException {
        return deliveryDAO.add(new Delivery(DTO.getDelivery_Id(),DTO.getDelivery_Date(),DTO.getOrder_Id(),DTO.getLocation(),DTO.getCustomer_PhoneNo(),DTO.getRemoveBtn()));
    }

    @Override
    public boolean update(DeliveryDTO DTO) throws SQLException {
        return deliveryDAO.update(new Delivery(DTO.getDelivery_Id(),DTO.getDelivery_Date(),DTO.getOrder_Id(),DTO.getLocation(),DTO.getCustomer_PhoneNo(),DTO.getRemoveBtn()));
    }


    @Override
    public String generateNewId() throws SQLException {
        return deliveryDAO.generateNewId();
    }

    @Override
    public int getDeliveryCount() throws SQLException {
        return deliveryDAO.getDeliveryCount();
    }


}
