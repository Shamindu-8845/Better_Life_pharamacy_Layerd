package lk.ijse.gdse.demo.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicationDTO {
    private String Medication_Id;
    private String Name;
    private String Description;
    private Date Expiry_Date;
    private Double Price;
    private int Stock_Level;


}
