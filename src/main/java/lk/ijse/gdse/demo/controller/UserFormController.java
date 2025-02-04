package lk.ijse.gdse.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import lk.ijse.gdse.demo.dao.DAOFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import lk.ijse.gdse.demo.dao.custom.UserDAO;
import lk.ijse.gdse.demo.util.CrudUtil;

public class UserFormController implements Initializable {

    @FXML
    private AnchorPane UserFormAnchorPane;

    @FXML
    private AnchorPane UserFormOptions;

    @FXML
    private Button btnCustomer, btnEmployee, btnBack, btnUser;

    @FXML
    private AnchorPane UserFormContent;

    @FXML
    private LineChart<String, Number> LineChart;


    @FXML
    private CategoryAxis XAxis;

    @FXML
    private NumberAxis YAxis;



    private UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XAxis.setLabel("Customer_Id");
        YAxis.setLabel("Number of Customers");
        loadLineChartData();
    }



    @FXML
    void onActionEmployee(ActionEvent event) {
        System.out.println("loading");
        navigateToForm("/view/UserFormEmployee.fxml");
        System.out.println("Loaded");
    }

    @FXML
    void onActionUser(ActionEvent event) {
        System.out.println("Loaded");
        navigateToForm("/view/UserFormUser.fxml");
    }

    @FXML
    void onActoinCustomer(ActionEvent event) {
        navigateToForm("/view/UserFormCustomer.fxml");
    }

    @FXML
    void onActionAdminForm(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    public void navigateToForm(String fxmlPath) {
        try {
            UserFormContent.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(UserFormContent.widthProperty());
            load.prefHeightProperty().bind(UserFormContent.heightProperty());

            UserFormContent.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
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
            ((Stage) UserFormAnchorPane.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLineChartData() {
        // Example: Fetching data from the database (counting customers per address)
        try {
            // Query to count customers based on their address
            String query = "SELECT Address, COUNT(*) as CustomerCount FROM customer GROUP BY Address ORDER BY Address";
            ResultSet resultSet = CrudUtil.execute(query);

            // Create a series to hold the chart data
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Customers by Address");

            // Loop through the result set and add data points to the series
            while (resultSet.next()) {
                String address = resultSet.getString("Address");
                int customerCount = resultSet.getInt("CustomerCount");
                series.getData().add(new XYChart.Data<>(address != null ? address : "No Address", customerCount));
            }

            // Clear previous data and add the new data series to the chart
            LineChart.getData().clear();
            LineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading chart data").show();
        }
    }
}
