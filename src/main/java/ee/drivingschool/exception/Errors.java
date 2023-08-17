package ee.drivingschool.exception;

public enum Errors {
    COURSE_NOT_FOUND(1),
    STUDENT_NOT_FOUND(2),
    TEACHER_NOT_FOUND(3),
    DRIVING_CARD_NOT_FOUND(4),
    DRIVING_LESSON_NOT_FOUND(5);

    private final int value;

    Errors(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
