package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.MedicationBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.MedicationDAO;
import lk.ijse.gdse.demo.dto.MedicationDTO;
import lk.ijse.gdse.demo.entity.Medication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicationBOImpl implements MedicationBO {

    private MedicationDAO medicationDAO = (MedicationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MEDICATION);


    @Override
    public MedicationDTO findById(String medicationId) throws SQLException {
        return medicationDAO.findById(medicationId);
    }

    @Override
    public ArrayList<String> getAllMedicationIds() throws SQLException {
        return medicationDAO.getAllMedicationIds();
    }

    @Override
    public boolean isMedicationExists(String medicationId) throws SQLException {
        return medicationDAO.isMedicationExists(medicationId);
    }


    @Override
    public boolean updateStockLevel(String medicationId, int newStockLevel, Connection connection) throws SQLException {
        return medicationDAO.updateStockLevel(medicationId, newStockLevel, connection);
    }

    @Override
    public int getStockLevel(String medicationId) throws SQLException {
        return medicationDAO.getStockLevel(medicationId);
    }

    @Override
    public ArrayList<MedicationDTO> getAll() throws SQLException {
        ArrayList<MedicationDTO> medicationDTOS = new ArrayList<>();
        ObservableList<Medication> medications = medicationDAO.getAll();
        for (Medication medication : medications) {
            medicationDTOS.add(new MedicationDTO(medication.getMedication_Id(),medication.getName(),medication.getDescription(),medication.getExpiry_Date(),medication.getPrice(),medication.getStock_Level()));
        }
         return medicationDTOS;
    }

    @Override
    public boolean add(MedicationDTO DTO) throws SQLException {
        return medicationDAO.add(new Medication(DTO.getMedication_Id(),DTO.getName(),DTO.getDescription(),DTO.getExpiry_Date(),DTO.getPrice(),DTO.getStock_Level()));
    }

    @Override
    public boolean update(MedicationDTO DTO) throws SQLException {
        return medicationDAO.update(new Medication(DTO.getMedication_Id(),DTO.getName(),DTO.getDescription(),DTO.getExpiry_Date(),DTO.getPrice(),DTO.getStock_Level()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return medicationDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return medicationDAO.generateNewId();
    }

    @Override
    public String countExpiredMedications() throws SQLException {
        return medicationDAO.countExpiredMedications();
    }

    @Override
    public int getTotalMedications() throws SQLException {
        return medicationDAO.getTotalMedications();
    }
}
