package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.InsuranceDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.MedicationDTO;
import lk.ijse.gdse.demo.entity.Insurance;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsuranceDAOImpl implements InsuranceDAO {

    @Override
    public  ObservableList<Insurance> getAll() throws SQLException {
      /*  List<Insurance> insuranceDTOList = new ArrayList<>();
        String query = "SELECT * FROM insurance";*/

       ResultSet resultSet =  SQLUtil.execute("SELECT * FROM insurance");
       ObservableList<Insurance> insuranceObservableList = FXCollections.observableArrayList();

       while (resultSet.next()) {
           insuranceObservableList.add(new Insurance(
                   resultSet.getString(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getDouble(4)));
       }

       return insuranceObservableList;

       /* Connection con = DBConnection.getInstance().getConnection();
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String InsuranceId = resultSet.getString("Insurance_Id");
            String CusstomerId = resultSet.getString("Customer_Id");
            String CompanuName = resultSet.getString("Company_Name");
            double dis = resultSet.getDouble("Discount");

            insuranceDTOList.add(new Insurance(InsuranceId,CusstomerId,CompanuName,dis));
        }

        return FXCollections.observableArrayList(insuranceDTOList);
*/
    }

    @Override
    public  String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("select Insurance_Id from Insurance order by Insurance_Id desc limit 1");

        if (resultSet.next()){
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    public  MedicationDTO findyByName(String medicationId) throws SQLException {
        String sql = "SELECT Company_Name from Insurance WHERE Insurance_Id = ?";
        try (PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pst.setString(1, medicationId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Create and return MedicationDTO if record is found
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
        return null; // Return null if no result is found
    }

    public  ArrayList<String> getCompanyNames() throws SQLException {
        // Execute SQL query to get all company names
        ResultSet rst = CrudUtil.execute("SELECT Company_Name FROM insurance");

        // Create an ArrayList to store the company names
        ArrayList<String> companyNames = new ArrayList<>();

        // Iterate through the result set and add each company name to the list
        while (rst.next()) {
            companyNames.add(rst.getString(1));
        }

        // Return the list of company names
        return companyNames;
    }


    public  String getDiscountByCompanyName(String companyName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Discount FROM insurance WHERE Company_Name = ?", companyName);
        if (rst.next()) {
            int discount = rst.getInt("Discount"); // Assuming the `Discount` column is now an `INT`
            return discount + "%"; // Return the discount as a percentage string
        }
        return "0%"; // Return "0%" if no discount is found
    }



//    public boolean updateInsurance(InsuranceDTO insurance) {
//        return true;
//    }

    @Override
    public boolean delete(String Id) {
        return true;
    }

//    public boolean addInsurance(InsuranceDTO insurance) {
//        return true;
//    }
    @Override
    public  boolean update(Insurance insurance) throws SQLException {
        /*// Update query to modify the existing insurance record
        String sql = "UPDATE insurance SET Customer_Id = ?, Company_Name = ?, Discount = ? WHERE Insurance_Id = ?";
        return CrudUtil.execute(
                sql,
                insurance.getCustomer_Id(),
                insurance.getCompany_Name(),
                insurance.getDiscount(),
                insurance.getInsurance_Id()
        );*/

       return SQLUtil.execute("UPDATE insurance SET Customer_Id = ?, Company_Name = ?, Discount = ? WHERE Insurance_Id = ?",
                insurance.getCustomer_Id(),
                insurance.getCompany_Name(),
                insurance.getDiscount(),
                insurance.getInsurance_Id());
    }
    @Override
    public  boolean add(Insurance insurance) throws SQLException {
        /*// Insert query to add a new insurance record
        String sql = "INSERT INTO insurance (Insurance_Id, Customer_Id, Company_Name, Discount) VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(
                sql,
                insurance.getInsurance_Id(),
                insurance.getCustomer_Id(),
                insurance.getCompany_Name(),
                insurance.getDiscount()
        );*/

       return SQLUtil.execute("INSERT INTO insurance (Insurance_Id, Customer_Id, Company_Name, Discount) VALUES (?, ?, ?, ?)",
                insurance.getInsurance_Id(),
                insurance.getCustomer_Id(),
                insurance.getCompany_Name(),
                insurance.getDiscount());
    }


}
