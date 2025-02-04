package lk.ijse.gdse.demo.bo.custom;

import lk.ijse.gdse.demo.bo.SuperBO;
import lk.ijse.gdse.demo.dto.MedicationDTO;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface MedicationBO extends SuperBO {

    MedicationDTO findById(String medicationId) throws SQLException;

    ArrayList<String> getAllMedicationIds() throws SQLException;

    boolean isMedicationExists(String medicationId) throws SQLException;

    boolean updateStockLevel(String medicationId, int newStockLevel, Connection connection) throws SQLException;

    int getStockLevel(String medicationId) throws SQLException;

    ArrayList<MedicationDTO> getAll() throws SQLException;

    boolean add(MedicationDTO DTO) throws SQLException;

    boolean update(MedicationDTO DTO) throws SQLException;

    boolean delete(String customerId) throws SQLException;

    String generateNewId() throws SQLException;

    String countExpiredMedications() throws SQLException;

    int getTotalMedications() throws SQLException;

}
