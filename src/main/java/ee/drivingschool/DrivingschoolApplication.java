package ee.drivingschool;

import ee.drivingschool.exception.DrivingCardNotFoundException;
import ee.drivingschool.model.DrivingCard;
import ee.drivingschool.repository.*;
import ee.drivingschool.service.DrivingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DrivingschoolApplication implements CommandLineRunner {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DrivingLessonRepository drivingLessonRepository;

	@Autowired
	private DrivingCardService drivingCardService;

	public DrivingschoolApplication(CourseRepository courseRepository,
									StudentRepository studentRepository,
									TeacherRepository teacherRepository) {
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DrivingschoolApplication.class, args);
	}

//	@Transactional
	@Override
	public void run(String... args) throws Exception {


//		DrivingCard drivingCard = drivingCardService.getDrivingCardById(2L);
//
//
//		DrivingLesson drivingLesson1 = new DrivingLesson (drivingCard, "S2", LocalDate.now(), 45, DrivingLessonStatus.PASSED);
//		DrivingLesson drivingLesson2 = new DrivingLesson (drivingCard, "S3", LocalDate.now(), 130, DrivingLessonStatus.BOOKED);

//		drivingLesson1.setStudentComment("Want to cancel today lesson");
//
//		drivingLessonRepository.save(drivingLesson1);
//		drivingLessonRepository.save(drivingLesson2);

//		User User1 = new User (1L, "user1@gmail.com","$2a$12$87QIQUw8bH5nTBhCRvt0qOzXfKHhzUPObp1ZDsBmBpA/i5KOrybW2", true);
//		User User2 = new User (2L, "user2@gmail.com","$2a$12$87QIQUw8bH5nTBhCRvt0qOzXfKHhzUPObp1ZDsBmBpA/i5KOrybW2", true);
//		User admin1 = new User(3L, "admin1@gmail.com","$2a$12$87QIQUw8bH5nTBhCRvt0qOzXfKHhzUPObp1ZDsBmBpA/i5KOrybW2", true);
//
//		userRepository.save(User1);
//		userRepository.save(User2);
//		userRepository.save(admin1);

//		Teacher teacher1 = createTeacher("Jason", "Bourne", "+37254348984", "Tuukri 15-17",
//				"jason@gmail.com");
//		Teacher teacher2 = createTeacher("Mark", "Peterson", "+37254758990", "Laagri 15-17",
//				"mark@gmail.com");
//
//		Course course1 = createCourse(teacher1,"50-T-A-23-01", "B-category", LocalDate.now(), LocalDate.now());
//		Course course2 = createCourse(teacher2,"50-T-A-23-02", "A-category", LocalDate.now(), LocalDate.now());
//
//
//		Student student1 = createStudent(course1, "Mark", "Sillan", "384567846354",
//				"+37234758763", "Gonsiori 12", "mark@gmail.com");
//		Student student2 = createStudent(course1, "Billy", "Bob", "384561146399",
//				"+37234758743", "Mustamäe tee 121-15", "billy@gmail.com");
//
//
//		Teacher teacher = teacherRepository.findTeacherById(21L).get(0);
//		System.out.println("Teacher1:" + teacher);
	}
	
}
