package ee.drivingschool.service;

import ee.drivingschool.dto.*;
import ee.drivingschool.exception.CourseNotFoundException;
import ee.drivingschool.exception.Errors;
import ee.drivingschool.exception.StudentNotFoundException;
import ee.drivingschool.model.Course;
import ee.drivingschool.model.Status;
import ee.drivingschool.model.Student;
import ee.drivingschool.model.Teacher;
import ee.drivingschool.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final CourseService courseService;

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public List<StudentDto> getAllStudentsDto() {
        List<Student> students = studentRepository.findAll();
        return toStudentDtoList(students);
    }

    private List<StudentDto> toStudentDtoList(List<Student> students) {

        List<StudentDto> studentDtoList = new ArrayList<>();

        for (Student student : students) {
            studentDtoList.add(toStudentDto(student));
        }
        return studentDtoList;
    }

    private StudentDto toStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setIdCode(student.getIdCode());
        studentDto.setPhone(student.getPhone());
        studentDto.setEmail(student.getEmail());
        studentDto.setAddress(student.getAddress());

        Status status = student.getStatus();
        if (status != null) {
            studentDto.setStatus(student.getStatus());
        }

        Course course = student.getCourse();
        if (course != null) {
            studentDto.setCourseName(course.getCourseName());
        }
        return studentDto;
    }

    public Student createNewStudent(StudentCreationRequestDto studentCreationRequestDto) {
        Student student = toStudent(studentCreationRequestDto);
        return studentRepository.save(student);
    }

    private Student toStudent(StudentCreationRequestDto studentCreationRequestDto) {

        Course course;
        try {
            course = courseService.findCourseById(studentCreationRequestDto.getCourseId());
        } catch (CourseNotFoundException e) {
            course = null;
        }

        Student student = new Student();
        student.setFirstName(studentCreationRequestDto.getFirstName());
        student.setLastName(studentCreationRequestDto.getLastName());
        student.setIdCode(studentCreationRequestDto.getIdCode());
        student.setPhone(studentCreationRequestDto.getPhone());
        student.setEmail(studentCreationRequestDto.getEmail());
        student.setAddress(studentCreationRequestDto.getAddress());
        student.setStatus(studentCreationRequestDto.getStatus());

        if (course != null) {
            student.setCourse(course);
        }
        return student;
    }

    public StudentEditDto getStudentDtoById(Long id) throws StudentNotFoundException {

        Student student = findStudentById(id);

        return toStudentEditDto(student);
    }

    private StudentEditDto toStudentEditDto(Student student) {

        Course course = student.getCourse();

        StudentEditDto studentEditDto = new StudentEditDto();
        studentEditDto.setId(student.getId());
        studentEditDto.setFirstName(student.getFirstName());
        studentEditDto.setLastName(student.getLastName());
        studentEditDto.setIdCode(student.getIdCode());
        studentEditDto.setPhone(student.getPhone());
        studentEditDto.setEmail(student.getEmail());
        studentEditDto.setAddress(student.getAddress());
        studentEditDto.setStatus(student.getStatus());
        if (course != null) {
            studentEditDto.setCourseId(course.getId());
            studentEditDto.setCourseName(course.getCourseName());
        }
        return studentEditDto;
    }

    private Student findStudentById(Long id) throws StudentNotFoundException {
        return studentRepository.findById(id).orElseThrow(()
                -> new StudentNotFoundException("Student not found", Errors.STUDENT_NOT_FOUND));
    }

    public void edit(Long id, StudentEditRequestDto studentEditRequestDto) throws StudentNotFoundException {

        Student student = findStudentById(id);
        student.setFirstName(studentEditRequestDto.getFirstName());
        student.setLastName(studentEditRequestDto.getLastName());
        student.setIdCode(studentEditRequestDto.getIdCode());
        student.setPhone(studentEditRequestDto.getPhone());
        student.setEmail(studentEditRequestDto.getEmail());
        student.setAddress(studentEditRequestDto.getAddress());
        student.setStatus(studentEditRequestDto.getStatus());
        studentRepository.save(student);
    }

    public List<StudentDto> findCourseStudents(Long courseId) {
        List<Student> students = studentRepository.findAllByCourseId(courseId);
        return toStudentDtoList(students);
    }

    public Page<StudentDto> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Student> page = studentRepository.findAll(pageable);
        List<StudentDto> studentDtoList = convertStudentListToDto(page.getContent());
        return new PageImpl<>(studentDtoList, pageable, page.getTotalElements());
    }

    private List<StudentDto> convertStudentListToDto(List<Student> studentList) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (Student student : studentList) {
            Course course = student.getCourse();
            StudentDto studentDto = new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setIdCode(student.getIdCode());
            studentDto.setAddress(student.getAddress());
            studentDto.setPhone(student.getPhone());
            studentDto.setEmail(student.getEmail());
            studentDto.setStatus(student.getStatus());
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            if (course != null) {
                studentDto.setCourseName(course.getCourseName());
            }
            studentDtoList.add(studentDto);
        }
        return studentDtoList;
    }

    public boolean isStudentWithEmailExists(String email) {
        return studentRepository.findAllByEmail(email).size() > 0;
    }
}
