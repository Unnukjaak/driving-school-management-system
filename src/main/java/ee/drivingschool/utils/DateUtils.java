package ee.drivingschool.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate convertDate(String date) {
        return LocalDate.parse(date, formatter);
    }
    public static String convertLocalDateToString(LocalDate date) {
        return date.format(formatter);
    }
}
