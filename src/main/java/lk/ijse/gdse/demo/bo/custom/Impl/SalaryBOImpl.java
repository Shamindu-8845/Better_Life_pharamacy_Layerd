package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.SalaryBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.SalaryDAO;
import lk.ijse.gdse.demo.dto.SalaryDTO;
import lk.ijse.gdse.demo.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryBOImpl implements SalaryBO {

    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALARY);

    @Override
    public ArrayList<SalaryDTO> getAll() throws SQLException {
       ArrayList<SalaryDTO> salaryDTOS = new ArrayList<>();
       ObservableList<Salary> salarys = salaryDAO.getAll();
       for (Salary salary : salarys) {
           salaryDTOS.add(new SalaryDTO(salary.getSalary_Id(),salary.getAmount(),salary.getDate()));
       }
       return salaryDTOS;

    }

    @Override
    public boolean add(SalaryDTO DTO) throws SQLException {
        return salaryDAO.add(new Salary(DTO.getSalary_Id(),DTO.getAmount(),DTO.getDate()));
    }

    @Override
    public boolean update(SalaryDTO DTO) throws SQLException {
        return salaryDAO.update(new Salary(DTO.getSalary_Id(),DTO.getAmount(),DTO.getDate()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return salaryDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return salaryDAO.generateNewId();
    }
}
