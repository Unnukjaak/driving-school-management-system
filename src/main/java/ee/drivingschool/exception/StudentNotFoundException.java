package ee.drivingschool.exception;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentNotFoundException extends Exception {

    private Errors errorCode;
    private String message;

    public StudentNotFoundException(String message, Errors errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
