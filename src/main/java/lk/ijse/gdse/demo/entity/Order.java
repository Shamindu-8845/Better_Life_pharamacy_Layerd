package lk.ijse.gdse.demo.entity;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private String Order_Id;
    private String Customer_Id;
    private Date Order_Date;
    private Date Delivery_Date;
    private String Medication_Name;
    private String Medication_Id;

    private ArrayList<OderDetails> orderDetails;  // This holds the details of the order

    public <E> Order(String orderId, String customerId, Date date, Date deliveryDate, String itemName, ArrayList<E> es, ArrayList<OderDetails> orderDetailsList) {
    }

    public Order(String orderId, String customerId, Date orderDate, Date deliveryDate, String medicationName, String orderId1) {
    }

    // Method to return the order details
    public ArrayList<OderDetails> getOrderDetailsDTO() {
        return orderDetails;
    }

    // Method to set the order details
    public void setOderDetailsDTO(ArrayList<OderDetails> orderDetailsDTOS) {
        this.orderDetails = orderDetailsDTOS;  // Set the order details correctly
    }
}
