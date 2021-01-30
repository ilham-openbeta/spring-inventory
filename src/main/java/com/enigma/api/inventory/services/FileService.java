package com.enigma.api.inventory.services;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface FileService {
    String upload(String folder, BufferedInputStream in) throws Exception;
    void download(String folder, String filename, OutputStream out) throws IOException;
}
