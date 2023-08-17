package ee.drivingschool.controller;

import ee.drivingschool.dto.*;
import ee.drivingschool.exception.StudentNotFoundException;
import ee.drivingschool.model.Status;
import ee.drivingschool.model.Student;
import ee.drivingschool.service.CourseService;
import ee.drivingschool.service.DrivingCardService;
import ee.drivingschool.service.StudentService;
import ee.drivingschool.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminStudentsController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DrivingCardService drivingCardService;

    @GetMapping("/admin/students")
    public String getAllStudents(@RequestParam(value = "courseId", required = false) Long courseId, final ModelMap modelMap) {
        List<StudentDto> studentList;
        if (courseId != null) {
            studentList = studentService.findCourseStudents(courseId);
        } else {
            studentList = studentService.getAllStudentsDto();
        }
        modelMap.addAttribute("pageNo", 1);
        modelMap.addAttribute("studentsList", studentList);
        return "admin-students";
    }

    // ---------------------- CREATE NEW STUDENT ----------------------
    @GetMapping("/admin/student/create")
    public String showCreateStudentForm(StudentCreationRequestDto studentDto, ModelMap modelMap) {
        List<CourseDto> courses = courseService.getAllCoursesDto();
        modelMap.addAttribute("studentDto", studentDto);
        modelMap.addAttribute("courses", courses);
        modelMap.addAttribute("statuses", Status.values());
        return "create-student";
    }

    @PostMapping("admin/student/create")
    public String createStudent(@Valid @ModelAttribute("studentDto") StudentCreationRequestDto studentCreationRequestDto,
                                BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            List<CourseDto> courses = courseService.getAllCoursesDto();
            modelMap.addAttribute("courses", courses);
            modelMap.addAttribute("statuses", Status.values());
            return "create-student";
        }
        Student student = studentService.createNewStudent(studentCreationRequestDto);
        drivingCardService.createNewDrivingCard(student);
        return "redirect:/admin/students/1";
    }

    // ---------------------- EDIT STUDENT ----------------------
    @GetMapping("/admin/student/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, ModelMap modelMap) throws StudentNotFoundException {
        StudentEditDto studentEditDto = studentService.getStudentDtoById(id);
        List<CourseDto> courses = courseService.getAllCoursesDto();
        modelMap.addAttribute("student", studentEditDto);
        modelMap.addAttribute("courses", courses);
        modelMap.addAttribute("statuses", Status.values());
        return "edit-student";
    }

    @PostMapping("/admin/student/{id}")
    public String editStudent(@PathVariable("id") Long id,
                              @ModelAttribute("student")
                              StudentEditRequestDto studentEditRequestDto) throws StudentNotFoundException {
        studentService.edit(id, studentEditRequestDto);
        return "redirect:/admin/students";
    }

    // ---------------------- STUDENT PAGE ----------------------
    @GetMapping("/student")
    public String showStudentPage(ModelMap modelMap) {
        return "layout-student";

    }

    @GetMapping("admin/students/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, ModelMap modelMap) {
        int pageSize = 5;
        Page<StudentDto> page = studentService.findPaginated(pageNo, pageSize);
        List<StudentDto> listStudents = page.getContent();


        modelMap.addAttribute("currentPage", pageNo);
        modelMap.addAttribute("totalPages", page.getTotalPages());
        modelMap.addAttribute("totalItems", page.getTotalElements());
        modelMap.addAttribute("studentsList", listStudents);

        return "admin-students";
    }


}
