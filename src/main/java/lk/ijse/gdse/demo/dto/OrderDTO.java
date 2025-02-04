package lk.ijse.gdse.demo.dto;

import lombok.*;
import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private String Order_Id;
    private String Customer_Id;
    private Date Order_Date;
    private Date Delivery_Date;
    private String Medication_Name;
    private String Medication_Id;

    private ArrayList<OderDetailsDTO> orderDetails;  // This holds the details of the order

    public <E> OrderDTO(String orderId, String customerId, Date date, Date deliveryDate, String itemName, ArrayList<E> es, ArrayList<OderDetailsDTO> orderDetailsList) {
    }

    // Method to return the order details
    public ArrayList<OderDetailsDTO> getOrderDetailsDTO() {
        return orderDetails;
    }

    // Method to set the order details
    public void setOderDetailsDTO(ArrayList<OderDetailsDTO> orderDetailsDTOS) {
        this.orderDetails = orderDetailsDTOS;  // Set the order details correctly
    }
}
