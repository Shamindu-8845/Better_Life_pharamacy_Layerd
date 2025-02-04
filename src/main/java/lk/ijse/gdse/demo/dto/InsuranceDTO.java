package lk.ijse.gdse.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InsuranceDTO {
    private String Insurance_Id;
    private String Customer_Id;
    private String Company_Name;
    private double Discount;

}
