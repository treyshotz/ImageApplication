package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.database.EntityManagerConfig;
import NTNU.IDATT1002.models.GeoLocation;
import NTNU.IDATT1002.repository.GeoLocatioRepository;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

import com.drew.metadata.file.FileSystemDirectory;
import com.drew.metadata.file.FileTypeDirectory;
import com.drew.metadata.iptc.IptcDirectory;

import com.drew.metadata.jpeg.JpegDirectory;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;


/**
 * Class MetaDataExtractor. Extracts metadata and geolocation and histogram from it.
 */
public class MetaDataExtractor {

    private GeoLocatioRepository geoLocationRepository;

    public MetaDataExtractor() {
    }

    /**
     * Returns a string with the GPS position
     * @return the gelocation of the file
     */
    public GeoLocation getGeoLocation(File file) {
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
            longitude = pos[1];

            geoLocation.setLatitude(latitude);
            geoLocation.setLongitude(longitude);
        }
        catch (NullPointerException | ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
        return geoLocation;
    }

    /**
     * Gets the camera Make and Model of the camera used to take the photo
     * @param file that will be checked for Camera make
     * @return empty string if nothings found
     */
    public String getCamera(File file) {
        String cameraInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Make") || tag.toString().contains("Model")) {
                    cameraInformation += tag.toString() + ",";
                }
            }
            if(!(cameraInformation.isBlank()))
                cameraInformation = cleanUpTags(cameraInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return cameraInformation;
    }

    /**
     * Method for getting lens iformation
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getLens(File file) {
        String lensInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Lens")) {
                    lensInformation += tag.toString() + ",";
                }
            }
            if(!(lensInformation.isBlank()))
                lensInformation = cleanUpTags(lensInformation, directory);
        }
        catch (IOException | ImageProcessingException | NullPointerException e) {
        e.printStackTrace();
        }
        return lensInformation;
    }

    /**
     * Method for getting aperture information
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getAperture(File file) {
        String apertureInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Aperture")) {
                    apertureInformation += tag.toString() + ",";
                }
            }
            if(!(apertureInformation.isBlank()))
                apertureInformation = cleanUpTags(apertureInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return apertureInformation;
    }

    /**
     * Method for getting shutterspeed information
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getShutterSpeed(File file) {
        String shutterSpeedInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Shutter")) {
                    shutterSpeedInformation += tag.toString() + ",";
                }
            }
            if(!(shutterSpeedInformation.isBlank()))
                shutterSpeedInformation = cleanUpTags(shutterSpeedInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return shutterSpeedInformation;
    }

    /**
     * Method for getting iso information
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getIso(File file) {
        String isoInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("ISO")) {
                    isoInformation += tag.toString() + ",";
                }
            }
            if(!(isoInformation.isBlank()))
                isoInformation = cleanUpTags(isoInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return isoInformation;
    }

    /**
     * Method for getting focal length information
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getFocalLength(File file) {
        String focalLengthInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Focal Length")) {
                    focalLengthInformation += tag.toString() + ",";
                    break;
                }
            }
            if(!(focalLengthInformation.isBlank()))
                focalLengthInformation = cleanUpTags(focalLengthInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return focalLengthInformation;
    }

    /**
     * Method for getting file type information
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getFileType(File file) {
        String fileTypeInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(FileTypeDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("File Type Name")) {
                    fileTypeInformation += tag.toString() + ",";
                    break;
                }
            }
            if(!(fileTypeInformation.isBlank()))
                fileTypeInformation = cleanUpTags(fileTypeInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return fileTypeInformation;
    }

    /**
     * Method for getting date information
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getPhotoDate(File file) {
        String dateInformation = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(IptcDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Date Created")) {
                    dateInformation += tag.toString() + ",";
                    break;
                }
            }
            for(Tag tag : directory.getTags()) {
               if(tag.toString().contains("Time Created")) {
                   dateInformation += tag.toString() + ",";
                   break;
               }
            }
            if(!(dateInformation.isBlank()))
                dateInformation = cleanUpTags(dateInformation, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return dateInformation;
    }

    /**
     * Method for getting file size
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getFileSize(File file) {
        String fileSize = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(FileSystemDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Size")) {
                    fileSize += tag.toString() + ",";
                }
            }
            if(!(fileSize.isBlank()))
                fileSize = cleanUpTags(fileSize, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return fileSize;
    }


    /**
     * Method for getting file dimension
     * @param file that will be checked
     * @return empty string if nothing is found
     */
    public String getFileDimension(File file) {
        String fileDimension = "";
        Directory directory;

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            directory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
            for(Tag tag : directory.getTags()) {
                if(tag.toString().contains("Height") || tag.toString().contains("Width")) {
                    fileDimension += tag.toString() + ",";
                }
            }
            if(!(fileDimension.isBlank()))
                fileDimension = cleanUpTags(fileDimension, directory);
        } catch (IOException | ImageProcessingException | NullPointerException e) {
            e.printStackTrace();
        }
        return fileDimension;
    }
    
    public void setMetadata(NTNU.IDATT1002.models.Metadata metadata, File file) {
        metadata.setCamera(getCamera(file));
        metadata.setLens(getLens(file));
        metadata.setAperture(getAperture(file));
        metadata.setShutterSpeed(getShutterSpeed(file));
        //metadata.setISO(getIso(file));
        metadata.setFocalLength(getFocalLength(file));
        metadata.setFileType(getFileType(file));
        metadata.setPhotoDate(getPhotoDate(file));
        metadata.setFileSize(getFileSize(file));
        metadata.setFileDimension(getFileDimension(file));
    }

    /**
     * Cleans up the tags on a string
     * @param textToClean string that will be cleaned
     * @param directoryToRemove directory that will be removed from string
     * @return cleaned string
     */
    private String cleanUpTags(String textToClean, Directory directoryToRemove) {
        String removingText = directoryToRemove.getName();
        textToClean = textToClean.replace(removingText, "");
        textToClean = textToClean.replace("[", "");
        textToClean = textToClean.replace("]", "");
        textToClean = textToClean.replace(" - ", ": ");
        textToClean = textToClean.substring(0, textToClean.length()-1);
        return textToClean;
    }
}
