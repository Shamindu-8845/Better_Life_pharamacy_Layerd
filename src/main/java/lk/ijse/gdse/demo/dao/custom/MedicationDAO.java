package lk.ijse.gdse.demo.dao.custom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.MedicationDTO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.entity.Medication;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface MedicationDAO extends CrudDAO<Medication> {

    MedicationDTO findById(String medicationId) throws SQLException;
    ArrayList<String> getAllMedicationIds() throws SQLException;
    boolean isMedicationExists(String medicationId) throws SQLException;
    int getTotalMedications() throws SQLException;
    String getMedicationNameById(String medicationId) throws SQLException;
    boolean reduceQty(OderDetailsDTO orderDetailsDTO) throws SQLException;
    String countExpiredMedications() throws SQLException;
    boolean updateStockLevel(String medicationId, int newStockLevel, Connection connection) throws SQLException;
    int getStockLevel(String medicationId) throws SQLException;


}
