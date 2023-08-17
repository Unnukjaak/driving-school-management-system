package ee.drivingschool.dto;

import ee.drivingschool.model.Course;
import ee.drivingschool.model.Student;
import ee.drivingschool.model.Teacher;
import lombok.Data;

@Data
public class DrivingCardDto {

    private Long id;
    private String courseName;
    private String studentName;
    private String teacherName;

}
