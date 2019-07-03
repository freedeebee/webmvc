package de.schad.mi.webmvc.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.data.ImageMeta;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${file.upload.directory}")
    private String UPLOADDIR;

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
        Instant date;
        

        Metadata metadata = ImageMetadataReader.readMetadata(fileStream);
        var geodir = metadata.getFirstDirectoryOfType(GpsDirectory.class); 
        latitude = geodir.getGeoLocation().getLatitude();
        longitude = geodir.getGeoLocation().getLongitude();
		Directory exififd0dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class); 
		date = exififd0dir.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL).toInstant();
        
        ImageMeta imageMeta = new ImageMeta(LocalDate.ofInstant(date, ZoneId.of("")),latitude,longitude);
        ImageMetadataReader.readMetadata(fileStream);
        return imageMeta;
    }
    
}