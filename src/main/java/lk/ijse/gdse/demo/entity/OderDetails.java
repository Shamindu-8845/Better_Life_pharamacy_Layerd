package lk.ijse.gdse.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OderDetails {
    private String Order_Id;
    private String Medication_Id;
    private int quantity;
    private double Price;


}
