package lk.ijse.gdse.demo.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.CustomerBO;
import lk.ijse.gdse.demo.bo.custom.InsuranceBO;
import lk.ijse.gdse.demo.bo.custom.MedicationBO;
import lk.ijse.gdse.demo.bo.custom.OrderBO;
import lk.ijse.gdse.demo.dao.custom.Impl.*;
import lk.ijse.gdse.demo.dao.custom.*;
import lk.ijse.gdse.demo.db.DBConnection;
import lk.ijse.gdse.demo.dto.*;
import lk.ijse.gdse.demo.dto.tm.CartTM;
import lk.ijse.gdse.demo.entity.Payment;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderManageFormController {

   //private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    //private InsuranceDAO insuranceDAO = (InsuranceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INSURANCE);

    InsuranceBO insuranceBO = (InsuranceBO) BOFactory.getInstance().getBO(BOFactory.BOType.INSURANCE);

    //private MedicationDAO medicationDAO = (MedicationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MEDICATION);

    MedicationBO medicationBO = (MedicationBO) BOFactory.getInstance().getBO(BOFactory.BOType.MEDICATION);

    //private OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);

    OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnEmail;

    @FXML
    private TableColumn<CartTM, Button> colAction;

    @FXML
    private TableColumn<CartTM, Integer> colCartQty;

    @FXML
    private TableColumn<CartTM, String> colMedication_Id;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private TableColumn<CartTM, Double> colUnitPrice;

    @FXML
    private ComboBox<String> comBoxMedication_Id;


    @FXML
    private AnchorPane contentAnchorPane;


    @FXML
    private ComboBox<String> comboxInsuranceCompanyName;

    @FXML
    private Label lblTotaFinalAmount;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDelivery_Date;

    @FXML
    private TextField txtMedication_Name;

    @FXML
    private TextField txtOrder_Date;

    @FXML
    private TextField txtOrder_Id;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private Label lblTotal;

    OrderDetailsDAOImpl orderDetailsDAO = new OrderDetailsDAOImpl();

    @FXML
    private TextField txtPhone_NumberCustomer;

    @FXML
    private TextField txtCustomer_Ids;


    @FXML
    private CheckBox checkCard;

    @FXML
    private TextField txtPayemnt;

    @FXML
    private Label lblAfterInsuranceAmount;

    @FXML
    private TextField txtDiscount;


    @FXML
    private CheckBox checkCash;

    @FXML
    private Label lblDelivery;

    @FXML
    private TextField lblDeliveryLocation;

    @FXML
    private Button btnPayNow;

    @FXML
    private CheckBox btnInsurance;


    @FXML
    private TextField txtInsurance_Id;

    @FXML
    private Button btnGenarateReport;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<CartTM> tblCart;

    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @FXML
    void onActionCustomerId(ActionEvent event) {
        String customerId = txtCustomer_Ids.getText();

        try {
            String customerName = customerDAOImpl.getCustomerNameByCustomerId(customerId);
            String customerPhone = customerDAOImpl.getCustomerPhoneByCustomerId(customerId);

            if (customerName != null && customerPhone != null) {
                txtCustomerName.setText(customerName);
                txtPhone_NumberCustomer.setText(customerPhone);
            } else {
                txtCustomerName.setText("Customer not found");
                txtPhone_NumberCustomer.setText("");
                Alert alert = new Alert(Alert.AlertType.WARNING, "No customer found for this Customer ID.");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            txtCustomerName.setText("Error retrieving customer");
            txtPhone_NumberCustomer.setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the customer.");
            alert.show();
        }
    }




    @FXML
    void onActionCustomer_phoneNo(ActionEvent event) {
        String phoneNo = txtPhone_NumberCustomer.getText();
        try {
            String customerName = customerBO.getCustomerNameByPhoneNo(phoneNo);
            String customerId = customerBO.getCustomerIdByPhoneNo(phoneNo);

            if (customerName != null && customerId != null) {
                txtCustomerName.setText(customerName);
                txtCustomer_Ids.setText(customerId);
            } else {
                txtCustomer_Ids.setText(customerDAOImpl.getNextCustomer_Id());
                Alert alert = new Alert(Alert.AlertType.WARNING, "No customer found for this phone number.Please Enter Name!!");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            txtCustomerName.setText("Error retrieving customer");
            txtCustomer_Ids.setText(""); // Clear the customer ID field in case of error
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while searching for the customer.");
            alert.show();
        }
    }

    private final ObservableList<CartTM> orderDetailsDTOS = FXCollections.observableArrayList();

    private OrderDAOImpl orderDAOImpl = new OrderDAOImpl();
    private MedicationDAOImpl medicationDAOImpl = new MedicationDAOImpl();
    private CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();

//    public void initialize() {
//        setCellValues();
//        try {
//            refreshPage();
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR, "Failed to load data!").show();
//        }
//    }

    private void setCellValues() {
        colMedication_Id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMedication_Id()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
        colUnitPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUnit_price()));
        colCartQty.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCart_qty()));
        colTotal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTotal()));
        colAction.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRemoveBtn()));
        tblCart.setItems(orderDetailsDTOS);
    }

    private void refreshPage() throws SQLException {
        txtOrder_Id.setText(orderBO.generateNewId());
        txtOrder_Date.setText(LocalDate.now().toString());
        loadMedicationIds();
        clearFields();
        tblCart.refresh();
    }

    private void loadMedicationIds() throws SQLException {
        ArrayList<String> mediId = medicationBO.getAllMedicationIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(mediId);

        comBoxMedication_Id.setItems(observableList);
    }

    private void clearFields() {
//        comBoxCustomer_Id.getSelectionModel().clearSelection();
        comBoxMedication_Id.getSelectionModel().clearSelection();
        txtCustomerName.clear();
        txtMedication_Name.clear();
        txtPrice.clear();
        txtQty.clear();
        txtQtyOnHand.clear();
        txtDelivery_Date.clear();
    }

    @FXML
    void onActionAddToCart(ActionEvent event) {
        String selectedMedicationId = comBoxMedication_Id.getValue();
        if (selectedMedicationId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a medication!").show();
            return;
        }

        String cartQtyString = txtQty.getText();
        if (!cartQtyString.matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity!").show();
            return;
        }

        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        if (cartQty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Not enough stock available!").show();
            return;
        }

        double unitPrice = Double.parseDouble(txtPrice.getText());
        double total = cartQty * unitPrice;

        String itemName = txtMedication_Name.getText();

        for (CartTM orderDetailsDTO : orderDetailsDTOS) {

            if (orderDetailsDTO.getMedication_Id().equals(selectedMedicationId)) {
                int newQty = orderDetailsDTO.getCart_qty() + cartQty;
                orderDetailsDTO.setCart_qty(newQty);
                orderDetailsDTO.setTotal(newQty * unitPrice);

                tblCart.refresh();
                return;
            }
        }



        Button removeBtn = new Button("Remove");
        CartTM newOrderDetailsDTO = new CartTM(selectedMedicationId, itemName, cartQty, unitPrice, total, removeBtn);

        removeBtn.setOnAction(e -> {
            orderDetailsDTOS.remove(newOrderDetailsDTO);
            tblCart.refresh();
        });


        orderDetailsDTOS.add(newOrderDetailsDTO);
        calculateTotal(total);

    }

    @FXML
    void onActionchechBoxCustomer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Information Required");
        alert.setHeaderText(null); // No header text
        alert.setContentText("Please enter Customer Name and Address.");
        alert.showAndWait(); // Display the alert
    }

    @FXML
    void onActionGenerateReport(ActionEvent event) {

    }


    private PaymentCreateController paymentCreateController;

    public void setPaymentCreateController(PaymentCreateController paymentCreateController) {
        this.paymentCreateController = paymentCreateController;
    }

    public double calculateTotal(double total) {
        int netTotal = 0;

        // Calculate total from table
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }

        // Fetch discount from the InsuranceModel (assuming it's a global variable or method)
        double discount = getDiscountFromCompany();  // Method to fetch discount, can be fetched once per company

        // Apply discount if available (assuming discount is in percentage)
        double finalTotal = netTotal - (netTotal * discount / 100);

        // Update the local label
        lblTotal.setText(String.format("%.2f", finalTotal));


        // Update lblAmount in PaymentCreateController if the reference is set
        if (paymentCreateController != null) {
            paymentCreateController.setTotalAmount(finalTotal);
        }

        return finalTotal; // Return the final discounted total
    }

//    public double getTotal() {
//        // Call calculateTotal to get the net total
//        return calculateTotal(0);
//    }


    @FXML
    void onActioncomBoxMedicationId(ActionEvent event) throws SQLException {
        // Get selected Medication ID from ComboBox
        String selectedMedicationId = comBoxMedication_Id.getValue();
//        System.out.println(selectedMedicationId);


        if (selectedMedicationId != null) {

            MedicationDTO medicationDTO = medicationBO.findById(selectedMedicationId);


            if (medicationDTO != null) {

                txtMedication_Name.setText(medicationDTO.getName()); // Medication Name
                txtQtyOnHand.setText(String.valueOf(medicationDTO.getStock_Level())); // Stock Level
                txtPrice.setText(String.valueOf(medicationDTO.getPrice())); // Price
            }
        }
    }

//    @FXML
//    void onActionPlaceOrder(ActionEvent event) throws SQLException {
//        // Check if cart is empty
//        if (tblCart.getItems().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
//            return;
//        }
//
//        // Retrieve order ID, date, and customer from UI fields
//        String orderId = txtOrder_Id.getText();
//
//
//        String dateString = txtOrder_Date.getText();
//        Date dateOfOrder = null;
//        Date deliveryDate = null;
//
//        try {
//            // Validate the order date format first
//            if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd").show();
//                return;
//            }
//            dateOfOrder = Date.valueOf(dateString);  // Will throw an exception if the format is incorrect
//
//            // Validate the delivery date format
//            String deliveryDateString = txtDelivery_Date.getText();
//            if (!deliveryDateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                new Alert(Alert.AlertType.ERROR, "Invalid delivery date format! Use yyyy-MM-dd").show();
//                return;
//            }
//            deliveryDate = Date.valueOf(deliveryDateString);  // Will throw an exception if the format is incorrect
//
//        } catch (IllegalArgumentException e) {
//            new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd").show();
//            return;
//        }
//        String customerName = txtCustomerName.getText();
//        String phoneNo = txtPhone_NumberCustomer.getText(); // Assuming you have a field for phone number
//
//        // Check if the required fields are empty
//        if (customerName.isEmpty() || phoneNo.isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Please enter Customer Name, Address, and Phone Number.").show();
//            return;
//        }
//
////        String deliveryDate = txtDelivery_Date.getText();
////        if (deliveryDate.isEmpty()) {
////            deliveryDate =orderModel.saveOrder();
////        }
//
//        // Retrieve the customer ID if it's provided, else generate a new one
//        String customerId = txtCustomer_Ids.getText();
//        if (customerId.isEmpty()) {
//            customerId = CustomerModel.generateCustomerId();  // Generate a new Customer ID
//        }
//
//        // Create a CustomerDTO object to hold the customer data
//        CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, customerName, null);
//
//        // Begin database transaction
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false); // Disable auto-commit for transaction control
//
//            // Check if the customer already exists
//            if (!CustomerModel.isCustomerIdExists(customerId)) {
//                // Save the customer data to the database if not already exists
//                boolean isSaved = CustomerModel.addCustomer(customerDTO);
//
//                if (!isSaved) {
//                    new Alert(Alert.AlertType.ERROR, "Failed to save customer!").show();
//                    connection.rollback(); // Rollback transaction if customer save fails
//                    return;
//                }
//            }
//
//
////            // Save the order information
////            OrderDTO orderDTO = new OrderDTO(
////                    orderId,               // Order ID
////                    customerId,            // Customer ID
////                    dateOfOrder,           // Order Date
////                    deliveryDate, // Delivery Date (can be adjusted)
////                    itemName,                   // Placeholder for Medication Name (to be populated later)// Placeholder for Medication ID (to be populated later)
////                    new ArrayList<>()      // Placeholder for order details, will be populated below
////            );
//
//
//            // Save the order information
//            OrderDTO orderDTO = new OrderDTO(
//                    orderId,               // Order ID
//                    customerId,            // Customer ID
//                    deliveryDate,           // Order Date
//                    new Date(System.currentTimeMillis()), // Delivery Date (can be adjusted)
//                    "",                    // Placeholder for Medication Name (to be populated later)
//                    "",                    // Placeholder for Medication ID (to be populated later)
//                    new ArrayList<>()       // Placeholder for order details, will be populated below
//            );
//
//            // Loop through the cart items to collect data for each item and add to order details array
//            ArrayList<OderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
//            for (CartTM cartTM : tblCart.getItems()) {  // Assuming tblCart holds CartTM objects
//                OderDetailsDTO orderDetailsDTO = new OderDetailsDTO(
//                        orderId,                    // Order ID
//                        cartTM.getMedication_Id(),   // Medication ID from the cart item
//                        cartTM.getCart_qty(),        // Quantity from the cart item
//                        cartTM.getUnit_price()    // Unit price from the cart item
//                        // Medication Name from the cart item
//                );
//                orderDetailsDTOS.add(orderDetailsDTO);
//            }
//
//            orderDTO.setOderDetailsDTO(orderDetailsDTOS);
//
//            // Insert the order into the database
//            boolean isOrderSaved = CrudUtil.execute(
//                    "insert into orders (Order_Id, Customer_Id, Order_Date) values (?, ?, ?)",
//                    orderDTO.getOrder_Id(),
//                    orderDTO.getCustomer_Id(),
//                    orderDTO.getOrder_Date()
//            );
//
//            if (isOrderSaved) {
//                // Save order details
//                boolean isOrderDetailListSaved = OrderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTO());
//                if (isOrderDetailListSaved) {
//                    connection.setAutoCommit(false);
//                    connection.commit(); // Commit transaction if both order and details are saved
//
//                    new Alert(Alert.AlertType.INFORMATION, "Order saved successfully..!").show();
//
//
//                    // Optionally, reset the form or refresh the page
//                    // refreshPage(); // Implement this method if needed
//                } else {
//                    connection.setAutoCommit(false);// Rollback if order details save failed
//                    connection.rollback();
//                    new Alert(Alert.AlertType.ERROR, "Failed to save order details.").show();
//                }
//            } else {
//               // Disable autocommit
//                connection.setAutoCommit(false);
//                connection.rollback(); // Rollback if order save failed
//                new Alert(Alert.AlertType.ERROR, "Failed to save order.").show();
//            }
//        } catch (SQLException e) {
//            connection.rollback();
//            // Rollback in case of any error
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
//        } finally {
//            connection.setAutoCommit(true); // Reset auto-commit after operation
//        }
//    }
/////////////////////////////////////


//    @FXML
//    void onActionPlaceOrder(ActionEvent event) throws SQLException {
//        // Check if cart is empty
//        if (tblCart.getItems().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
//            return;
//        }
//
//        // Retrieve order ID, date, and customer from UI fields
//        String orderId = txtOrder_Id.getText();
//        String dateString = txtOrder_Date.getText();
//        String medicationName = txtMedication_Name.getText();
//        String Medication_Id = comBoxMedication_Id.getValue();
//        Date dateOfOrder = null;
//        Date deliveryDate = null;
//
//        try {
//            // Validate the order date format
//            if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd").show();
//                return;
//            }
//            dateOfOrder = Date.valueOf(dateString);  // Will throw an exception if the format is incorrect
//
//            // Validate the delivery date format
//            String deliveryDateString = txtDelivery_Date.getText();
//            if (!deliveryDateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
//                new Alert(Alert.AlertType.ERROR, "Invalid delivery date format! Use yyyy-MM-dd").show();
//                return;
//            }
//            deliveryDate = Date.valueOf(deliveryDateString);  // Will throw an exception if the format is incorrect
//
//        } catch (IllegalArgumentException e) {
//            new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd").show();
//            return;
//        }
//
//        // Check if the required fields are empty
//        String customerName = txtCustomerName.getText();
//        String phoneNo = txtPhone_NumberCustomer.getText();
//        if (customerName.isEmpty() || phoneNo.isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Please enter Customer Name and Phone Number.").show();
//            return;
//        }
//
//        // Retrieve the customer ID if it's provided, else generate a new one
//        String customerId = txtCustomer_Ids.getText();
//        if (customerId.isEmpty()) {
//            customerId = CustomerModel.generateCustomerId();  // Generate a new Customer ID
//        }
//
//        // Create a CustomerDTO object to hold the customer data
//        CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, customerName, null);
//
//        // Begin database transaction
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(true); // Disable auto-commit for transaction control
//
//            // Check if the customer already exists, if not save the customer
//            if (!CustomerModel.isCustomerIdExists(customerId)) {
//                boolean isSaved = CustomerModel.addCustomer(customerDTO, connection); // Pass connection here
//                if (!isSaved) {
//                    new Alert(Alert.AlertType.ERROR, "Failed to save customer!").show();
//                    connection.rollback(); // Rollback transaction if customer save fails
//                    return;
//                }
//            }
//
//            // Check if all medications in the cart exist in the database
//            for (CartTM cartTM : tblCart.getItems()) {
//                if (!MedicationModel.isMedicationExists(cartTM.getMedication_Id())) {
//                    new Alert(Alert.AlertType.ERROR, "Medication ID " + cartTM.getMedication_Id() + " does not exist!").show();
//                    connection.rollback();
//                    return;
//                }
//            }
//
//            // Create OrderDTO
//            OrderDTO orderDTO = new OrderDTO(
//                    orderId,               // Order ID
//                    customerId,            // Customer ID
//                    dateOfOrder,           // Order Date
//                    deliveryDate,          // Delivery Date
//                    medicationName,                    // Placeholder for Medication Name
//                    Medication_Id,                    // Placeholder for Medication ID
//                    new ArrayList<>()
//            );
//
//            // Loop through the cart items to collect data for each item and add to order details
//            ArrayList<OderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
//            for (CartTM cartTM : tblCart.getItems()) {
//                OderDetailsDTO orderDetailsDTO = new OderDetailsDTO(
//                        orderId,                    // Order ID
//                        cartTM.getMedication_Id(),   // Medication ID
//                        cartTM.getCart_qty(),        // Quantity
//                        cartTM.getUnit_price()       // Unit price
//                );
//                orderDetailsDTOS.add(orderDetailsDTO);
//            }
//
//            orderDTO.setOderDetailsDTO(orderDetailsDTOS);
//
//            // Insert the order into the database
//            boolean isOrderSaved = OrderModel.saveOrder(orderDTO, connection); // Use transaction
//
//            if (isOrderSaved) {
//                // Save order details
//                boolean isOrderDetailListSaved = OrderDetailsModel.saveOrderDetailsList(orderDTO.getOrderDetailsDTO(), connection);
//                if (isOrderDetailListSaved) {
//                    connection.setAutoCommit(false);
//
//
//                    new Alert(Alert.AlertType.INFORMATION, "Order saved successfully..!").show();
//                } else {
//                    connection.setAutoCommit(true);
//                    //connection.rollback(); // Rollback if order details save failed
//                    new Alert(Alert.AlertType.ERROR, "Failed to save order details.").show();
//                }
//            } else {
//                connection.rollback(); // Rollback if order save failed
//                new Alert(Alert.AlertType.ERROR, "Failed to save order.").show();
//            }
//
//        } catch (SQLException e) {
////            connection.rollback();  // Rollback in case of any error
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
//        } finally {
//            connection.setAutoCommit(true); // Reset auto-commit after operation
//        }
//    }
////////These is the correct code

//Still Now Running these code!!!!!
@FXML
void onActionPlaceOrder(ActionEvent event) throws SQLException {
    // Check if cart is empty
    // Check if cart is empty
    if (tblCart.getItems().isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
        return;
    }

    String orderId = txtOrder_Id.getText();
    String dateString = txtOrder_Date.getText();
    String medicationName = txtMedication_Name.getText();
    String medicationId = comBoxMedication_Id.getValue();
    Date dateOfOrder = null;
    Date deliveryDate = null;

    try {
        if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd").show();
            return;
        }
        dateOfOrder = Date.valueOf(dateString);
        String deliveryDateString = txtDelivery_Date.getText();
        if (!deliveryDateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            new Alert(Alert.AlertType.ERROR, "Invalid delivery date format! Use yyyy-MM-dd").show();
            return;
        }
        deliveryDate = Date.valueOf(deliveryDateString);  // Will throw an exception if the format is incorrect

    } catch (IllegalArgumentException e) {
        new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd").show();
        return;
    }

    // Check if the required fields are empty
    String customerName = txtCustomerName.getText();
    String phoneNo = txtPhone_NumberCustomer.getText();
    if (customerName.isEmpty() || phoneNo.isEmpty()) {
        new Alert(Alert.AlertType.ERROR, "Please enter Customer Name and Phone Number.").show();
        return;
    }

    String customerId = txtCustomer_Ids.getText();
    if (customerId.isEmpty()) {
        customerId = customerBO.generateNewId();
    }
    CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, customerName, null);

    Connection connection = DBConnection.getInstance().getConnection();
    try {
        connection.setAutoCommit(false);

        if (!customerDAOImpl.isCustomerIdExists(customerId)) {
            boolean isSaved = customerBO.add(customerDTO);
            if (!isSaved) {
                new Alert(Alert.AlertType.ERROR, "Failed to save customer!").show();
                connection.rollback();
                return;
            }
        }

        for (CartTM cartTM : tblCart.getItems()) {
            if (!medicationBO.isMedicationExists(cartTM.getMedication_Id())) {
                new Alert(Alert.AlertType.ERROR, "Medication ID " + cartTM.getMedication_Id() + " does not exist!").show();
                connection.rollback();
                return;
            }

            int stockLevel = medicationBO.getStockLevel(cartTM.getMedication_Id());
            if (stockLevel < cartTM.getCart_qty()) {
                new Alert(Alert.AlertType.ERROR, "Insufficient stock for Medication ID " + cartTM.getMedication_Id()).show();
                connection.rollback();
                return;
            }
        }

        // Create OrderDTO
        OrderDTO orderDTO = new OrderDTO(
                orderId,               // Order ID
                customerId,            // Customer ID
                dateOfOrder,           // Order Date
                deliveryDate,          // Delivery Date
                medicationName,        // Placeholder for Medication Name
                medicationId,          // Placeholder for Medication ID
                new ArrayList<>()
        );

        // Loop through the cart items to collect data for each item and add to order details
        ArrayList<OderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
        for (CartTM cartTM : tblCart.getItems()) {
            OderDetailsDTO orderDetailsDTO = new OderDetailsDTO(
                    orderId,                    // Order ID
                    cartTM.getMedication_Id(),   // Medication ID
                    cartTM.getCart_qty(),        // Quantity
                    cartTM.getUnit_price()       // Unit price
            );
            orderDetailsDTOS.add(orderDetailsDTO);
        }

        orderDTO.setOderDetailsDTO(orderDetailsDTOS);

        // Insert the order into the database
        boolean isOrderSaved = orderBO.save(orderDTO); // Use transaction

        if (isOrderSaved) {
            // Save order details
            System.out.println("hihihihihihih");
            boolean isOrderDetailListSaved = orderBO.saveOrderDetails(orderDTO.getOrderDetailsDTO());
            if (isOrderDetailListSaved) {
                // Update stock levels for each medication in the cart
                for (CartTM cartTM : tblCart.getItems()) {
                    int newStockLevel = medicationBO.getStockLevel(cartTM.getMedication_Id()) - cartTM.getCart_qty();
                    boolean isStockUpdated = medicationBO.updateStockLevel(cartTM.getMedication_Id(), newStockLevel, connection);
                    if (!isStockUpdated) {
                        new Alert(Alert.AlertType.ERROR, "Failed to update stock level for Medication ID " + cartTM.getMedication_Id()).show();
                        connection.rollback();
                        return;
                    }
                }

//                    connection.commit(); // Commit the transaction
                new Alert(Alert.AlertType.INFORMATION, "Order saved successfully..!").show();
            } else {
                connection.rollback(); // Rollback if order details save failed
                new Alert(Alert.AlertType.ERROR, "Failed to save order details.").show();
            }
        } else {
            connection.rollback(); // Rollback if order save failed
            new Alert(Alert.AlertType.ERROR, "Failed to save order.").show();
        }

    } catch (SQLException e) {
        System.out.println("number 0202");

        connection.rollback();  // Rollback in case of any error
        new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
    } finally {
        try {
            connection.setAutoCommit(true); // Reset auto-commit to true
        } catch (SQLException e) {
           // Handle if any exception occurs while setting autocommit to true
        }
    }
}

////Still Now running these code!!!



//    @FXML
//    void onActionPlaceOrder(ActionEvent event) {
//        if (tblCart.getItems().isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "Please add items to the cart!").show();
//            return;
//        }
//
//        // Get the values from the fields
//        String orderId = txtOrder_Id.getText().trim();
//        String dateString = txtOrder_Date.getText().trim();
//        String deliveryDateString = txtDelivery_Date.getText().trim();
//        String customerId = txtCustomer_Ids.getText().trim();
//        String customerName = txtCustomerName.getText().trim();
//        String phoneNo = txtPhone_NumberCustomer.getText().trim();
//
//        // Validate input fields
//        if (orderId.isEmpty() || dateString.isEmpty() || deliveryDateString.isEmpty() ||
//                customerId.isEmpty() || customerName.isEmpty() || phoneNo.isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "All fields must be filled out.").show();
//            return;
//        }
//
//        // Parse dates
//        Date orderDate, deliveryDate;
//        try {
//            orderDate = Date.valueOf(dateString);
//            deliveryDate = Date.valueOf(deliveryDateString);
//        } catch (IllegalArgumentException e) {
//            new Alert(Alert.AlertType.ERROR, "Invalid date format! Use yyyy-MM-dd.").show();
//            return;
//        }
//
//        Connection connection = null;
//        try {
//            // Get the database connection and start transaction
//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false); // Start the transaction
//
//            // Check if customer exists or add a new customer
//            CustomerDTO customerDTO = new CustomerDTO(customerId, phoneNo, customerName, null);
//            boolean customerExists = CustomerModel.isCustomerIdExists(customerId);
//
//            if (!customerExists) {
//                if (!CustomerModel.addCustomer(customerDTO)) {
//                    connection.rollback(); // Rollback on failure
//                    new Alert(Alert.AlertType.ERROR, "Failed to save customer data!").show();
//                    return;
//                }
//            }
//
//            // Create order details from the cart
//            ArrayList<OderDetailsDTO> orderDetailsList = new ArrayList<>();
//            for (CartTM cartTM : tblCart.getItems()) {
//                OderDetailsDTO orderDetailsDTO = new OderDetailsDTO(
//                        orderId,
//                        cartTM.getMedication_Id(),
//                        cartTM.getCart_qty(),
//                        cartTM.getUnit_price()
//                );
//                orderDetailsList.add(orderDetailsDTO);
//            }
//
//            // Create the order DTO
//            OrderDTO orderDTO = new OrderDTO(
//                    orderId,
//                    customerId,
//                    deliveryDate,
//                    orderDate,
//                    null,       // Payment ID is not used here
//                    null,       // Medication ID is optional
//                    orderDetailsList
//            );
//
//            // Save the order
//            boolean isOrderSaved = OrderModel.saveOrder(orderDTO);
//            if (!isOrderSaved) {
//                connection.rollback(); // Rollback on failure
//                new Alert(Alert.AlertType.ERROR, "Failed to save order.").show();
//                return;
//            }
//
//            // Save order details
//            boolean areOrderDetailsSaved = OrderDetailsModel.saveOrderDetailsList(orderDetailsList);
//            if (!areOrderDetailsSaved) {
//                connection.rollback(); // Rollback on failure
//                new Alert(Alert.AlertType.ERROR, "Failed to save order details.").show();
//                return;
//            }
//
//            // Commit the transaction
//            connection.commit();
//            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
//
//            clearFields(); // Reset form (ensure this method is implemented)
//
//        } catch (SQLException e) {
//            // Handle SQL exceptions, rollback if needed
//            if (connection != null) {
//                try {
//                    connection.rollback(); // Rollback transaction on error
//                } catch (SQLException rollbackEx) {
//                    rollbackEx.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage() +
//                    "\nSQLState: " + e.getSQLState() +
//                    "\nErrorCode: " + e.getErrorCode() +
//                    "\nMessage: " + e.getMessage()).show(); // Show detailed error message
//        } catch (Exception e) {
//            // Catch any non-SQL exceptions
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true); // Restore auto-commit mode
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//


    @FXML
    void onActionReset(ActionEvent event) throws SQLException {
        clearFields();
        orderDetailsDTOS.clear();
        tblCart.refresh();
        txtOrder_Id.setText(orderBO.generateNewId());
        txtPhone_NumberCustomer.clear();
        txtPayemnt.clear();

    }

    @FXML
    void onActionDashBoard(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
    }

    public void navigateToForm(String fxmlPath) {
        try {
            // Clear existing children
            contentAnchorPane.getChildren().clear();

            // Load the FXML file dynamically
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Bind the loaded form to the parent container (contentAnchorPane)
            load.prefWidthProperty().bind(contentAnchorPane.widthProperty());
            load.prefHeightProperty().bind(contentAnchorPane.heightProperty());

            // Add the loaded form to the parent container
            contentAnchorPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PaymentDAOImpl paymentDAOImpl = new PaymentDAOImpl();
    private InsuranceDAOImpl insuranceDAOImpl = new InsuranceDAOImpl();

    public void initialize() {
        setCellValues();
        try {
            refreshPage();
            paymentDAOImpl = new PaymentDAOImpl();



            ArrayList<String> companyNames = insuranceBO.getCompanyNames();

            ObservableList<String> observableCompanyNames = FXCollections.observableArrayList(companyNames);


            comboxInsuranceCompanyName.setItems(observableCompanyNames);


            comboxInsuranceCompanyName.setOnAction(event -> {
                String selectedCompany = comboxInsuranceCompanyName.getValue();
                if (selectedCompany != null) {
                    loadDiscountForCompany(selectedCompany);
                }
            });

            String nextPayment_Id = paymentDAO.generateNewId();
            txtPayemnt.setText(nextPayment_Id);

        } catch (SQLException e) {
            // Show error message if an exception occurs
            new Alert(Alert.AlertType.ERROR, "Failed to load data!").show();
            e.printStackTrace();
        }
    }


    private void loadDiscountForCompany(String companyName) {
        try {
            // Fetch discount from the database
            String discount = insuranceBO.getDiscountByCompanyName(companyName);

            // Display the discount in the TextField
            txtDiscount.setText(discount);  // Assuming txtDiscount is a TextField
        } catch (SQLException e) {
            // Handle SQL exceptions
            new Alert(Alert.AlertType.ERROR, "Failed to load discount: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    private double getDiscountFromCompany() {
        // Retrieve selected company name
        String companyName = getSelectedCompanyName();

        // Initialize discount value
        double discount = 0.0;

        try {
            // Fetch the discount from the InsuranceModel
            String discountStr = insuranceBO.getDiscountByCompanyName(companyName);

            try {
                discount = Double.parseDouble(discountStr);// Assuming the discount is returned as a string
                lblAfterInsuranceAmount.setText(String.valueOf(discount));
            } catch (NumberFormatException e) {
                // Return 0 if parsing fails
                System.out.println("Item selected");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            new Alert(Alert.AlertType.ERROR, "Failed to load discount: " + e.getMessage()).show();
            e.printStackTrace();
        }

        return discount;
    }

    private void updateAmountAfterInsurance() {
        try {
            // Get the total amount from lblTotal (convert it from String to double)
            double totalAmount = Double.parseDouble(lblTotal.getText());

            // Get the discount value from txtDiscount (assuming it's a percentage)
            double discountPercentage = Double.parseDouble(txtDiscount.getText());

            // Calculate the discount
            double discountAmount = totalAmount * (discountPercentage / 100);

            // Calculate the final amount after applying the discount
            double finalAmount = totalAmount - discountAmount;

            // Update the lblAfterInsuranceAmount with the final amount
            lblAfterInsuranceAmount.setText(String.valueOf(finalAmount));
        } catch (NumberFormatException e) {
            // Handle possible errors in parsing numbers
            new Alert(Alert.AlertType.ERROR, "Invalid number format: " + e.getMessage()).show();
        }
    }


    private String getSelectedCompanyName() {

        if (comboxInsuranceCompanyName != null) {
            return comboxInsuranceCompanyName.getSelectionModel().getSelectedItem();
        } else {
            return "";  // Return empty string if no company is selected
        }
    }


    @FXML
    void onActioncmbInsurance(ActionEvent event) throws SQLException {
        if (btnInsurance.isSelected()) {
            // Set next Insurance ID in txtInsurance_Id
            String nextInsuranceId = insuranceBO.generateNewId();
            txtInsurance_Id.setText(nextInsuranceId);

            // Get the total and discount values
            String totalText = lblTotal.getText();
            String discountText = txtDiscount.getText();

            // Validate total and discount fields
            if (totalText == null || totalText.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Total amount is empty!").show();
                return;
            }
            if (discountText == null || discountText.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Discount is empty!").show();
                return;
            }

            try {
                // Parse discount percentage and total
                if (discountText.endsWith("%")) {
                    discountText = discountText.substring(0, discountText.length() - 1); // Remove '%' character
                }

                double total = Double.parseDouble(totalText);
                double discountPercentage = Double.parseDouble(discountText);

                // Calculate the discounted amount
                double discountedAmount = (total * discountPercentage) / 100;
                System.out.println(discountedAmount);
                lblAfterInsuranceAmount.setText(String.format("%.2f", discountedAmount));


                onActioncmbInsuraceCompany(event);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid number format! Please check your inputs.").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Company Name and Discount!").show();
        }
    }


    @FXML
    void onActionPayNow(ActionEvent event) {
        try {

            String paymentId = txtPayemnt.getText();
            Date paymentDate = Date.valueOf(txtOrder_Date.getText());
            double amount = Double.parseDouble(lblTotaFinalAmount.getText());
            String paymentType = "";


            if (checkCash.isSelected()) {
                paymentType = "Cash";
            } else if (checkCard.isSelected()) {
                paymentType = "Card";
            } else if (btnInsurance.isSelected()) {
                paymentType = "Insurance";
            }

            String insuranceId = txtInsurance_Id.getText();
            if (insuranceId.isEmpty()) {
                insuranceId = null;
            }


            Payment paymentDTO = new Payment(paymentId, paymentDate, amount, paymentType, "Complete", insuranceId);



            PaymentDAOImpl paymentDAOImpl = new PaymentDAOImpl();
            paymentDAO.add(paymentDTO);


            new Alert(Alert.AlertType.INFORMATION, "Payment Successful!").show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error processing payment: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    private double getDiscountFromCompany(String companyName) throws SQLException {
        String discountStr = insuranceBO.getDiscountByCompanyName(companyName);

        try {
            return Double.parseDouble(discountStr);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @FXML
    void onActioncmbInsuraceCompany(ActionEvent event) {
        try {
            // Initialize the InsuranceModel and set the next Insurance ID
            insuranceDAOImpl = new InsuranceDAOImpl();
            String insuranceId =insuranceBO.generateNewId();
            txtInsurance_Id.setText(insuranceId);

            // Get the selected company name from the ComboBox
            String selectedCompanyName = (String) comboxInsuranceCompanyName.getValue();

            if (selectedCompanyName != null) {
                double discount = 0.0;

                // Apply discount based on the selected company name
                if (selectedCompanyName.equalsIgnoreCase("AIA")) {
                    discount = 5.0; // 5% discount for AIA
                } else if (selectedCompanyName.equalsIgnoreCase("OtherCompanyName")) {
                    discount = 10.0; // Example: 10% for another company
                }

                // Update the discount amount label
                lblAfterInsuranceAmount.setText(String.format("%.2f%%", discount));

                // Calculate the total price before discount
                double totalPriceBeforeDiscount = calculateTotal(0);
                System.out.println("Total Price Before Discount: " + totalPriceBeforeDiscount);

                // Validate the total price and discount values
                if (totalPriceBeforeDiscount > 0 && discount > 0) {
                    // Calculate the final amount after discount
                    double finalAmount = totalPriceBeforeDiscount - (totalPriceBeforeDiscount * (discount / 100));
                    System.out.println("Final Amount After Discount: " + finalAmount);

                    // Update the Total label with the final amount
                    lblTotal.setText(String.format("%.2f", finalAmount));
                    lblTotaFinalAmount.setText(lblTotal.getText());
                } else {
                    new Alert(Alert.AlertType.WARNING, "Total price or discount is invalid!").show();
                }
            } else {
                // Show a warning if no company is selected
                new Alert(Alert.AlertType.WARNING, "Please select a Company Name!").show();
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions
            new Alert(Alert.AlertType.ERROR, "Error while fetching Insurance ID: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (Exception e) {
            // Handle any unexpected exceptions
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }



    // Method to get the total price before applying the discount (this can vary based on your app)
    private double getTotalPriceBeforeDiscount() {
        // Replace with your actual method to get the total price before discount
        // For example, from a table or from user input
        return 0.0; // Example value; replace with actual calculation
    }


    @FXML
    void onActionCash(ActionEvent event) {
        // Check if the checkbox is selected
        if (checkCash.isSelected()) {
            // Assuming calculateTotal() returns the net total value
            double netTotal = calculateTotal(0);  // Get the calculated total

            // Set the calculated total in the label, formatted to 2 decimal places
            lblTotaFinalAmount.setText(String.format("%.2f", netTotal));
        }
    }

    @FXML
    void onActionCard(ActionEvent event) {
        if (checkCard.isSelected()) {
            // Get the net total using the calculateTotal method (no arguments if not required)
            double netTotal = calculateTotal(0);  // Call the method without any arguments if not needed

            // Ensure netTotal is not zero or negative before applying the discount
            if (netTotal > 0) {
                // Apply 2% discount
                double discountedTotal = netTotal - (netTotal * 0.02);  // 2% discount

                // Set the final discounted amount in lblTotalFinalAmount
                lblTotaFinalAmount.setText(String.format("%.2f", discountedTotal));
            } else {
                // Handle the case if the total is zero or invalid
                lblTotaFinalAmount.setText("0.00");  // or display an appropriate message
            }
        }
    }

    @FXML
    void onActioncheckDelivery(ActionEvent event) throws SQLException {
        try{

            DeliveryDAO deliveryDAO = new DeliveryDAOImpl();

            String Delivery_Id = deliveryDAO.generateNewId();
            String Order_Id =  txtOrder_Id.getText();
            Date Delidate = Date.valueOf(txtDelivery_Date.getText());
            String DeliLocation = lblDeliveryLocation.getText();
            String DeliPhoneNumber = txtPhone_NumberCustomer.getText();


            DeliveryDTO deliveryDTO = new DeliveryDTO(Delivery_Id,Delidate,Order_Id,DeliLocation,DeliPhoneNumber,null);

            DeliveryDAOImpl deliveryDAOImpl = new DeliveryDAOImpl();
            deliveryDAOImpl.insertDelivery(deliveryDTO);
            lblDelivery.setText("Now Ready to Delivery!!!!");
            new Alert(Alert.AlertType.INFORMATION, "Delivery Successful!").show();

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Error processing delivery: " + e.getMessage()).show();
        }
    }

    @FXML
    void onActionSendEmail(ActionEvent event) {
        String orderId = txtOrder_Id.getText();
        String customerId = txtCustomer_Ids.getText();
        String deliveryDate = txtDelivery_Date.getText();
        String deliveryLocation = lblDeliveryLocation.getText();
        String deliveryPhoneNumber = txtPhone_NumberCustomer.getText();
        String recipientEmail = "recipient@example.com"; // Replace with actual recipient email

        try {
            sendEmail(recipientEmail, orderId, customerId, deliveryDate, deliveryLocation, deliveryPhoneNumber);
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR, "Sending Email Failed").show();
            e.printStackTrace();
        }
    }

    private void sendEmail(String recipientEmail, String orderId, String customerId, String deliveryDate,
                           String deliveryLocation, String deliveryPhoneNumber) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String myEmail = "kssds1212@gmail.com ";
        String password = "zefj drbk xvpi ugrk";

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });
        Message message = prepareMessage(session, myEmail, recipientEmail, orderId, customerId, deliveryDate,
                deliveryLocation, deliveryPhoneNumber);
        Transport.send(message);
        new Alert(Alert.AlertType.INFORMATION, "Email Sent Successfully").show();
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String orderId,
                                   String customerId, String deliveryDate, String deliveryLocation,
                                   String deliveryPhoneNumber) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Order Details");

            String emailContent = String.format(
                    "Order ID: %s%nCustomer ID: %s%nDelivery Date: %s%nDelivery Location: %s%nCustomer Phone: %s",
                    orderId, customerId, deliveryDate, deliveryLocation, deliveryPhoneNumber);

            message.setText(emailContent);
            return message;
        } catch (Exception e) {
            Logger.getLogger(OrderManageFormController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }


    public void onActionGenarateReport(ActionEvent actionEvent) {
        try {
            // Load the JRXML file
            InputStream reportStream = getClass().getResourceAsStream("/report/Order_Report.jrxml");
            if (reportStream == null) {
                throw new JRException("Report file not found!");
            }

            // Compile the JRXML file
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Get database connection
            Connection connection = DBConnection.getInstance().getConnection();

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);

            // Display the report without save contributors
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate report: " + e.getMessage()).show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    public void onActionSelectCustomerDetailsByName(ActionEvent actionEvent) throws SQLException {
        String customerName = txtCustomerName.getText();
        String customerDetails = customerBO.getCustomerDetailsbyName(customerName); // Fetch combined details

        try {
            if (customerDetails != null && !customerDetails.isEmpty()) {
                String[] details = customerDetails.split(", ");

                if (details.length < 2) { // Prevents ArrayIndexOutOfBoundsException
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Customer details are incomplete or corrupted!");
                    alert.show();
                    return;
                }

                String customerId = details[0].split(": ")[1];  // Extract Customer ID
                String customerPhoneNum = details[1].split(": ")[1];  // Extract Phone Number

                txtCustomer_Ids.setText(customerId);
                txtPhone_NumberCustomer.setText(customerPhoneNum);
            } else {
                txtCustomer_Ids.setText(customerBO.generateNewId());
                Alert alert = new Alert(Alert.AlertType.WARNING, "No customer details found for this name. Please enter your details!");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error processing customer details. Please check database formatting.");
            alert.show();
        }

    }

}
