package lk.ijse.gdse.demo.controller;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerFormController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    @FXML
    private AnchorPane MainAnchorPane;

    @FXML
    private AnchorPane OptionAnchorPane;

    @FXML
    private AnchorPane SubAnchorPane;

    @FXML
    private Button btnInsurance;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnPrescription;

    @FXML
    private Button btnRegister;

    @FXML
    private Hyperlink hyperlinkmassage;

    @FXML
    private TextField txtCustomer_Address;

    @FXML
    private TextField txtCustomer_Id;

    @FXML
    private TextField txtCustomer_Name;

    @FXML
    private TextField txtPhone_Number;

    @FXML
    private TextField txtUser_Id;

    @FXML
    void onActionMassageGiven(ActionEvent event) {

    }


    @FXML
    void onActionInsurance(ActionEvent event) {

    }

    @FXML
    void onActionLogout(ActionEvent event) {

    }

    @FXML
    void onActionPlaceOrder(ActionEvent event) {

    }

    @FXML
    void onActionPrescription(ActionEvent event) {

    }

    @FXML
    void onActionRegister(ActionEvent event) {

    }


    @FXML
    void onActionRegisterForm(ActionEvent event) {

    }

}
