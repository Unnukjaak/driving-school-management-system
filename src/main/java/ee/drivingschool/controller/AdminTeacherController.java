package ee.drivingschool.controller;

import ee.drivingschool.dto.StudentDto;
import ee.drivingschool.dto.TeacherCreationRequestDto;
import ee.drivingschool.dto.TeacherDto;
import ee.drivingschool.dto.TeacherEditDto;
import ee.drivingschool.dto.TeacherEditRequestDto;
import ee.drivingschool.exception.TeacherNotFoundException;
import ee.drivingschool.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminTeacherController {

    private final TeacherService teacherService;

    public AdminTeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/admin/teachers")
    public String getAllTeachers() {
        return "redirect:/admin/teachers/1";
    }


    // ---------------------- CREATE NEW TEACHER ----------------------
    @GetMapping("/admin/teacher/create")
    public String showCreateTeacherForm(ModelMap modelMap) {
        TeacherDto teacherDto = new TeacherDto();
        modelMap.addAttribute("teacherDto", teacherDto);
        return "create-teacher";
    }
    @PostMapping("/admin/teacher")
    public String createTeacher(@ModelAttribute("teacherDto") TeacherCreationRequestDto teacherCreationRequestDto) {
        teacherService.createNewTeacher(teacherCreationRequestDto);
        return "redirect:/admin/teachers";
    }

    // ---------------------- EDIT TEACHER ----------------------
    @GetMapping("/admin/teacher/{id}")
    public String showEditTeacherForm(@PathVariable("id") Long id, ModelMap modelMap) throws TeacherNotFoundException {
        TeacherEditDto teacherEditDto = teacherService.getTeacherDtoById(id);
        modelMap.addAttribute("teacher", teacherEditDto);
        return "edit-teacher";
    }
    @PostMapping("/admin/teacher/{id}")
    public String editTeacher(@PathVariable("id") Long id, @ModelAttribute("teacher")
                              TeacherEditRequestDto teacherEditRequestDto) throws TeacherNotFoundException {
        teacherService.edit(id, teacherEditRequestDto);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/admin/teachers/{pageNo}")
    public String getAllTeachers(@PathVariable(value = "pageNo") int pageNo, ModelMap modelMap) {
        int pageSize = 5;

        Page<TeacherDto> page = teacherService.findPaginated(pageNo, pageSize);
        List<TeacherDto> listTeachers = page.getContent();


        modelMap.addAttribute("currentPage", pageNo);
        modelMap.addAttribute("totalPages", page.getTotalPages());
        modelMap.addAttribute("totalItems", page.getTotalElements());
        modelMap.addAttribute("teachersList", listTeachers);

        return "admin-teachers";
    }

}
