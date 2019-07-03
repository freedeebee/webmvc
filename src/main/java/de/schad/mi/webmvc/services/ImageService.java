package de.schad.mi.webmvc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.drew.imaging.ImageProcessingException;
import de.schad.mi.webmvc.model.data.ImageMeta;


public interface ImageService {

    String store(InputStream input, String filename);

    // could be replaced
    ImageMeta getMetaData(FileInputStream fileStream) throws ImageProcessingException, IOException;
}