package ee.drivingschool.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstonianIdCodeValidator implements ConstraintValidator<EstonianIdCode, String> {

    @Override
    public void initialize(EstonianIdCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() != 11) {
            return false;
        }
        if (!value.matches("\\d+")) {
            return false;
        }
        int[] coefficients = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Integer.parseInt(String.valueOf(value.charAt(i))) * coefficients[i];
        }
        int remainder = sum % 11;
        int checkDigit = remainder != 10 ? remainder : 0;

        return checkDigit == Integer.parseInt(String.valueOf(value.charAt(10)));
    }
}
