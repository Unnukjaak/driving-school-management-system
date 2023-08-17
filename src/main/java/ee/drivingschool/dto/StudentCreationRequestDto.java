package ee.drivingschool.dto;

import ee.drivingschool.model.Status;
import ee.drivingschool.validators.EstonianIdCode;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class StudentCreationRequestDto {

    private Long id;
    @NotBlank(message = "Please enter your first name")
    private String firstName;
    @NotBlank(message = "Please enter your last name")
    private String lastName;
    @EstonianIdCode
    private String idCode;
    @NotBlank(message = "Please enter a valid phone number")
    private String phone;
    @Email(message = "Please enter a valid email address")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @Min(value = 1, message = "Please select a course")
    private Long courseId;
    private Status status;
}
