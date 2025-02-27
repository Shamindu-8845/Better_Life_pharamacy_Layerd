package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.MedicationDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.MedicationDTO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.entity.Medication;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAOImpl implements MedicationDAO {
    @Override
    public  ObservableList<Medication> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM medication");
        List<Medication> medications = new ArrayList<>();

        while (resultSet.next()) {
            Medication medication = new Medication(
                    resultSet.getString("Medication_Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getDate("Expiry_Date"),
                    resultSet.getDouble("Price"),
                    resultSet.getInt("Stock_Level")
            );
            medications.add(medication);
        }
        return FXCollections.observableArrayList(medications);

    }

    public  MedicationDTO findById(String medicationId) throws SQLException {
        String sql = "SELECT * FROM Medication WHERE Medication_Id = ?";
        try (PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pst.setString(1, medicationId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new MedicationDTO(
                        rs.getString("Medication_Id"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getDate("Expiry_Date"),
                        rs.getDouble("Price"),
                        rs.getInt("Stock_Level")
                );
            }
        }
        return null;
    }

    public  ArrayList<String> getAllMedicationIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Medication_Id from Medication");
        ArrayList<String> itemIds = new ArrayList<>();
        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }
        return itemIds;
    }
    @Override
    public String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select Medication_Id from Medication order by Medication_Id desc limit 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("M%03d", newIdIndex);
        }
        return "M001";
    }
    @Override
    public boolean update(Medication medicationDTO) throws SQLException {
        return SQLUtil.execute("update Medication set Medication_Id=?, Name=?, Price=?, Stock_Level=?, Expiry_Date=?, Description=? where Medication_Id=?",
                medicationDTO.getMedication_Id(),
                medicationDTO.getName(),
                medicationDTO.getPrice(),
                medicationDTO.getStock_Level(),
                medicationDTO.getExpiry_Date(),
                medicationDTO.getDescription(),
                medicationDTO.getMedication_Id());
    }

    public  boolean isMedicationExists(String medicationId) throws SQLException {
            String query = "SELECT COUNT(*) FROM medication WHERE Medication_Id = ?";
            try (PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(query)) {
                statement.setString(1, medicationId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            return false;
        }
    @Override
    public boolean add(Medication medicationDTO) throws SQLException {
        return SQLUtil.execute("INSERT INTO Medication (Medication_Id, Name, Price, Expiry_Date, Stock_Level, Description) VALUES (?, ?, ?, ?, ?, ?)",
                medicationDTO.getMedication_Id(),
                medicationDTO.getName(),
                medicationDTO.getPrice(),
                medicationDTO.getExpiry_Date(),
                medicationDTO.getStock_Level(),
                medicationDTO.getDescription());
    }
    @Override
    public boolean delete(String Id) throws SQLException {
       return SQLUtil.execute("delete from Medication where Medication_Id=?", Id);
    }


    public int getTotalMedications() throws SQLException {
        String query = "SELECT COUNT(Medication_Id) FROM Medication";
        ResultSet resultSet = CrudUtil.execute(query);

        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }
    }

    public  String getMedicationNameById(String medicationId) throws SQLException {
        String query = "SELECT Name FROM Medication WHERE Medication_Id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, medicationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Name");
            }
        }
        return null;
    }



    public  boolean reduceQty(OderDetailsDTO orderDetailsDTO) throws SQLException {
        return CrudUtil.execute(
                "update Medication set Stock_Level = Stock_Level - ? where Medication_Id = ?",
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getMedication_Id()
        );
    }

    public  String countExpiredMedications() throws SQLException {
        String query = "SELECT COUNT(*) AS expired_count FROM Medication WHERE Expiry_Date < CURDATE()";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("expired_count");
            }
        }
        return "0";
    }

    public  boolean updateStockLevel(String medicationId, int newStockLevel, Connection connection) throws SQLException {
        String sql = "UPDATE Medication SET Stock_Level = ? WHERE Medication_Id = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, newStockLevel);
            pst.setString(2, medicationId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error while updating stock level", e);
        }
    }

    public  int getStockLevel(String medicationId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "SELECT Stock_Level FROM Medication WHERE Medication_Id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, medicationId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Stock_Level");
            }
        }
        return 0;
    }


}
