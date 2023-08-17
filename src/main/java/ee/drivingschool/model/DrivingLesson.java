package ee.drivingschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
public class DrivingLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "driving_card_id")
    private DrivingCard drivingCard;

    private String topic;
    private LocalDate startAt;
    private int durationInMinutes;
    private DrivingLessonStatus status;
    private String studentComment;
    private LocalTime startTime;

    public DrivingLesson() {
    }

    public DrivingLesson(DrivingCard drivingCard, String topic, LocalDate startAt, int durationInMinutes, DrivingLessonStatus status) {
        this.drivingCard = drivingCard;
        this.topic = topic;
        this.startAt = startAt;
        this.durationInMinutes = durationInMinutes;
        this.status = status;
    }
}
