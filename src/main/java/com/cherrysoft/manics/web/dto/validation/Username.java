package com.cherrysoft.manics.web.dto.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.util.Objects.isNull;

@Target({ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Size(
    min = 5,
    max = 20,
    message = "Username must be between {min} and {max} chars."
)
public @interface Username {
  String message() default "Username can only have letters and numbers.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

class UsernameValidator implements ConstraintValidator<Username, String> {
  private static final String VALID_USERNAME_PATTERN = "^[a-zA-Z0-9]*$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }
    return value.matches(VALID_USERNAME_PATTERN);
  }

}
