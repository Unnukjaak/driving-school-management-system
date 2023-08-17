package ee.drivingschool.repository;

import ee.drivingschool.model.DrivingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrivingCardRepository extends JpaRepository<DrivingCard, Long> {

    @Query("SELECT dc.id, CONCAT(s.firstName, ' ', s.lastName) AS studentFullName," +
            " CONCAT(t.firstName, ' ', t.lastName)  AS teacherFullName, " +
            "c.courseName AS courseName, s.id, t.id, c.id " +
            "FROM DrivingCard dc " +
            "JOIN dc.student s " +
            "JOIN dc.teacher t " +
            "JOIN dc.course c")
    List<Object[]> findAllDrivingCardsWithNames();
}
