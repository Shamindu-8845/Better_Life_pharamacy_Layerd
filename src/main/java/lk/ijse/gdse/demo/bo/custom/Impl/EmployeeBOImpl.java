package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.EmployeeBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.EmployeeDAO;
import lk.ijse.gdse.demo.dto.EmployeeDTO;
import lk.ijse.gdse.demo.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);


    @Override
    public int getEmployeeCount() throws SQLException {
        return employeeDAO.getEmployeeCount();
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws SQLException {
       ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
       ObservableList<Employee> all  = employeeDAO.getAll();
       for (Employee employee : all) {
           employeeDTOS.add(new EmployeeDTO(employee.getEmployee_Id(),employee.getSalary_Id(),employee.getRoll(),employee.getName()));
       }
       return employeeDTOS;
    }

    @Override
    public boolean add(EmployeeDTO DTO) throws SQLException {
        return employeeDAO.add(new Employee(DTO.getEmployee_Id(),DTO.getSalary_Id(),DTO.getRoll(),DTO.getName()));
    }

    @Override
    public boolean update(EmployeeDTO DTO) throws SQLException {
        return employeeDAO.update(new Employee(DTO.getEmployee_Id(),DTO.getSalary_Id(),DTO.getRoll(),DTO.getName()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return employeeDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return employeeDAO.generateNewId();
    }
}
