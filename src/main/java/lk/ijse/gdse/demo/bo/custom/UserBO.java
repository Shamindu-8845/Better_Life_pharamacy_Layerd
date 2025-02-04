package lk.ijse.gdse.demo.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.UserDTO;
import lk.ijse.gdse.demo.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDTO> getAll() throws SQLException;
    String generateNewId() throws SQLException;
    boolean add(UserDTO userDTO) throws SQLException;
    boolean delete(String Id) throws SQLException;
    boolean update(UserDTO userDTO) throws SQLException;
}
