package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {

    double getTotalPrice() throws SQLException;

    ArrayList<OderDetailsDTO> getAll() throws SQLException;

    boolean add(OderDetailsDTO DTO) throws SQLException;

    boolean update(OderDetailsDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;


}
