package lk.ijse.gdse.demo.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Medication {
    private String Medication_Id;
    private String Name;
    private String Description;
    private Date Expiry_Date;
    private Double Price;
    private int Stock_Level;


}
