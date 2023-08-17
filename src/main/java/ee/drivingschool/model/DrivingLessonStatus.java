package ee.drivingschool.model;

public enum DrivingLessonStatus {

    PENDING(0),
    BOOKED(1),
    PASSED(2),
    CANCELED(3);

    private final int value;

    DrivingLessonStatus(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
