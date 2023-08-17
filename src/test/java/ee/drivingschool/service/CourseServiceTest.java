package ee.drivingschool.service;

import ee.drivingschool.dto.CourseDto;
import ee.drivingschool.model.Course;
import ee.drivingschool.model.Status;
import ee.drivingschool.model.Teacher;
import ee.drivingschool.repository.CourseRepository;
import ee.drivingschool.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    CourseRepository courseRepository;

    @Mock
    TeacherRepository teacherRepository;

    @Test
    public void getAllCourses_returnSuccessfully() {
        // Given
        Teacher teacher = new Teacher(1L,
                "Jason",
                "Bourne",
                "+37254348984",
                "Tuukri 15-17",
                "jason@gmail.com");

        Course course1 = Course.builder()
                .id(1L).courseName("50-T-A-23-01")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .status(Status.ACTIVE)
                .teacher(teacher)
                .build();

        List<Course> courses = new ArrayList<>();
        courses.add(course1);

        // When
        when(courseRepository.findAll()).thenReturn(courses);
        CourseService courseService = new CourseService(courseRepository, teacherRepository);
        List<CourseDto> courseDtoList = courseService.getAllCoursesDto();

        // Then
        assertEquals(1, courseDtoList.size());
        assertEquals(courseDtoList.get(0).getId(), 1L);
        assertEquals(courseDtoList.get(0).getCourseName(), "50-T-A-23-01");
        assertEquals(course1.getStartDate(), courseDtoList.get(0).getStartDate());
        assertEquals(course1.getEndDate(), courseDtoList.get(0).getEndDate());
        assertEquals(Status.ACTIVE, courseDtoList.get(0).getStatus());
        assertEquals("Jason Bourne", courseDtoList.get(0).getTeacherName());

    }
}
