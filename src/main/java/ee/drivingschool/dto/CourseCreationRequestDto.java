package ee.drivingschool.dto;

import ee.drivingschool.model.Status;
import ee.drivingschool.utils.DateUtils;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
public class CourseCreationRequestDto {

    private String courseName;
    private String category;
    private String startDate;
    private String endDate;
    private Long teacherId;
    private Status status;

    public LocalDate getStartDate() {
        return DateUtils.convertDate(startDate);
    }

    public LocalDate getEndDate() {
        return DateUtils.convertDate(endDate);
    }
}
