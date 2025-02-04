package lk.ijse.gdse.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.PaymentBO;
import lk.ijse.gdse.demo.dto.PaymentDTO;
import lk.ijse.gdse.demo.dto.SuppliersDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.PaymentDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class PaymentManageFormController {
    private PaymentDAOImpl paymentDAOImpl;
    @FXML
    private Button btnAddPayment;

    @FXML
    private Button btnDeletePayment;

    @FXML
    private Button btnUpdatePayment;

    @FXML
    private Button btnDashBoard;

    @FXML
    private TableColumn<PaymentDTO, String> colAmount;

    @FXML
    private TableColumn<PaymentDTO, Date> colDate;

    @FXML
    private TableColumn<PaymentDTO, String> colInsurance_Id;

    @FXML
    private TableColumn<PaymentDTO, String> colPayment_Id;

    @FXML
    private TableColumn<PaymentDTO, String> colPayment_Type;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private TableView<PaymentDTO> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtInsuranace_Id;

    @FXML
    private TextField txtPayemnt_Id;

    @FXML
    private TextField txtPayment_Type;

    @FXML
    private TextField txtStatus;

    @FXML
    private AnchorPane content;

    @FXML
    private Button btnReset;

    private SuppliersDTO PaymentDTO;

   //private PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);

    public void initialize() {
        colPayment_Id.setCellValueFactory(new PropertyValueFactory<>("Payment_Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPayment_Type.setCellValueFactory(new PropertyValueFactory<>("Payment_Type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colInsurance_Id.setCellValueFactory(new PropertyValueFactory<>("Insurance_Id"));

        paymentDAOImpl = new PaymentDAOImpl();
        loadPaymentTable();
        tblPayment.setOnMouseClicked(this::onRowClick);

        try {
            String nextPayment_Id = paymentBO.generateNewId();
            txtPayemnt_Id.setText(nextPayment_Id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPaymentTable() {
        try {
            List<PaymentDTO> paymentList = paymentBO.getAll();
            ObservableList<PaymentDTO> observableList = FXCollections.observableArrayList(paymentList);
            tblPayment.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onRowClick(MouseEvent event) {
        PaymentDTO selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            txtPayemnt_Id.setText(selectedPayment.getPayment_Id());
            txtDate.setText(String.valueOf(selectedPayment.getDate()));
            txtAmount.setText(String.valueOf(selectedPayment.getAmount()));
            txtPayment_Type.setText(selectedPayment.getPayment_Type());
            txtStatus.setText(selectedPayment.getStatus());
            txtInsuranace_Id.setText(selectedPayment.getInsurance_Id());
        }
    }

    private boolean validatePaymentFields() {
        String paymentId = txtPayemnt_Id.getText();
        String date = txtDate.getText();
        String amount = txtAmount.getText();
        String paymentType = txtPayment_Type.getText();
        String status = txtStatus.getText();
        String insuranceId = txtInsuranace_Id.getText();

        // Patterns
        String idPattern = "^[A-Za-z0-9]+$";
        String datePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";
        String paymentTypePattern = "^[A-Za-z ]+$";
        String statusPattern = "^(Complete|Incomplete|Cancelled|Pending)$";
        String insuranceIdPattern = "^[A-Za-z0-9]+$";

        boolean isValid = true;

        if (!paymentId.matches(idPattern)) {
            txtPayemnt_Id.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtPayemnt_Id.setStyle(null);
        }

        if (!date.matches(datePattern)) {
            txtDate.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtDate.setStyle(null);
        }

        if (!amount.matches(amountPattern)) {
            txtAmount.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtAmount.setStyle(null);
        }

        if (!paymentType.matches(paymentTypePattern)) {
            txtPayment_Type.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtPayment_Type.setStyle(null);
        }

        if (!status.matches(statusPattern)) {
            txtStatus.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtStatus.setStyle(null);
        }

        if (!insuranceId.matches(insuranceIdPattern)) {
            txtInsuranace_Id.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            txtInsuranace_Id.setStyle(null);
        }

        if (!isValid) {
            new Alert(Alert.AlertType.WARNING, "Please correct the invalid input.").show();
        }

        return isValid;
    }

    private void clearFields() {
        txtPayemnt_Id.clear();
        txtDate.clear();
        txtAmount.clear();
        txtPayment_Type.clear();
        txtStatus.clear();
        txtInsuranace_Id.clear();
    }

    @FXML
    void onActionAddPayment(ActionEvent event) {
        if (validatePaymentFields()) {
            try {
                String payId = txtPayemnt_Id.getText();
                Double amount = Double.valueOf(txtAmount.getText());
                java.sql.Date date = java.sql.Date.valueOf(txtDate.getText());
                String status = txtStatus.getText();
                String paymentType = txtPayment_Type.getText();
                String insuranceId = txtInsuranace_Id.getText();

                PaymentDTO paymentDTO = new PaymentDTO(payId, date, amount, paymentType, status, null);

                boolean added = paymentBO.add(paymentDTO);
                if (added) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Added Successfully").show();
                    loadPaymentTable();
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Payment Add Failed").show();
            }
        }
    }

    @FXML
    void onActionDeletePayment(ActionEvent event) {
        PaymentDTO selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            try {
                boolean deleted = paymentBO.delete(selectedPayment.getPayment_Id());
                if (deleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Deleted Successfully").show();
                    loadPaymentTable();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Payment").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error Occurred While Deleting Payment").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Select a Payment to Delete").show();
        }
    }

    @FXML
    void onActionUpdatePayment(ActionEvent event) {
        if (validatePaymentFields()) {
            try {
                String payId = txtPayemnt_Id.getText();
                Double amount = Double.valueOf(txtAmount.getText());
                java.sql.Date date = java.sql.Date.valueOf(txtDate.getText());
                String status = txtStatus.getText();
                String paymentType = txtPayment_Type.getText();
                String insuranceId = txtInsuranace_Id.getText();

                PaymentDTO updatedPayment = new PaymentDTO(payId, date, amount, paymentType, status, insuranceId);

                boolean updated = paymentBO.update(updatedPayment);
                if (updated) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Updated Successfully").show();
                    loadPaymentTable();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Payment").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error Occurred While Updating Payment").show();
            }
        }
    }


    @FXML
    void onActionbtnReset(ActionEvent event) throws SQLException {
        txtPayemnt_Id.setText(paymentBO.generateNewId());
        txtDate.clear();
        txtAmount.clear();
        txtPayment_Type.clear();
        txtStatus.clear();
        txtInsuranace_Id.clear();

    }

    @FXML
    void onActiontbtnDashBoard(ActionEvent event) {
        navigateTo("/view/AdminForm.fxml");
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
            ((Stage)content.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load FXML at path: " + fxmlPath); // Log the FXML path
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }

    }



}
