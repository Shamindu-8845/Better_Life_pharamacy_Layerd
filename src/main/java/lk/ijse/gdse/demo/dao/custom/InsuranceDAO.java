package lk.ijse.gdse.demo.dao.custom;


import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.dto.InsuranceDTO;
import lk.ijse.gdse.demo.dto.MedicationDTO;
import lk.ijse.gdse.demo.entity.Insurance;

import java.sql.*;
import java.util.ArrayList;


public interface InsuranceDAO extends CrudDAO<Insurance> {

    MedicationDTO findyByName(String medicationId) throws SQLException;

    ArrayList<String> getCompanyNames() throws SQLException;

    String getDiscountByCompanyName(String companyName) throws SQLException;



}
