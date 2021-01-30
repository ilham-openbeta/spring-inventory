package com.enigma.api.inventory.models.validations;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStream;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext cvc) {
        Tika tika = new Tika();
        try (InputStream in = file.getInputStream()) {
            String mediaType = tika.detect(in);
            if (mediaType.matches("^image.*")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
