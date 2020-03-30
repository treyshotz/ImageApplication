package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.database.EntityManagerConfig;
import NTNU.IDATT1002.models.GeoLocation;
import NTNU.IDATT1002.models.Histogram;
import NTNU.IDATT1002.repository.GeoLocatioRepository;
import NTNU.IDATT1002.repository.HistorgramRepository;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.GpsDirectory;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;


/**
 * Class MetaDataExtractor. Extracts metadata and geolocation and histogram from it.
 */
public class MetaDataExtractor {

    private GeoLocatioRepository geoLocationRepository;
    private HistorgramRepository historgramRepository;

    public MetaDataExtractor() {
        EntityManager entityManager = EntityManagerConfig.getEntityManager();

        this.historgramRepository = new HistorgramRepository(entityManager);
        this.geoLocationRepository = new GeoLocatioRepository(entityManager);
    }

    /**
     * Returns a string with the GPS position
     *
     * @return
     * @throws ImageProcessingException
     * @throws IOException
     * @throws MetadataException
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
            longitude = pos[0];

            geoLocation.setLatitude(latitude);
            geoLocation.setLongitude(longitude);
        }
        catch (NullPointerException | ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
        return geoLocation;
    }

    /**
     * TODO: Decide what data to store.
     *
     * @param file
     * @return
     */
    public Histogram getHistogram(File file) {
//        Metadata metadata = null;
//
//        try {
//            metadata = ImageMetadataReader.readMetadata(file);
//        } catch (IOException | ImageProcessingException e) {
//            e.printStackTrace();
//        }
//
//        StringBuilder data = new StringBuilder();
//        assert metadata != null;
//        for(Directory d : metadata.getDirectories()) {
//            for (Tag t : d.getTags()) {
//                data.append(t.toString()).append(" | ");
//            }
//        }
//        histogram.setData(data.toString());


        Histogram histogram = new Histogram();
        histogram.setData("INSERT DATA HERE");

        return histogram;
    }

}
