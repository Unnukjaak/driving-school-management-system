package ee.drivingschool.dto;

import ee.drivingschool.model.Status;

public class CourseEditRequestDto extends CourseCreationRequestDto {

    CourseEditRequestDto(String courseName, String category, String startDate, String endDate, Long teacherId, Status status) {
        super(courseName, category, startDate, endDate, teacherId, status);
    }
}
