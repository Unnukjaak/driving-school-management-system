package ee.drivingschool.repository;

import ee.drivingschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Object getStudentById(long l);

    List<Student> findAllByCourseId(Long courseId);

    List<Student> findAllByEmail(String email);
}
