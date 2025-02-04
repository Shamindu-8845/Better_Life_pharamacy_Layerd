package lk.ijse.gdse.demo.bo;

import lk.ijse.gdse.demo.bo.custom.Impl.*;
import lk.ijse.gdse.demo.bo.custom.Impl.OrderBOImpl;


public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory==null?boFactory=new BOFactory():boFactory;
    }

    public enum BOType {
        CUSTOMER,DELIVERY,EMPLOYEE,INSURANCE,INVENTORY,MEDICATION,ORDER,ORDERDETAILS,PAYMENT,SALARY,SUPPLIER,USER
    }

    public SuperBO getBO(BOFactory.BOType boType) {
        switch (boType) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INSURANCE:
                return new InsuranceBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case MEDICATION:
                return new MedicationBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDERDETAILS:
                return  new OrderDetailsBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case SUPPLIER:
                return  new SupplierBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
