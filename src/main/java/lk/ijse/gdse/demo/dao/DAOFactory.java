package lk.ijse.gdse.demo.dao;

import lk.ijse.gdse.demo.dao.custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType {
        CUSTOMER,DELIVERY,EMPLOYEE,INSURANCE,INVENTORY,MEDICATION,ORDER,ORDERDETAILS,PAYMENT,SALARY,SUPPLIER,USER
    }

    public  CrudDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case INSURANCE:
                return new InsuranceDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case MEDICATION:
                return new MedicationDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAILS:
                return  new OrderDetailsDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            case SUPPLIER:
                return  new SupplierDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
