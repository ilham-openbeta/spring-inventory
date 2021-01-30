package com.enigma.api.inventory.services;

import com.enigma.api.inventory.services.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    FileServiceImpl service;

    @Test
    void uploadDownloadTest() throws Exception {
        String input = "text";
        InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        BufferedInputStream bis = new BufferedInputStream(in);
        String filename = service.upload("D:/SAMPAH/test", bis);
        Assertions.assertNotNull(filename);

        OutputStream os = new ByteArrayOutputStream();
        service.download("D:/SAMPAH/test", filename, os);
        String output = os.toString();
        Assertions.assertNotNull(output);
    }
}
