package ee.drivingschool.dto;

import ee.drivingschool.model.Status;
import lombok.*;

@Data
public class CourseDto {

    private Long id;
    private String courseName;
    private String category;
    private String startDate;
    private String endDate;
    private Long teacherId;
    private String teacherName;
    private Status status;

    public void setTeacher(TeacherDto convertTeacherToDto) {
    }

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
}
