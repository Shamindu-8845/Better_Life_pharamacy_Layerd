package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.PaymentBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.PaymentDAO;
import lk.ijse.gdse.demo.dto.PaymentDTO;
import lk.ijse.gdse.demo.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        ObservableList<Payment> payments = paymentDAO.getAll();
        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(payment.getPayment_Id(),payment.getDate(),payment.getAmount(),payment.getPayment_Type(),payment.getStatus(),payment.getInsurance_Id()));
        }
        return paymentDTOS;
    }

    @Override
    public boolean add(PaymentDTO DTO) throws SQLException {
        return paymentDAO.add(new Payment(DTO.getPayment_Id(),DTO.getDate(),DTO.getAmount(),DTO.getPayment_Type(),DTO.getStatus(),DTO.getInsurance_Id()));
    }

    @Override
    public boolean update(PaymentDTO DTO) throws SQLException {
        return paymentDAO.update(new Payment(DTO.getPayment_Id(),DTO.getDate(),DTO.getAmount(),DTO.getPayment_Type(),DTO.getStatus(),DTO.getInsurance_Id()));
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return paymentDAO.delete(customerId);
    }

    @Override
    public String generateNewId() throws SQLException {
        return paymentDAO.generateNewId();
    }
}
