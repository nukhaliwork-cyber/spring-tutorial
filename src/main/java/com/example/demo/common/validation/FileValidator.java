package com.example.demo.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private long maxSizeBytes;
    private String[] allowedTypes;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.maxSizeBytes = constraintAnnotation.maxSizeInMb() * 1024 * 1024;
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Optional file pass
        }

        // Check file size
        if (file.getSize() > maxSizeBytes) {
            context.disableDefaultConstraintViolation();
            long maxMb = maxSizeBytes / (1024 * 1024);
            context.buildConstraintViolationWithTemplate("Faylın həcmi icazə verilən həddi aşır (Maksimum " + maxMb + "MB)")
                    .addConstraintViolation();
            return false;
        }

        // Check content type
        String contentType = file.getContentType();
        boolean isAllowed = contentType != null && Arrays.asList(allowedTypes).contains(contentType);
        if (!isAllowed) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("İcazə verilməyən fayl formatı: " + (contentType != null ? contentType : "naməlum"))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
