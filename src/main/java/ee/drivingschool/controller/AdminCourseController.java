package ee.drivingschool.controller;

import ee.drivingschool.dto.*;
import ee.drivingschool.exception.CourseNotFoundException;
import ee.drivingschool.model.Status;
import ee.drivingschool.service.CourseService;
import ee.drivingschool.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class AdminCourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public AdminCourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping("/")
    public String getAllCourses(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("STUDENT".equals(authority.getAuthority())) {
                    return "redirect:/lessons";
                }
            }
        }
        return "redirect:/page/1";
    }

    // ---------------------- CREATE NEW COURSE ----------------------
    @GetMapping("/admin/course/create")
    public String showCreateCourseForm(ModelMap modelMap) {
        CourseDto courseDto = new CourseDto();
        List<TeacherDto> teachers = teacherService.getAllTeachersDto();
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("course", courseDto);
        return "create-course";
    }

    @PostMapping("/admin/course/create")
    public String createCourse(@ModelAttribute("course") CourseCreationRequestDto courseCreationRequestDto) {
        courseService.createNewCourse(courseCreationRequestDto);
        return "redirect:/";
    }

    //---------------------- EDIT COURSE ----------------------
    @GetMapping("/admin/course/{id}")
    public String showEditCourseForm(@PathVariable("id") Long id, ModelMap modelMap) throws CourseNotFoundException {
        CourseDto courseDto = courseService.getCourseEditDtoById(id);
        List<TeacherDto> teachers = teacherService.getAllTeachersDto();
        modelMap.addAttribute("teachers", teachers);
        modelMap.addAttribute("course", courseDto);
        modelMap.addAttribute("statuses", Status.values());
        return "edit-course";
    }

    @PostMapping("/admin/course/{id}")
    public String editCourse(@PathVariable("id") Long id, @ModelAttribute("course") CourseEditRequestDto courseEditRequestDto) throws CourseNotFoundException {
        courseService.edit(id, courseEditRequestDto);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, ModelMap modelMap, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("STUDENT".equals(authority.getAuthority())) {
                    return "redirect:/lessons";
                }
            }
        }
        int pageSize = 5;

        Page<CourseDto> page = courseService.findPaginated(pageNo, pageSize);
        List<CourseDto> listCourses = page.getContent();


        modelMap.addAttribute("currentPage", pageNo);
        modelMap.addAttribute("totalPages", page.getTotalPages());
        modelMap.addAttribute("totalItems", page.getTotalElements());
        modelMap.addAttribute("coursesList", listCourses);

        return "index";
    }
}
