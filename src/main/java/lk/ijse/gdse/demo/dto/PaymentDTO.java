package lk.ijse.gdse.demo.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
   private String Payment_Id;
   private Date Date;
   private double Amount;
   private String Payment_Type;
   private String Status;
   private String Insurance_Id;

//   public String setDate() {
//      return Date.toString();
//   }
}
