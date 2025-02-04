package lk.ijse.gdse.demo.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Salary {
    private String Salary_Id;
    private Double Amount;
    private Date date;

    public String getSalary_Id(){
        return Salary_Id;
    }
}
