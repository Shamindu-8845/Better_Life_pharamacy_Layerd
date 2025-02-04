package lk.ijse.gdse.demo.bo.custom;


import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {

    String generateNewId() throws SQLException;

    boolean add(OrderDTO DTO) throws SQLException;

    boolean save(OrderDTO orderDTO) throws SQLException;

    boolean saveOrderDetails(ArrayList<OderDetailsDTO> orderDetailsDTO);

    boolean updateMedication(ArrayList<OderDetailsDTO> orderDetailsDTO);

    boolean saveOrderDetail(ArrayList<OderDetailsDTO> orderDetailsDTO);

}
