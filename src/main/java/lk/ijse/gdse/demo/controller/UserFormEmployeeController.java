package lk.ijse.gdse.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.demo.bo.BOFactory;
import lk.ijse.gdse.demo.bo.custom.EmployeeBO;
import lk.ijse.gdse.demo.bo.custom.SalaryBO;
import lk.ijse.gdse.demo.dto.EmployeeDTO;
import lk.ijse.gdse.demo.dto.SalaryDTO;
import lk.ijse.gdse.demo.dao.custom.Impl.EmployeeDAOImpl;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.gdse.demo.dao.custom.Impl.SalaryDAOImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UserFormEmployeeController {

    @FXML
    private TableColumn<EmployeeDTO, String> colEmployee_Id;

    @FXML
    private TableColumn<EmployeeDTO, String> colName;

    @FXML
    private TableColumn<EmployeeDTO, String> colRoll;

    @FXML
    private TableColumn<EmployeeDTO, String> colSalary_Id;

    @FXML
    private TableView<EmployeeDTO> tblEmployee;

    private EmployeeDAOImpl employeeDAOImpl;

    private SalaryDAOImpl salaryDAOImpl;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SalaryDTO, Double> colAmount;

    @FXML
    private TableColumn<SalaryDTO, Date> colDate;

    @FXML
    private TableColumn<SalaryDTO, String> colSalary_Id_Salary;

    @FXML
    private TableView<SalaryDTO> tblsalary;

    @FXML
    private TextField txtEmployee_Id;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRoll;

    @FXML
    private TextField txtSalary_Date;

    @FXML
    private TextField txtSalary_Id;

    @FXML
    private TextField txtSalarytable_Id;

    @FXML
    private TextField txtxSalary_Amount;

    //private SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SALARY);

    SalaryBO salaryBO = (SalaryBO) BOFactory.getInstance().getBO(BOFactory.BOType.SALARY);

    //private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    public void initialize() throws SQLException {
        employeeDAOImpl = new EmployeeDAOImpl();

        colEmployee_Id.setCellValueFactory(new PropertyValueFactory<>("Employee_Id"));
        colSalary_Id.setCellValueFactory(new PropertyValueFactory<>("Salary_Id")); // Direct PropertyValueFactory
        colRoll.setCellValueFactory(new PropertyValueFactory<>("Roll"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        txtEmployee_Id.setText(employeeBO.generateNewId());

        salaryDAOImpl = new SalaryDAOImpl();

        colSalary_Id_Salary.setCellValueFactory(new PropertyValueFactory<>("Salary_Id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount")); // Direct PropertyValueFactory
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tblEmployee.setOnMouseClicked(this::onRowClick);
        tblsalary.setOnMouseClicked(this::onRowClick);
        txtSalarytable_Id.setText(salaryBO.generateNewId());
        loadSalaryTable();

        loadEmployeeTable();
    }

    private void onRowClick(MouseEvent mouseEvent) {
        EmployeeDTO selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
        SalaryDTO selectedSalary = tblsalary.getSelectionModel().getSelectedItem();

        if (selectedSalary != null) {
            txtSalarytable_Id.setText(selectedSalary.getSalary_Id());
            txtSalary_Date.setText(String.valueOf(selectedSalary.getDate()));
            txtxSalary_Amount.setText(String.valueOf(selectedSalary.getAmount()));
        }

        if (selectedEmployee != null) {
            txtEmployee_Id.setText(selectedEmployee.getEmployee_Id());
            txtName.setText(selectedEmployee.getName());
            txtRoll.setText(selectedEmployee.getRoll());
            txtSalary_Id.setText(selectedEmployee.getSalary_Id());
        }
    }

    private void loadEmployeeTable() {
        try {
            List<EmployeeDTO> employeeDTOList = employeeBO.getAll();
            ObservableList<EmployeeDTO> employeeDTOObservableList = FXCollections.observableArrayList(employeeDTOList);
            tblEmployee.setItems(employeeDTOObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /////

    private void loadSalaryTable() {
        try {
            List<SalaryDTO> salaryList = salaryBO.getAll();
            ObservableList<SalaryDTO> salaryListObservableList = FXCollections.observableArrayList(salaryList);
            tblsalary.setItems(salaryListObservableList);

            /*List<CustomerDTO> customerList = customerBO.getAll();
            ObservableList<CustomerDTO> customerDTOS = FXCollections.observableArrayList(customerList);
            tblCustomer.setItems(customerDTOS);*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionEmployee_Add(ActionEvent event) {
        // Retrieve values from the form fields
        String empID = txtEmployee_Id.getText();  // Assuming you have a TextField for Employee_Id
        String empSalary = txtSalary_Id.getText(); // Assuming you have a TextField for Salary_Id
        String empRoll = txtRoll.getText();        // Assuming you have a TextField for Roll
        String empName = txtName.getText();        // Assuming you have a TextField for Name

        // Validate the input data
        if (empID.isEmpty() || empSalary.isEmpty() || empRoll.isEmpty() || empName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return;
        }

        // Create an EmployeeDTO object with the retrieved data
        EmployeeDTO employee = new EmployeeDTO(empID, empSalary, empRoll, empName);

        try {
            // Add the employee to the database using the employeeModel
            boolean isAdded = employeeBO.add(employee);

            if (isAdded) {
                loadEmployeeTable();  // Refresh the employee table
                clearFiedldsEmployee(); // Clear the form fields
            } else {
                System.out.println("Failed to Add Employee");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error adding employee: " + e.getMessage()).show();
        }
    }

    @FXML
    void onActionEmployee_Reset(ActionEvent event) throws SQLException {
        txtEmployee_Id.setText(employeeBO.generateNewId());
        txtRoll.clear();
        txtSalary_Id.clear();
        txtName.clear();
    }

    @FXML
    void onActionSalary_Add(ActionEvent event) {
        String salarytId = txtSalarytable_Id.getText();
        Date salarytDate = Date.valueOf(txtSalary_Date.getText());
        Double salarytAmount = Double.valueOf(txtxSalary_Amount.getText());

        SalaryDTO salaryDTO = new SalaryDTO(salarytId, salarytAmount, salarytDate);

        try{
            boolean isAdded = salaryBO.add(salaryDTO);
            if(isAdded){
                loadSalaryTable();
                clearFieldsSalary();
            }else{
                System.out.println("Failed to added salary");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionSalary_Delete(ActionEvent event) {
        String salaryId = txtSalarytable_Id.getText();

        try {
            boolean isDeleted = salaryBO.delete(salaryId);
            if (isDeleted) {
                loadSalaryTable();
                clearFieldsSalary();
            } else {
                System.out.println("Failed to delete customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionSalary_Reset(ActionEvent event) throws SQLException {



        txtSalarytable_Id.setText(salaryBO.generateNewId());
        txtSalary_Date.clear();
        txtxSalary_Amount.clear();

    }

    @FXML
    void onActionSalary_Update(ActionEvent event) {
        String salarytId = txtSalarytable_Id.getText();
        Date salarytDate = Date.valueOf(txtSalary_Date.getText());
        Double salarytAmount = Double.valueOf(txtxSalary_Amount.getText());

        SalaryDTO salaryDTO = new SalaryDTO(salarytId, salarytAmount, salarytDate);

        try{
            boolean isUpdate = salaryBO.update(salaryDTO);
            if(isUpdate){
                loadSalaryTable();
                clearFieldsSalary();
            }else{
                System.out.println("Failed to update salary");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActoinEmployee_Update(ActionEvent event) {
        // Retrieve values from the form fields
        String empID = txtEmployee_Id.getText();  // Assuming you have a TextField for Employee_Id
        String empSalary = txtSalary_Id.getText(); // Assuming you have a TextField for Salary_Id
        String empRoll = txtRoll.getText();        // Assuming you have a TextField for Roll
        String empName = txtName.getText();        // Assuming you have a TextField for Name

        // Validate the input data
        if (empID.isEmpty() || empSalary.isEmpty() || empRoll.isEmpty() || empName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields must be filled!").show();
            return;
        }

        // Create an EmployeeDTO object with the retrieved data
        EmployeeDTO employee = new EmployeeDTO(empID, empSalary, empRoll, empName);

        try {
            // Update the employee in the database using the employeeModel
            boolean isUpdated = employeeBO.update(employee);

            if (isUpdated) {
                loadEmployeeTable();  // Refresh the employee table
                clearFiedldsEmployee(); // Clear the form fields
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Employee").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating employee: " + e.getMessage()).show();
        }
    }

    @FXML
    void onAtion_EmployeeDelete(ActionEvent event) {
        // Retrieve the employee ID from the input field
        String empID = txtEmployee_Id.getText();  // Assuming you have a TextField for Employee_Id

        // Validate the input data
        if (empID.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Employee ID must be provided!").show();
            return;
        }

        // Confirm the deletion action
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Delete the employee from the database using the employeeModel
                    boolean isDeleted = employeeBO.delete(empID);

                    if (isDeleted) {
                        loadEmployeeTable();  // Refresh the employee table
                        clearFiedldsEmployee(); // Clear the form fields
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to Delete Employee").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error deleting employee: " + e.getMessage()).show();
                }
            }
        });
    }

    private void clearFieldsSalary() {
        txtSalary_Id.clear();
        txtSalary_Date.clear();
        txtxSalary_Amount.clear();
    }
    private void clearFiedldsEmployee() {
        txtEmployee_Id.clear();
        txtSalary_Id.clear();
        txtRoll.clear();
        txtName.clear();
    }



}
