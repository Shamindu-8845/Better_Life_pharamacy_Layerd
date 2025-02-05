package lk.ijse.gdse.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.*;
import lk.ijse.gdse.demo.util.CrudUtil;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.sql.SQLException;

public class AdminFormController {

    @FXML
    private AnchorPane AdminFormAnchorPane;


    @FXML
    private AnchorPane content;


    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnBill;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnDelivery;

    @FXML
    private Button btnInsurance;

    @FXML
    private Button btnInventory;

    @FXML
    private Button btnMedication;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnSuppliers;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblTotalEmployees;

    @FXML
    private Label lblTotalDeliveries;

    @FXML
    private Label lblCurrentTime;

    @FXML
    void onActionBillingInvoiceManage(ActionEvent event) {

    }

    @FXML
    void onActionHomeDeliveryManage(ActionEvent event) {
    navigateToForm("/view/DeliveryPersonForm.fxml");
    }

    @FXML
    void onActionInsuranaceManage(ActionEvent event) {
        navigateToForm("/view/InsuranceForm.fxml");
    }

    @FXML
    void onActionInventoryManage(ActionEvent event) {
        navigateToForm("/view/InventoryManageForm.fxml");
    }


    @FXML
    void onActionMedicationManage(ActionEvent event) {
        navigateToForm("/view/MedicationManageForm.fxml");
    }

    @FXML
    void onActionOrderManage(ActionEvent event) {
        navigateToForm("/view/OrderManageForm.fxml");
    }

    @FXML
    void onActionPaymentManage(ActionEvent event) {
        navigateToForm("/view/PaymentManageForm.fxml");
    }

    @FXML
    void onActionSupplierManage(ActionEvent event) {
        navigateToForm("/view/SuppliersManageForm.fxml");
    }

    @FXML
    private Label lblExpired;

    @FXML
    private Label lblTotalCustomer;

    @FXML
    private Label lblTotalMedicine;

    @FXML
    private Label lblTotalSuppliers;

    @FXML
    private Label lblTotalinvoice;

    @FXML
    private LineChart<String, Number> LineChart;


    @FXML
    private CategoryAxis XAxis;

    @FXML
    private NumberAxis YAxis;

    @FXML
    private TableColumn<?, ?> tblTotalSales;

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    MedicationBO medicationBO = (MedicationBO) BOFactory.getInstance().getBO(BOFactory.BOType.MEDICATION);

    DeliveryBO deliveryBO = (DeliveryBO) BOFactory.getInstance().getBO(BOFactory.BOType.DELIVERY);

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    OrderDetailsBO orderDetailsBO = (OrderDetailsBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERDETAILS);

    public void initialize() {
        XAxis.setLabel("Date");
        YAxis.setLabel("Number of orders");
        loadLineChartData();
        try {
            int totalEmployee = employeeBO.getEmployeeCount();
            lblTotalEmployees.setText(String.valueOf(totalEmployee));

            int totalCustomers = customerBO.getTotalCustomers();
            lblTotalCustomer.setText(String.valueOf(totalCustomers));


            lblExpired.setText(medicationBO.countExpiredMedications());


            int totalDelivery = deliveryBO.getDeliveryCount();
            lblTotalDeliveries.setText(String.valueOf(totalDelivery));


            int totalSuppliers = supplierBO.getTotalSuppliers();
            lblTotalSuppliers.setText(String.valueOf(totalSuppliers));


            int totalMedications = medicationBO.getTotalMedications();
            lblTotalMedicine.setText(String.valueOf(totalMedications));


            double totalSales = orderDetailsBO.getTotalPrice();
            tblTotalSales.setText(String.format("Total Sales: %.2f", totalSales));

        } catch (SQLException e) {
            e.printStackTrace();
            lblTotalSuppliers.setText("Error loading data");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    String currentTime = LocalTime.now().format(formatter);
                    lblCurrentTime.setText(currentTime);
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }


    private void loadLineChartData() {
        try {
            String query = "SELECT Order_Date, COUNT(*) as OrderCount FROM Orders GROUP BY Order_Date ORDER BY Order_Date";
            ResultSet resultSet = CrudUtil.execute(query);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Medication Orders");

            while (resultSet.next()) {
                String date = resultSet.getString("Order_Date");
                int orderCount = resultSet.getInt("OrderCount");
                series.getData().add(new XYChart.Data<>(date, orderCount));
            }

            LineChart.getData().clear();
            LineChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading chart data").show();
        }
    }


    @FXML
    void onActionUserForm(ActionEvent event) {
        navigateTo("/view/UserForm.fxml");
    }

    @FXML
    void onActionLoginPage(ActionEvent event) {
        navigateTo("/view/MainForm.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                ((Stage)AdminFormAnchorPane.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML at path: " + fxmlPath);
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }

    @FXML
    void onActionCustomerManageForm(ActionEvent event) {
        navigateToForm("/view/CustomerManageForm.fxml");
    }

    public void navigateToForm(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(content.widthProperty());
            load.prefHeightProperty().bind(content.heightProperty());

            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }


    public void onActionNewCustomer(ActionEvent actionEvent) {
        navigateToForm("/view/CustomerManageForm.fxml");
        
    }

    public void onActionNewMedicine(ActionEvent actionEvent) {
        navigateToForm("/view/MedicationManageForm.fxml");
    }

    public void onActionNewSupplier(ActionEvent actionEvent) {
        navigateToForm("/view/SuppliersManageForm.fxml");
    }
    @FXML
    void onActionPlaceOrder(ActionEvent event) {
        navigateToForm("/view/OrderManageForm.fxml");
    }

    @FXML
    void onActionDashBoard(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    @FXML
    void onActionDeliveryShow(ActionEvent event) {
        navigateToForm("/view/DeliveryPersonForm.fxml");
    }



}
