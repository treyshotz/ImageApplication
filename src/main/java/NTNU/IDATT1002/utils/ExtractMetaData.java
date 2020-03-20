package NTNU.IDATT1002.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.*;
import com.drew.metadata.jpeg.JpegDirectory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ExtractMetaData {

    private final File image;

    public ExtractMetaData(File image) {
        this.image = image;
    }

    /**
     * Extracts all data possible for a image
     *
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     */
    public String getAll() throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(this.image);
        String text = "";
        for (Directory d : metadata.getDirectories()) {
            for (Tag t : d.getTags()) {
                text += t.toString() + " | ";
            }
        }
        text += "\n";
        return text;
    }

    /**
     * Returns a string with the image dimension
     *
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     * @throws MetadataException
     */
    private String getDimension() throws ImageProcessingException, IOException, MetadataException {
        try {
            String dimension = "Dimension: ";

            Metadata metadata = ImageMetadataReader.readMetadata(this.image);

            JpegDirectory jpeg = metadata.getFirstDirectoryOfType(JpegDirectory.class);
            dimension += jpeg.getImageHeight();
            dimension += "x";
            dimension += jpeg.getImageWidth();
            return dimension;
        } catch (NullPointerException e) {

        }
        return "No dimension found";
    }

    /**
     * Returns a string with the GPS position
     *
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     * @throws MetadataException
     */
    private String getGPS() throws ImageProcessingException, IOException, MetadataException {
        try {
            String gps = "";

            Metadata metadata = ImageMetadataReader.readMetadata(this.image);

            GpsDirectory gpspos = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            gps += "GPS position: " + gpspos.getGeoLocation();

            return gps;
        } catch (NullPointerException e) {

        }
        return "No GPS information found";
    }

    /**
     * Returns all predetermined metadata as an ArrayList
     * @return
     * @throws IOException
     * @throws MetadataException
     * @throws ImageProcessingException
     */
    public ArrayList<String> getNecessary() throws IOException, MetadataException, ImageProcessingException {
        ArrayList<String> information = new ArrayList<String>();

        information.add(getGPS());
        information.add(getDimension());

        return information;
    }
}