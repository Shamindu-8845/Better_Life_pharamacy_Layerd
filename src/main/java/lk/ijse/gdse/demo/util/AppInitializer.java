package lk.ijse.gdse.demo.util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.db.DBConnection;

import java.sql.Connection;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/AdminForm.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pharmacy System");
        stage.show();

        /*please use password and username to log into the system
        * username - admin
        * password - 1234
        * */


        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            if (connection != null) {
                System.out.println("Database Connected!");
            }
        } catch (Exception e) {
            System.out.println("Database Not Connected!" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
