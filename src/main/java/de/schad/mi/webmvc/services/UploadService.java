package de.schad.mi.webmvc.services;

import java.io.InputStream;

public interface UploadService {

    public String store(InputStream input, String filename);
}