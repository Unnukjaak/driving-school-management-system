package ee.drivingschool.dto;

import ee.drivingschool.model.DrivingLessonStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DrivingLessonCreationRequestDto {

    private String topic;
    private String startAt;
    private DrivingLessonStatus status;
    private LocalTime startTime;
}
