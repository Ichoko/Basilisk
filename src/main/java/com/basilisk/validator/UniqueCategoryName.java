package com.basilisk.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = UniqueCategoryNameValidator.class)
@Target({ElementType.TYPE})//bisa ditempelkan dimana Type berupa objeck, kelas, methhod dll
@Retention(RetentionPolicy.RUNTIME)//dipertahankan selama runtime
public @interface UniqueCategoryName {
    public String message();
    public Class<?>[] groups() default{};
    public Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})//bisa ditempelkan dimana Type berupa objeck, kelas, methhod dll
    @Retention(RetentionPolicy.RUNTIME)//dipertahankan selama runtime
    @interface List{
        UniqueCategoryName[] value();
    }
}
