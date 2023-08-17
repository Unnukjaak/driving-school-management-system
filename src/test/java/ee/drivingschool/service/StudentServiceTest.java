package ee.drivingschool.service;

import ee.drivingschool.dto.StudentDto;
import ee.drivingschool.exception.StudentNotFoundException;
import ee.drivingschool.model.Course;
import ee.drivingschool.model.Status;
import ee.drivingschool.model.Student;
import ee.drivingschool.repository.CourseRepository;
import ee.drivingschool.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    CourseRepository courseRepository;

    @Mock
    CourseService courseService;

    @Mock
    StudentRepository studentRepository;

    @Test
    public void testToStudentEditDto_returnSuccessfully() throws StudentNotFoundException {
        // Given
        Student student = new Student(1L,
                "Mark",
                "Peterson",
                "38412070234",
                "+3725638234",
                "Tuukri põik 15",
                "test@gmail.com",
                Status.ACTIVE);

        Course course1 = Course.builder()
                .id(1L).courseName("50-T-A-23-01")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .status(Status.ACTIVE)
                .build();

        student.setCourse(course1);

        // When
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        StudentService studentService = new StudentService(studentRepository, courseService);
        StudentDto studentDto = studentService.getStudentDtoById(1L);

        // Then
        assertEquals(1L, studentDto.getId());
        assertEquals(course1.getId(), studentDto.getCourseId());
        assertEquals(course1.getCourseName(), studentDto.getCourseName());


    }
}
