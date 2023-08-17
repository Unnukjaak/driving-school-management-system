package ee.drivingschool.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class TeacherResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;
}
