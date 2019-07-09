package de.schad.mi.webmvc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;

import com.drew.metadata.Metadata;

import com.drew.metadata.exif.GpsDirectory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.data.ImageMeta;


/**
 * Image ServiceImpl ist the implementation of ImageService Interface
 * 
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${file.upload.directory}")
    private String UPLOADDIR;

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Override
    public String store(InputStream input, String filename) {

        String status;
        String path="";
        try {
            Path zielpfad = Paths.get(UPLOADDIR, filename);
            Files.copy(input, zielpfad);
            path = zielpfad.toString();
            status = "ok";
        } catch (IOException exc) {
            status = exc.getMessage();
        }
        return String.format("Datei %s , Status %s", path, status);
    }

    @Override
    public ImageMeta getMetaData(FileInputStream fileStream) throws ImageProcessingException, IOException {

        double latitude = 0;
        double longitude = 0;

        Metadata metadata = ImageMetadataReader.readMetadata(fileStream);
        var geodir = metadata.getFirstDirectoryOfType(GpsDirectory.class);
        latitude = geodir.getGeoLocation().getLatitude();
        longitude = geodir.getGeoLocation().getLongitude();

        logger.info("Lat: {}", latitude);
        logger.info("Long: {}", longitude);

        // Directory exififd0dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        // if (exififd0dir == null) {
        //     date = null;
        // } else {
        //     date = exififd0dir.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL).toInstant();
        // }


        ImageMeta imageMeta = new ImageMeta(latitude,longitude);
        return imageMeta;
    }

}