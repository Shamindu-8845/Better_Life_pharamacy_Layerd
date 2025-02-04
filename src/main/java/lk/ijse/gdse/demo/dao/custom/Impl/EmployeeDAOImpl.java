package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.EmployeeDAO;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.entity.Employee;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAOImpl implements EmployeeDAO{

    @Override
    public ObservableList<Employee> getAll() throws SQLException {

       ResultSet resultSet =  SQLUtil.execute("SELECT e.Employee_Id, e.Name, e.Roll, e.Salary_Id FROM Employee e JOIN Salary s ON s.Salary_Id = e.Salary_Id");

        /*String query = "SELECT e.Employee_Id, e.Name, e.Roll, e.Salary_Id " +
                "FROM Employee e JOIN Salary s ON s.Salary_Id = e.Salary_Id";

        ResultSet resultSet = CrudUtil.execute(query);
*/
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();


        while (resultSet.next()) {
            employeeList.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),resultSet.
                    getString(3),
                    resultSet.getString(4)));

           /* Employee employeeDTO = new Employee(
                    resultSet.getString("Employee_Id"),
                    resultSet.getString("Salary_Id"),
                    resultSet.getString("Roll"),
                    resultSet.getString("Name")
            );
            employeeList.add(employeeDTO);*/
        }

        return employeeList;
    }

    // Method to get employee count
    public  int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(Employee_Id) AS employee_count FROM employee";

        try (PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_count");
            }
        }
        return 0;  // Return 0 if no employees found
    }
    @Override
    public boolean add(Employee employeeDTO) throws SQLException {
        /*// SQL query to insert the employee data into the Employee table
        String query = "INSERT INTO Employee (Employee_Id, Salary_Id, Roll, Name) VALUES (?, ?, ?, ?)";

        // Execute the query using CrudUtil (assuming it's a utility for handling SQL queries)
        return CrudUtil.execute(query, employeeDTO.getEmployee_Id(), employeeDTO.getSalary_Id(), employeeDTO.getRoll(), employeeDTO.getName());
  */
       return SQLUtil.execute("INSERT INTO Employee (Employee_Id, Salary_Id, Roll, Name) VALUES (?, ?, ?, ?)",
                employeeDTO.getEmployee_Id(), employeeDTO.getSalary_Id(), employeeDTO.getRoll(), employeeDTO.getName());
    }
    @Override
    public boolean update(Employee employeeDTO) throws SQLException {
        /*String query = "UPDATE Employee SET Salary_Id=?, Roll=?, Name=? WHERE Employee_Id=?";

        return CrudUtil.execute(query, employeeDTO.getSalary_Id(), employeeDTO.getRoll(), employeeDTO.getName(), employeeDTO.getEmployee_Id());
 */
       return SQLUtil.execute("UPDATE Employee SET Salary_Id=?, Roll=?, Name=? WHERE Employee_Id=?",
                employeeDTO.getSalary_Id(),
                employeeDTO.getRoll(),
                employeeDTO.getName(),
                employeeDTO.getEmployee_Id());

    }
    @Override
    public boolean delete(String Id) throws SQLException {
        /*String query = "DELETE FROM Employee WHERE Employee_Id=?";

        return CrudUtil.execute(query, Id);*/

       return SQLUtil.execute("DELETE FROM Employee WHERE Employee_Id=?", Id);
    }
    @Override
    public  String generateNewId() throws SQLException {
        // Query to get the last Employee_Id from the Employee table
        ResultSet rs = SQLUtil.execute("SELECT Employee_Id FROM Employee ORDER BY Employee_Id DESC LIMIT 1");

        if (rs.next()) {
            // Get the last Employee_Id value
            String lastId = rs.getString(1);

            // Extract the numeric part of the Employee_Id (assuming Employee_Id starts with a letter)
            String subString = lastId.substring(1);  // Assuming the ID format is something like E001, E002, etc.

            try {
                // Convert the numeric part of the Employee_Id to an integer and increment by 1
                int i = Integer.parseInt(subString);
                int newIndex = i + 1;

                // Return the new Employee_Id, formatted with leading zeros
                return String.format("E%03d", newIndex);  // Adjust the format as per your Employee_Id length requirements
            } catch (NumberFormatException e) {
                // Handle cases where the substring is not a valid integer
                e.printStackTrace();
                return null;
            }
        }

        // Return null if no records are found (in case of an empty table)
        return "E001";
    }



}
