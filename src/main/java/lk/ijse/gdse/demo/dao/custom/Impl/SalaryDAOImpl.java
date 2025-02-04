package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.SalaryDAO;
import lk.ijse.gdse.demo.dto.EmployeeDTO;
import lk.ijse.gdse.demo.entity.Salary;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryDAOImpl implements SalaryDAO {

    @Override
    public ObservableList<Salary> getAll() throws SQLException {
       /* // Query to select all details from the Salary table
        String query = "SELECT Salary_Id, Amount, Date FROM Salary";

        // Execute the query using CrudUtil (Assumed utility class to execute SQL queries)
        ResultSet resultSet = CrudUtil.execute(query);

        // Create an ObservableList to hold SalaryDTO objects
        ObservableList<Salary> salaryList = FXCollections.observableArrayList();

        // Iterate through the result set and create SalaryDTO objects
        while (resultSet.next()) {
            Salary salaryDTO = new Salary(
                    resultSet.getString("Salary_Id"),
                    resultSet.getDouble("Amount"), // Amount is a decimal type
                    resultSet.getDate("Date")           // Date is a date type
            );
            salaryList.add(salaryDTO);
        }

        // Return the list of SalaryDTO objects
        return salaryList;*/

        ResultSet resultSet = SQLUtil.executeQuery("SELECT Salary_Id, Amount, Date FROM Salary");
        ObservableList<Salary> salaries = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Salary salary = new Salary(
                    resultSet.getString("Salary_Id"),
                    resultSet.getDouble("Amount"),
                    resultSet.getDate("Date")

            );
            salaries.add(salary);
        }
        return FXCollections.observableList(salaries);
    }

    @Override
    public boolean add(Salary salaryDTO) throws SQLException {
        /*return CrudUtil.execute(
                "INSERT INTO Salary (Salary_Id, Amount, Date) VALUES (?, ?, ?)",
                salaryDTO.getSalary_Id(),
                salaryDTO.getAmount(),
                salaryDTO.getDate()
        );*/
       return SQLUtil.execute("INSERT INTO Salary (Salary_Id, Amount, Date) VALUES (?, ?, ?)",
                salaryDTO.getSalary_Id(),
                salaryDTO.getAmount(),
                salaryDTO.getDate());

    }
    @Override
    public boolean update(Salary salaryDTO) throws SQLException {
        /*return CrudUtil.execute(
                "UPDATE Salary SET Amount=?, Date=? WHERE Salary_Id=?",
                salaryDTO.getAmount(),
                salaryDTO.getDate(),
                salaryDTO.getSalary_Id()
        );*/

       return SQLUtil.execute("UPDATE Salary SET Amount=?, Date=? WHERE Salary_Id=?",
                salaryDTO.getAmount(),
                salaryDTO.getDate(),
                salaryDTO.getSalary_Id());
    }
    @Override
    public  String generateNewId() throws SQLException {
        ResultSet rs = SQLUtil.execute("SELECT Salary_Id FROM Salary ORDER BY Salary_Id DESC LIMIT 1");

        if (rs.next()) {
            String lastId = rs.getString(1);
            String subString = lastId.substring(1);
            int I = Integer.parseInt(subString);
            int newIndex = I + 1;
            return String.format("S%04d", newIndex);  // Adjust the format if your `Salary_Id` has different length requirements
        }

        return "S0001";
    }
    @Override
    public boolean delete(String Id) throws SQLException {
        /*return CrudUtil.execute(
                "DELETE FROM salary WHERE Salary_Id=?",
                Id
        );*/

       return SQLUtil.execute("DELETE FROM salary WHERE Salary_Id=?",Id);
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        String query = "UPDATE Employee SET Salary_Id=?, Roll=?, Name=? WHERE Employee_Id=?";

        return CrudUtil.execute(query, employeeDTO.getSalary_Id(), employeeDTO.getRoll(), employeeDTO.getName(), employeeDTO.getEmployee_Id());
    }



}
