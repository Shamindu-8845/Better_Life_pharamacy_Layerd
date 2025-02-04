package lk.ijse.gdse.demo.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OderDetailsDTO {
    private String Order_Id;
    private String Medication_Id;
    private int quantity;
    private double Price;


}
