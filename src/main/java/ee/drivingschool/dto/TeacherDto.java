package ee.drivingschool.dto;

import lombok.Data;

@Data
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;

    private String teacherName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}

