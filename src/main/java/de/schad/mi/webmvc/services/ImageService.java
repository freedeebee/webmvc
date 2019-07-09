package de.schad.mi.webmvc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.drew.imaging.ImageProcessingException;
import de.schad.mi.webmvc.model.data.ImageMeta;

/**
 * ImageService Interface show all Methods regarding to handle with Images
 */
public interface ImageService {

    /**
     * store method to save a Image
     * 
     * @param input InputStream for Image
     * @param filename name of file as String
     * @return a String of ("Datei %s, Status %s")
     */
    String store(InputStream input, String filename);

    // could be replaced
    /**
     * getMetaData method will return ImageMeta of a picture
     * 
     * @param fileStream of the Image
     * @return ImageMeta (latitude,longitude)
     * @throws ImageProcessingException
     * @throws IOException
     */
    ImageMeta getMetaData(FileInputStream fileStream) throws ImageProcessingException, IOException;
}