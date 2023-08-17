package ee.drivingschool.controller;

import ee.drivingschool.model.DrivingCardItem;
import ee.drivingschool.service.CourseService;
import ee.drivingschool.service.DrivingCardService;
import ee.drivingschool.service.StudentService;
import ee.drivingschool.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DrivingCardController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private DrivingCardService drivingCardService;

    @GetMapping("/driving_card")
    public String getAllDrivingCards(ModelMap modelMap) {
        List<DrivingCardItem> drivingCards = drivingCardService.getDrivingCardItemsList();
        modelMap.addAttribute("driving_cards", drivingCards);
        return "admin-driving-cards";
    }
}
