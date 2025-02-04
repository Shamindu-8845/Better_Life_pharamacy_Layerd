package lk.ijse.gdse.demo.dto;

import lk.ijse.gdse.demo.entity.Delivery;
import lombok.*;

import javafx.scene.control.Button;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryDTO {
    private String Delivery_Id;
    private Date Delivery_Date;
    private String Order_Id;
    private String Location;
    private String Customer_PhoneNo;
    private Button removeBtn;
}
