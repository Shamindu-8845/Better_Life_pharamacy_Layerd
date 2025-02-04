package lk.ijse.gdse.demo.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private String Employee_Id;
    private String Salary_Id;
    private String Roll;
    private String Name;

    public Object getSalary() {
        return Salary_Id;
    }
}
