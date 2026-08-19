package com.example.demo.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "Fayl formatı və ya həcmi düzgün deyil";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    long maxSizeInMb() default 5;
    String[] allowedTypes() default {"image/jpeg", "image/png", "image/webp", "image/svg+xml"};
}
