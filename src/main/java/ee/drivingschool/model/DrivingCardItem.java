package ee.drivingschool.model;

import lombok.Data;

@Data
public class DrivingCardItem {

    private Long id;
    private String courseName;
    private String studentFullName;
    private String teacherFullName;
    private Long studentId;
    private Long teacherId;
    private Long courseId;
}
