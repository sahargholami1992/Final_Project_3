package com.example.final_project_3.utill;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.IOException;

public class ImageValidator implements ConstraintValidator<ValidImage, byte[]> {



    @Override
    public boolean isValid(byte[] imageData, ConstraintValidatorContext context) {
        if (imageData == null || imageData.length == 0) {
            return false;
        }

        // Check image size
        if (imageData.length > 300 * 1024) { // 300 KB
            return false;
        }

        // Check image format
        try {

            return isJpgFormat(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isJpgFormat(byte[] imageData) throws IOException {
        if (imageData.length < 2) {
            return false;
        }
        return (imageData[0] & 0xFF) == 0xFF && (imageData[1] & 0xFF) == 0xD8;
    }
}