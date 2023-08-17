package ee.drivingschool.controller;

import ee.drivingschool.dto.DrivingCardDto;
import ee.drivingschool.dto.DrivingLessonCreationRequestDto;
import ee.drivingschool.dto.DrivingLessonsDto;
import ee.drivingschool.exception.DrivingCardNotFoundException;
import ee.drivingschool.exception.DrivingLessonNotFoundException;
import ee.drivingschool.model.DrivingCard;
import ee.drivingschool.model.DrivingLesson;
import ee.drivingschool.model.DrivingLessonStatus;
import ee.drivingschool.service.DrivingCardService;
import ee.drivingschool.service.DrivingLessonService;
import ee.drivingschool.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DrivingLessonsController {

    @Autowired
    private DrivingLessonService drivingLessonService;

    @Autowired
    private DrivingCardService drivingCardService;

    @GetMapping("/lessons")
    public String getAllDrivingLessons(@RequestParam(value = "date", required = false) String date, ModelMap modelMap) {
        LocalDate selectedDate = LocalDate.now();
        if (date != null) {
            selectedDate = DateUtils.convertDate(date);
        }
        List<DrivingLessonsDto> drivingLessonsList = drivingLessonService.getAllDrivingLessonsOnDate(selectedDate);
        modelMap.addAttribute("drivingLessonsList", drivingLessonsList);
        modelMap.addAttribute("selectedDate", DateUtils.convertLocalDateToString(selectedDate));
        return "driving-lessons";
    }

    // ---------------------- CREATE NEW DRIVING LESSON ----------------------
    @GetMapping("/lesson/create/{drivingCardId}")
    public String showCreateDrivingLessonForm(@PathVariable("drivingCardId") Long drivingCardId, ModelMap modelMap,
                                              DrivingLessonCreationRequestDto drivingLessonDto) throws DrivingCardNotFoundException {
        DrivingCard drivingCard = drivingCardService.getDrivingCardById(drivingCardId);
        DrivingCardDto drivingCardDto = drivingCardService.toDrivingCardDto(drivingCard);
        modelMap.addAttribute("drivingLessonDto", drivingLessonDto);
        modelMap.addAttribute("drivingCardDto", drivingCardDto);
        modelMap.addAttribute("statuses", DrivingLessonStatus.values());
        return "driving-lesson-create";
    }
    @PostMapping("/lesson/create/{drivingCardId}")
    public String createDrivingLesson(@PathVariable("drivingCardId") Long drivingCardId,
                                      @ModelAttribute("drivingLesson")
                                      DrivingLessonCreationRequestDto drivingLessonCreationRequestDto) throws DrivingCardNotFoundException {
        drivingLessonService.create(drivingCardId, drivingLessonCreationRequestDto);
        return "redirect:/lessons?date=" + drivingLessonCreationRequestDto.getStartAt();
    }
    @PostMapping("/lesson/comment/{lessonId}")
    public String createLessonStudentComment(@PathVariable("lessonId") Long lessonId, @RequestParam String comment) throws DrivingLessonNotFoundException {
        DrivingLesson drivingLesson = drivingLessonService.getDrivingLessonById(lessonId);
        drivingLessonService.changeStudentComment(drivingLesson, comment); // TODO: Validate student ID
        return "redirect:/lessons?date=" + DateUtils.convertLocalDateToString(drivingLesson.getStartAt());
    }
}
