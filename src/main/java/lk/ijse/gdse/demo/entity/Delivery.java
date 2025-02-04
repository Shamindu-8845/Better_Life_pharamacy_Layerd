package lk.ijse.gdse.demo.entity;

import javafx.scene.control.Button;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Delivery {
    private String Delivery_Id;
    private Date Delivery_Date;
    private String Order_Id;
    private String Location;
    private String Customer_PhoneNo;
    private Button removeBtn;
}
