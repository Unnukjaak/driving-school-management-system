package ee.drivingschool.service;

import ee.drivingschool.dto.DrivingLessonCreationRequestDto;
import ee.drivingschool.dto.DrivingLessonsDto;
import ee.drivingschool.exception.DrivingCardNotFoundException;
import ee.drivingschool.exception.DrivingLessonNotFoundException;
import ee.drivingschool.exception.Errors;
import ee.drivingschool.model.DrivingCard;
import ee.drivingschool.model.DrivingLesson;
import ee.drivingschool.repository.DrivingLessonRepository;
import ee.drivingschool.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DrivingLessonService {

    @Autowired
    private DrivingLessonRepository drivingLessonRepository;

    @Autowired
    private DrivingCardService drivingCardService;

    public List<DrivingLessonsDto> getAllDrivingLessonsOnDate(LocalDate date) {
        List<DrivingLesson> drivingLessons = drivingLessonRepository.findAllByStartAt(date);
        return toDrivingLessonDtoList(drivingLessons);
    }

    private List<DrivingLessonsDto> toDrivingLessonDtoList(List<DrivingLesson> drivingLessons) {

        List<DrivingLessonsDto> drivingLessonsDto = new ArrayList<>();

        for (DrivingLesson drivingLesson : drivingLessons) {
            drivingLessonsDto.add(toDrivingLessonsDto(drivingLesson));
        }
        return drivingLessonsDto;
    }

    private DrivingLessonsDto toDrivingLessonsDto(DrivingLesson drivingLesson) {

        DrivingCard drivingCard = drivingLesson.getDrivingCard();

        DrivingLessonsDto drivingLessonsDto = new DrivingLessonsDto();
        drivingLessonsDto.setId(drivingLesson.getId());
        drivingLessonsDto.setTopic(drivingLesson.getTopic());
        drivingLessonsDto.setStartAt(drivingLesson.getStartAt());
        drivingLessonsDto.setDurationInMinutes(drivingLesson.getDurationInMinutes());
        drivingLessonsDto.setStudentComment(drivingLesson.getStudentComment());
        drivingLessonsDto.setStudentFullName(drivingCard.getStudent().getFullName());
        drivingLessonsDto.setDrivingCardId(drivingCard.getId());
        drivingLessonsDto.setStatus(drivingLesson.getStatus());
        drivingLessonsDto.setStartTime(drivingLesson.getStartTime());
        return drivingLessonsDto;
    }

    public DrivingLesson create(Long drivingCardId, DrivingLessonCreationRequestDto drivingLessonCreationRequestDto) throws DrivingCardNotFoundException {
        DrivingCard drivingCard = drivingCardService.getDrivingCardById(drivingCardId);
        DrivingLesson drivingLesson = new DrivingLesson();
        drivingLesson.setTopic(drivingLessonCreationRequestDto.getTopic());
        drivingLesson.setStartAt(DateUtils.convertDate(drivingLessonCreationRequestDto.getStartAt()));
        drivingLesson.setStartTime(drivingLessonCreationRequestDto.getStartTime());
        drivingLesson.setStatus(drivingLessonCreationRequestDto.getStatus());
        drivingLesson.setDrivingCard(drivingCard);
        return drivingLessonRepository.save(drivingLesson);
    }
    public DrivingLesson getDrivingLessonById(Long id) throws DrivingLessonNotFoundException {
        return drivingLessonRepository.findById(id).orElseThrow(()
                -> new DrivingLessonNotFoundException("Driving Lesson not found", Errors.DRIVING_LESSON_NOT_FOUND));
    }

    public void changeStudentComment(DrivingLesson drivingLesson, String studentComment) {
        drivingLesson.setStudentComment(studentComment);
        drivingLessonRepository.save(drivingLesson);
    }
}
