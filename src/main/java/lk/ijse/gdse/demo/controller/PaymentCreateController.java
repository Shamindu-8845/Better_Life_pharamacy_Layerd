package lk.ijse.gdse.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import lk.ijse.gdse.demo.dao.custom.Impl.InsuranceDAOImpl;
import lk.ijse.gdse.demo.dao.custom.Impl.PaymentDAOImpl;

import java.sql.SQLException;


public class PaymentCreateController {

    private OrderManageFormController orderManageFormController = new OrderManageFormController();


    @FXML
    private Button btnGenarateReport;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnPayHere;

    @FXML
    private Button btnSendEmail;

    @FXML
    private CheckBox checkBoxCard;

    @FXML
    private CheckBox checkBoxCash;

    @FXML
    private CheckBox checkBoxOther;

    @FXML
    private ComboBox<String> comBoxCompany_Name;

    @FXML
    private Label lblAmount_Order;

    @FXML
    private Label lblCustomer_Id;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtInsurance_Id;

    @FXML
    private TextField txtPayment;



    @FXML
    void onActionDashboard(ActionEvent event) {

    }

    @FXML
    void onActionGenarateReport(ActionEvent event) {

    }

    @FXML
    void onActionPayHere(ActionEvent event) {

    }

    @FXML
    void onActionSendEmail(ActionEvent event) {

    }

    private PaymentDAOImpl paymentDAOImpl = new PaymentDAOImpl();
    private InsuranceDAOImpl insuranceDAOImpl = new InsuranceDAOImpl();

    public void setTotalAmount(double total) {
        lblAmount_Order.setText(String.valueOf(total)); // Update the label with the provided total
    }

    public void initialize() throws SQLException {
//        paymentModel = new PaymentModel();
////        lblTotalAmount.setText(String.valueOf(orderManageFormController.calculateTotal()));  // Call method without arguments and show the result
////        double total = orderManageFormController.getTotal();  // Calls getTotal which internally calls calculateTotal
////        lblTotalAmount.setText(String.valueOf(total));
//
//
//
//        // Fetch company names
//        ArrayList<String> companyNames = InsuranceModel.getCompanyNames();
//
//        // Populate ComboBox with the fetched company names
//        comBoxCompany_Name.getItems().addAll(companyNames);
//
//        // Set action handler for the ComboBox to load discounts for selected company
//        comBoxCompany_Name.setOnAction(event -> {
//            String selectedCompany = comBoxCompany_Name.getValue();
//            if (selectedCompany != null) {
//                loadDiscountForCompany(selectedCompany); // Assuming you have a method to load discount details
//            }
//        });
//
//        // Try to fetch the next payment ID and set it in the text field
//        try {
//            String nextPayment_Id = paymentModel.getNextPayment_Id();
//            txtPayment.setText(nextPayment_Id);
//
//        } catch (SQLException e) {
//            // Show error message if fetching the payment ID fails
//            new Alert(Alert.AlertType.ERROR, "Failed to load the next Payment ID: " + e.getMessage()).show();
//        }
//

    }

    @FXML
    void onActioncmbCampany_Name(ActionEvent event) throws SQLException {

//        insuranceModel = new InsuranceModel();
//
//        String InsuranceId = insuranceModel.getNextInsurace_Id();
//        txtInsurance_Id.setText(InsuranceId);
//        // Get the selected company name from the ComboBox
//        String selectedCompany_Name = (String) comBoxCompany_Name.getValue(); // Assuming the ComboBox stores Strings
//        if (selectedCompany_Name != null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Company Name: " + selectedCompany_Name);
//            alert.show();
//        } else {
//            new Alert(Alert.AlertType.WARNING, "Please select a Company Name!").show();
//        }
    }


    public void onActionInsuraceCheck(ActionEvent actionEvent) {

//        if (checkBoxOther.isSelected()) {
//            try {
//                onActioncmbCampany_Name(actionEvent); // Pass the existing ActionEvent directly
//            } catch (SQLException e) {
//                e.printStackTrace();
//                new Alert(Alert.AlertType.ERROR, "Failed to fetch insurance details: " + e.getMessage()).show();
//            }
//        } else {
//            new Alert(Alert.AlertType.WARNING, "Please select a Company Name and Discount!").show();
//        }
    }

 // Method to load the discount for the selected company
//    private void loadDiscountForCompany(String companyName) {
//        try {
//            // Fetch discount from the database
//            String  discount = InsuranceModel.getDiscountByCompanyName(companyName);
//
//            // Display the discount in the TextField
//            txtDiscount.setText(String.valueOf(discount));
//        } catch (SQLException e) {
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Failed to load discount: " + e.getMessage()).show();
//        }
//
//
//
//    }


}
