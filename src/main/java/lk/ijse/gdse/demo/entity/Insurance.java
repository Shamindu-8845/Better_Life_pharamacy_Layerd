package lk.ijse.gdse.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Insurance {
    private String Insurance_Id;
    private String Customer_Id;
    private String Company_Name;
    private double Discount;

}
