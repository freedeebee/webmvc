package de.schad.mi.webmvc.services;

import java.io.InputStream;

public interface ImageService {

    String store(InputStream input, String filename);
}