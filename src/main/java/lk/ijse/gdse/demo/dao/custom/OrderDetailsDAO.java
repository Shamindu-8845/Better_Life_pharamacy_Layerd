package lk.ijse.gdse.demo.dao.custom;

import lk.ijse.gdse.demo.dao.CrudDAO;
import lk.ijse.gdse.demo.dto.OderDetailsDTO;
import lk.ijse.gdse.demo.entity.OderDetails;

import java.sql.SQLException;

public interface OrderDetailsDAO extends CrudDAO<OderDetails> {

    double getTotalPrice() throws SQLException;
}
