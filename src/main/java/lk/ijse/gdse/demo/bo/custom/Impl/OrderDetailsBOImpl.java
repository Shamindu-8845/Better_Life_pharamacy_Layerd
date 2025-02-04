package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.OrderDetailsBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.entity.OderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBOImpl implements OrderDetailsBO {

    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAILS);



    @Override
    public double getTotalPrice() throws SQLException {
        return orderDetailsDAO.getTotalPrice();
    }

    @Override
    public ArrayList<OderDetailsDTO> getAll() throws SQLException {
        ArrayList<OderDetailsDTO> oderDetailsDTOS = new ArrayList<>();
        ObservableList<OderDetails> oderDetailsList = orderDetailsDAO.getAll();
        for (OderDetails oderDetails : oderDetailsList) {
            oderDetailsDTOS.add(new OderDetailsDTO(oderDetails.getOrder_Id(),oderDetails.getMedication_Id(),oderDetails.getQuantity(),oderDetails.getPrice()));
        }
         return oderDetailsDTOS;
    }

    @Override
    public boolean add(OderDetailsDTO DTO) throws SQLException {
        return orderDetailsDAO.add(new OderDetails(DTO.getOrder_Id(),DTO.getMedication_Id(),DTO.getQuantity(),DTO.getPrice()));
    }

    @Override
    public boolean update(OderDetailsDTO DTO) throws SQLException {
        return orderDetailsDAO.update(new OderDetails(DTO.getOrder_Id(),DTO.getMedication_Id(),DTO.getQuantity(),DTO.getPrice()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return orderDetailsDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return orderDetailsDAO.generateNewId();
    }


}
