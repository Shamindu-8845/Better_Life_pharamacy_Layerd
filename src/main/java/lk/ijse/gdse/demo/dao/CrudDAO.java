package lk.ijse.gdse.demo.dao;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dto.CustomerDTO;

import java.sql.SQLException;

public interface CrudDAO <T> {

    ObservableList<T> getAll() throws SQLException;

    boolean add(T DTO) throws SQLException;

    boolean update(T DTO) throws SQLException;

    boolean delete(String Id) throws SQLException;

    String generateNewId() throws SQLException;
}
