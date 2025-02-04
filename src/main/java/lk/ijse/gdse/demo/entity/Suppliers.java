package lk.ijse.gdse.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Suppliers {
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
