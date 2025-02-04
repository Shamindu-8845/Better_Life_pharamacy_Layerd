package lk.ijse.gdse.demo.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class CartTM {

    private String Medication_Id;
    private String Name;
    private int Cart_qty;
    private double Unit_price;
    private double Total;
    private Button removeBtn;



}
