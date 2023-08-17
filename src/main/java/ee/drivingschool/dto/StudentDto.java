package ee.drivingschool.dto;

import ee.drivingschool.model.Status;
import lombok.Builder;
import lombok.Data;

@Data
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String idCode;
    private String phone;
    private String email;
    private String address;
    private Long courseId;
    private String courseName;
    private Status status;

    public String getStatusCssClass() {
        if (status == null) {
            return "";
        }
        switch (status) {
            case ACTIVE -> {
                return "text-bg-success";
            }
            case INACTIVE -> {
                return "text-bg-danger";
            }
            case DELETED -> {
                return "text-bg-secondary";
            }
            case PENDING -> {
                return "text-bg-warning";
            }
            default -> {
                return "text-bg-info";
            }
        }
    }

    public void setTeacher(TeacherDto convertTeacherToDto) {
    }

    public void setTeacherName(String fullName) {
    }
}
