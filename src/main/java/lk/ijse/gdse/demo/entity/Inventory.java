package lk.ijse.gdse.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inventory {
    private String Inventory_Id;
    private int Record_Level;
    private int Stock_Level;
}
