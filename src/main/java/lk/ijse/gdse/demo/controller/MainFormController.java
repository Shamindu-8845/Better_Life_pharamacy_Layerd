package lk.ijse.gdse.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainFormController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane LoginMainFormAnchorPane;

    @FXML
    private Button btnLogin;

    @FXML
    private CheckBox chkRememberMe;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code if needed
    }

    @FXML
    void onActionbtnLogin(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username and Password cannot be empty.");
            return;
        }

        // Validate user credentials
        UserService userService = new UserService();
        if (userService.validateUser(username, password)) {
//            showAlert("Success", "Login successful!");

            // Navigate to different forms based on username
            if (username.equals("Admin") || username.equals("admin")) {
                navigateTo("/view/AdminForm.fxml");
            } else if (username.equals("Pharmacist")|| username.equals("pharmacist")) {
                navigateTo("/view/PharmacistForm.fxml");
            } else if (username.equals("Customer")|| username.equals("customer")) {
                navigateTo("/view/CustomerForm.fxml");
            } else if (username.equals("Delivery Person")|| username.equals("delivery person")) {
                navigateTo("/view/HomeDeliverMangeForm.fxml");
            }
        } else {
            showAlert("Error", "Invalid username or password.");
        }

        // Handle the Remember Me functionality
        if (chkRememberMe.isSelected()) {
            System.out.println("Remember Me checked for: " + username);
        }
    }

    @FXML
    void chkonAction(ActionEvent event) {
        // Logic for handling checkbox action
        System.out.println("Remember Me checkbox state: " + chkRememberMe.isSelected());
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public class UserService {

        public boolean validateUser(String username, String password) {
            boolean isValidUser = false;

            // Establishing the connection
            try (Connection connection = DBConnection.getInstance().getConnection()) {
                // Prepare SQL query to check if username and password match
                String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password); // Hash the password in production

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            isValidUser = true; // User found
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }

            return isValidUser;
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            // Close the current window if needed
            ((Stage) LoginMainFormAnchorPane.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
