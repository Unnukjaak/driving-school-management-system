package ee.drivingschool.dto;

import ee.drivingschool.model.DrivingLessonStatus;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DrivingLessonsDto {

    private Long id;
    private String topic;
    private LocalDate startAt;
    private int durationInMinutes;
    private DrivingLessonStatus status;
    private String studentComment;
    private Long drivingCardId;
    private String studentFullName;
    private LocalTime startTime;

    public String getStatusCssClass() {
        if (status == null) {
            return "";
        }
        switch (status) {
            case PENDING -> {
                return "text-bg-light";
            }
            case BOOKED -> {
                return "text-bg-primary";
            }
            case PASSED -> {
                return "text-bg-success";
            }
            case CANCELED -> {
                return "text-bg-danger";
            }
            default -> {
                return "text-bg-info";
            }
        }
    }
}
