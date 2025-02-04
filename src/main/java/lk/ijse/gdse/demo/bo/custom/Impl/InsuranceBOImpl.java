package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.InsuranceBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.InsuranceDAO;
import lk.ijse.gdse.demo.dto.InsuranceDTO;
import lk.ijse.gdse.demo.entity.Insurance;

import java.sql.SQLException;
import java.util.ArrayList;

public class InsuranceBOImpl implements InsuranceBO {

    private InsuranceDAO insuranceDAO = (InsuranceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSURANCE);

    @Override
    public ArrayList<String> getCompanyNames() throws SQLException {
        return insuranceDAO.getCompanyNames();
    }

    @Override
    public String getDiscountByCompanyName(String companyName) throws SQLException {
        return insuranceDAO.getDiscountByCompanyName(companyName);
    }

    @Override
    public ArrayList<InsuranceDTO> getAll() throws SQLException {
        ArrayList<InsuranceDTO> insuranceDTOS = new ArrayList<>();
        ObservableList<Insurance> all = insuranceDAO.getAll();
        for (Insurance insurance : all) {
            insuranceDTOS.add(new InsuranceDTO(insurance.getInsurance_Id(),insurance.getCustomer_Id(),insurance.getCompany_Name(),insurance.getDiscount()));
        }
        return insuranceDTOS;
    }

    @Override
    public boolean update(InsuranceDTO DTO) throws SQLException {
        return insuranceDAO.update(new Insurance(DTO.getInsurance_Id(),DTO.getCustomer_Id(),DTO.getCompany_Name(),DTO.getDiscount()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return insuranceDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return insuranceDAO.generateNewId();
    }
}
