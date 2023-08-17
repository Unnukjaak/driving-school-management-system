package ee.drivingschool.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DrivingLessonNotFoundException extends Exception{
    private Errors errorCode;
    private String message;

    public DrivingLessonNotFoundException(String message, Errors errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
