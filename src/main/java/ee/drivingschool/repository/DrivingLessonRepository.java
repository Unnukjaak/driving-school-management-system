package ee.drivingschool.repository;

import ee.drivingschool.model.DrivingLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DrivingLessonRepository extends JpaRepository<DrivingLesson, Long> {
    List<DrivingLesson> findAllByStartAt(LocalDate date);
}
