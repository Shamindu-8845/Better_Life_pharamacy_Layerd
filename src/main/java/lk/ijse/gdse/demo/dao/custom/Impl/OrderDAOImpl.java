package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.OrderDAO;
import lk.ijse.gdse.demo.entity.Order;
import lk.ijse.gdse.demo.util.CrudUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.*;

@Getter
@Setter
@ToString
public class OrderDAOImpl implements OrderDAO{

    @Override
    public ObservableList<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean add(Order DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order DTO) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return false;
    }

    @Override
    public  String generateNewId() throws SQLException {
        ResultSet rs = SQLUtil.execute("select Order_Id from Orders order by Order_Id desc limit 1");

        if (rs.next()) {
            String lastId = rs.getString(1);
            String subString  = lastId.substring(1);
            int I = Integer.parseInt(subString);
            int newIndex = I + 1;
            return String.format("O%03d", newIndex);
        }

        return  "O001";
    }


}
