package ee.drivingschool.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EstonianIdCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EstonianIdCode {
    String message() default "Invalid Estonian ID code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
