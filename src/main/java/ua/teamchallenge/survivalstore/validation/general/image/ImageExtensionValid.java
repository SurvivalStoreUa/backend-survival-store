package ua.teamchallenge.survivalstore.validation.general.image;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Constraint(validatedBy = ImageExtensionValidValidator.class)
@Target({PARAMETER, METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageExtensionValid {
    String message() default "Image only allows file types of .png, .jpg, .jpeg";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
