package lk.ijse.gdse.demo.dao.custom.Impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse.demo.dao.SQLUtil;
import lk.ijse.gdse.demo.dao.custom.UserDAO;
import lk.ijse.gdse.demo.entity.User;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public ObservableList<User> getAll() throws SQLException {
       /* String query = "SELECT User_Id, UserName, Password FROM User";
        ResultSet resultSet = CrudUtil.execute(query);
        ObservableList<User> userList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            User userDTO = new User(
                    resultSet.getString("User_Id"),
                    resultSet.getString("UserName"),
                    resultSet.getString("Password")
            );

            userList.add(userDTO);
        }

        return userList;*/

        ResultSet resultSet = SQLUtil.execute("SELECT User_Id, UserName, Password FROM User");
        ObservableList<User> users = FXCollections.observableArrayList();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getString("User_Id"),
                    resultSet.getString("UserName"),
                    resultSet.getString("Password")
            );
            users.add(user);
        }
        return users;
    }
    @Override
    public  String generateNewId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT User_Id FROM user ORDER BY User_Id DESC LIMIT 1");
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);  // Get the numeric part of the last ID
            int i = Integer.parseInt(substring);     // Convert to integer
            int newIdIndex = i + 1;                  // Increment the ID number
            return String.format("U%03d", newIdIndex); // Format as 'U001', 'U002', etc.
        }
        return "U001";  // Return the first ID if no records exist
    }
    @Override
    public  boolean add(User userDTO) throws SQLException {
        /*return CrudUtil.execute("INSERT INTO user (User_Id, UserName, password) VALUES (?, ?, ?)",
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword()
        );*/

       return SQLUtil.execute("INSERT INTO user (User_Id, UserName, password) VALUES (?, ?, ?)",
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword());

    }
    @Override
    public boolean delete(String Id) throws SQLException {
       /* return CrudUtil.execute("delete from user where User_Id=?", Id);*/
       return SQLUtil.execute("DELETE FROM user WHERE User_Id = ?", Id);
    }
    @Override
    public boolean update(User userDTO) throws SQLException {
        /*return CrudUtil.execute(
                "UPDATE user SET User_Id = ?, Password = ? WHERE User_Id = ?",
                userDTO.getUserId(),
                userDTO.getPassword(),
                userDTO.getUserId() // We use User_Id to identify the record to update
        );*/

       return SQLUtil.execute("UPDATE user SET User_Id = ?, Password = ? WHERE User_Id = ?",
                userDTO.getUserId(),
                userDTO.getPassword(),
                userDTO.getUserId());
    }

}
