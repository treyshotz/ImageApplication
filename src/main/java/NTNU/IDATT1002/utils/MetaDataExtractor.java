package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.models.GeoLocation;
import NTNU.IDATT1002.models.Histogram;
import NTNU.IDATT1002.models.Image;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.*;
import com.drew.metadata.jpeg.JpegDirectory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MetaDataExtractor {

    /**
     * Returns a string with the GPS position
     *
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     * @throws MetadataException
     */
    private static GeoLocation getGPS(File file) throws ImageProcessingException, IOException, MetadataException {
        String gps = "";
        String latitude = "";
        String longitude = "";
        GeoLocation geoLocation = new GeoLocation("0", "0");

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            GpsDirectory gpspos = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            gps += gpspos.getGeoLocation();
            String[] pos = gps.split(", ");
            latitude = pos[0];
            longitude = pos[0];

            geoLocation.setLatitude(latitude);
            geoLocation.setLongitude(longitude);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return geoLocation;
    }

    public static Histogram getHistorgram(File file) throws ImageProcessingException, IOException {
        String text = "";
        Metadata metadata = ImageMetadataReader.readMetadata(file);
        Histogram histogram = new Histogram();

        for(Directory d : metadata.getDirectories()) {
            for (Tag t : d.getTags()) {
                text += t.toString() + " | ";
            }
        }
        histogram.setData(text);
        return histogram;
    }

    public static NTNU.IDATT1002.models.Metadata assembleMetaData(File file, Image image) {

        NTNU.IDATT1002.models.Metadata metadata = new NTNU.IDATT1002.models.Metadata();
        try {
            metadata.setImage(image);
            metadata.setGeoLocation(getGPS(file));
            metadata.setHistogram(getHistorgram(file));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return metadata;
    }

}
