package ee.drivingschool.service;

import ee.drivingschool.dto.CourseCreationRequestDto;
import ee.drivingschool.dto.CourseDto;
import ee.drivingschool.dto.CourseEditRequestDto;
import ee.drivingschool.exception.CourseNotFoundException;
import ee.drivingschool.exception.Errors;
import ee.drivingschool.model.Course;
import ee.drivingschool.model.Status;
import ee.drivingschool.model.Teacher;
import ee.drivingschool.repository.CourseRepository;
import ee.drivingschool.repository.TeacherRepository;
import ee.drivingschool.utils.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<CourseDto> getAllCoursesDto() {
        List<Course> courses = courseRepository.findAll();
        return toCourseDto(courses);
    }

    private List<CourseDto> toCourseDto(List<Course> courses) {

        List<CourseDto> courseDtoList = new ArrayList<>();

        for (Course course : courses) {
            courseDtoList.add(toCourseDto(course));
        }
        return courseDtoList;
    }

    public CourseDto createNewCourse(CourseCreationRequestDto courseCreationRequestDto) {

        Course course = toCourse2(courseCreationRequestDto);

        Course sevedCourse = courseRepository.save(course);

        return toCourseDto(sevedCourse);
    }

    private CourseDto toCourseDto(Course course) {

        Teacher teacher = course.getTeacher();
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setCourseName(course.getCourseName());
        courseDto.setCategory(course.getCategory());
        courseDto.setStartDate(DateUtils.convertLocalDateToString(course.getStartDate()));
        courseDto.setEndDate(DateUtils.convertLocalDateToString(course.getEndDate()));
        courseDto.setStatus(course.getStatus());
        if (teacher != null) {
            courseDto.setTeacherId(teacher.getId());
            courseDto.setTeacherName(teacher.getFullName());
        }
        return courseDto;
    }

    private Course toCourse2(CourseCreationRequestDto courseCreationRequestDto) {
        Teacher teacher = teacherRepository.getReferenceById(courseCreationRequestDto.getTeacherId());
        Course course = Course.builder()
                .courseName(courseCreationRequestDto.getCourseName())
                .category(courseCreationRequestDto.getCategory())
                .startDate(courseCreationRequestDto.getStartDate())
                .endDate(courseCreationRequestDto.getEndDate())
                .teacher(teacher)
                .status(Status.PENDING)
                .build();
        return course;
    }

    public Course findCourseById(Long id) throws CourseNotFoundException {
        return courseRepository.findById(id).orElseThrow(()
                -> new CourseNotFoundException("Course not found", Errors.COURSE_NOT_FOUND));
    }

    public CourseDto getCourseEditDtoById(Long id) throws CourseNotFoundException {
        Course course = findCourseById(id);
        return toCourseDto(course);
    }

    public void edit(Long id, CourseEditRequestDto courseEditRequestDto) throws CourseNotFoundException {
        Teacher teacher = teacherRepository.getReferenceById(courseEditRequestDto.getTeacherId());
        Course course = findCourseById(id);
        course.setCourseName(courseEditRequestDto.getCourseName());
        course.setCategory(courseEditRequestDto.getCategory());
        course.setStartDate(courseEditRequestDto.getStartDate());
        course.setEndDate(courseEditRequestDto.getEndDate());
        course.setTeacher(teacher);
        course.setStatus(courseEditRequestDto.getStatus());
        courseRepository.save(course);
    }


    public Page<CourseDto> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Course> page = courseRepository.findAll(pageable);
        List<CourseDto> courseDtoList = convertCourseListToDto(page.getContent());
        return new PageImpl<>(courseDtoList, pageable, page.getTotalElements());
    }

    private List<CourseDto> convertCourseListToDto(List<Course> courseList) {
        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courseList) {
            Teacher teacher = course.getTeacher();
            CourseDto courseDto = new CourseDto();
            courseDto.setId(course.getId());
            courseDto.setCourseName(course.getCourseName());
            courseDto.setCategory(course.getCategory());
            courseDto.setStartDate(DateUtils.convertLocalDateToString(course.getStartDate()));
            courseDto.setEndDate(DateUtils.convertLocalDateToString(course.getEndDate()));
            courseDto.setStatus(course.getStatus());
            if (teacher != null) {
                courseDto.setTeacherName(teacher.getFullName());
            }
            courseDtoList.add(courseDto);
        }
        return courseDtoList;
    }

    public List<CourseDto> getAllCoursesDtoByCourseName() {
        List<CourseDto> courseDtoList = new ArrayList<>();

        for (Course course : courseRepository.findAll()) {
            courseDtoList.add(toCourseDtoByCourseName(course));
        }
        return courseDtoList;
    }

    private CourseDto toCourseDtoByCourseName(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setCourseName(course.getCourseName());
        return courseDto;
    }
}




