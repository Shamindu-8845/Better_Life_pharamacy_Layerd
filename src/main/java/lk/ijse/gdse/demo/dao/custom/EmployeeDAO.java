package lk.ijse.gdse.demo.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.dto.EmployeeDTO;
import lk.ijse.gdse.demo.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee>{

    // Method to get employee count
    int getEmployeeCount() throws SQLException;



}
