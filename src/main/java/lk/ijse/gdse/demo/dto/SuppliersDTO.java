package lk.ijse.gdse.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SuppliersDTO {
    private String Sup_Id;
    private String Name;
    private String Phone_No;

    public String getSupId() {
        return Sup_Id;
    }

    public String getPhoneNo() {
        return Phone_No;
    }
}
