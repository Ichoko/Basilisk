package com.basilisk.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.TYPE_USE, ElementType.TYPE})//bisa ditempelkan dimana Type berupa objeck, kelas, methhod dll
@Retention(RetentionPolicy.RUNTIME)//dipertahankan selama runtime
public @interface UniqueUsername {
    public Class<?>[] groups() default{};
    public Class<? extends Payload>[] payload() default {};
    public String message();

}
