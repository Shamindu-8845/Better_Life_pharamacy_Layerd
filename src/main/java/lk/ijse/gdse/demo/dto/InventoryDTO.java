package lk.ijse.gdse.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryDTO {
    private String Inventory_Id;
    private int Record_Level;
    private int Stock_Level;
}
