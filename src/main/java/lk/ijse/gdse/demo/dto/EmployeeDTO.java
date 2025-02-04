package lk.ijse.gdse.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO {
    private String Employee_Id;
    private String Salary_Id;
    private String Roll;
    private String Name;

    public Object getSalary() {
        return Salary_Id;
    }
}
