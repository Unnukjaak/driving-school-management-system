package ee.drivingschool.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseNotFoundException extends Exception {

    private Errors errorCode;
    private String message;

    public CourseNotFoundException(String message, Errors errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
