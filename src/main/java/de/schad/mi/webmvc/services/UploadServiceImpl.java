package de.schad.mi.webmvc.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${file.upload.directory}")
    private String UPLOADDIR;

    @Override
    public String store(InputStream input, String filename) {

        String status;

        try {
            Path zielpfad = Paths.get(UPLOADDIR, filename);
            Files.copy(input, zielpfad);
            status = "ok";
        } catch (IOException exc) {
            status = exc.getMessage();
        }
        return String.format("Datei %s, Status %s", filename, status);
    }

    
}