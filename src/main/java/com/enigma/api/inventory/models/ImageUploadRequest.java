package com.enigma.api.inventory.models;

import com.enigma.api.inventory.models.validations.ImageFile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageUploadRequest {

    @ImageFile
    private MultipartFile file;
}
