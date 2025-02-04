package lk.ijse.gdse.demo.bo.custom.Impl;

import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.bo.custom.UserBO;
import lk.ijse.gdse.demo.dao.DAOFactory;
import lk.ijse.gdse.demo.dao.custom.UserDAO;
import lk.ijse.gdse.demo.dto.UserDTO;
import lk.ijse.gdse.demo.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public ArrayList<UserDTO> getAll() throws SQLException {
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        ObservableList<User> users = userDAO.getAll();
        for (User user : users) {
            userDTOS.add(new UserDTO(user.getUserId(),user.getUserName(),user.getPassword()));

        }
        return userDTOS;

    }

    @Override
    public String generateNewId() throws SQLException {
        return userDAO.generateNewId();
    }

    @Override
    public boolean add(UserDTO userDTO) throws SQLException {
        return userDAO.add(new User(userDTO.getUserId(),userDTO.getUserName(),userDTO.getPassword()));
    }

    @Override
    public boolean delete(String Id) throws SQLException {
        return userDAO.delete(Id);
    }

    @Override
    public boolean update(UserDTO userDTO) throws SQLException {
        return userDAO.update(new User(userDTO.getUserId(),userDTO.getUserName(),userDTO.getPassword()));
    }
}
