package NTNU.IDATT1002.utils;

import NTNU.IDATT1002.models.GeoLocation;
import NTNU.IDATT1002.models.Metadata;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


/**
 * Metadata String Formatter provides and interface for formatting
 * metadata into a UI presentable string.
 */
public class MetadataStringFormatter {

    private static List<String> include = Arrays.asList("geolocation",
                                                 "camera",
                                                 "lens",
                                                 "aperture",
                                                 "shutterSpeed",
                                                 "ISO",
                                                 "focalLength",
                                                 "fileType",
                                                 "photoDate",
                                                 "fileSize",
                                                 "fileDimension");

    private static Logger logger = LoggerFactory.getLogger(MetadataStringFormatter.class);

    /**
     * Format given metadata by given delimiter.
     *
     * @param metadata the metadata to format
     * @param delimiter the delimiter separating the fields
     * @return the formatted string
     */
    public static String format(Metadata metadata, String delimiter) {
        Metadata pureMetadata = new Metadata(metadata);
        Stream<Field> fields = Arrays.stream(pureMetadata.getClass().getDeclaredFields());

        StringBuilder metadataString = new StringBuilder();

        fields.filter(field -> include.contains(field.getName()))
                .forEach(field -> {
                    String formattedField = getFormattedField(pureMetadata, delimiter, field);
                    metadataString.append(formattedField);
                });

        return metadataString.toString();
    }

    /**
     * Format given fields name and value.
     * Converts camel case into title case where the first letter
     * in each word is capital and adds given delimiter at the end.
     *
     * @param metadata the metadata holding the field
     * @param delimiter the delimiter separating the fields
     * @param field the field to format
     * @return the formatted field as a string
     */
    private static String getFormattedField(Metadata metadata,
                                            String delimiter,
                                            Field field) {
        field.setAccessible(true);

        String[] fieldNameSplitByUppercase = field.getName().split("(?=\\p{Upper}[a-z])");
        String fieldNameTitle = String.join(" ", fieldNameSplitByUppercase);

        return WordUtils.capitalizeFully(fieldNameTitle) +
                ": " +
                getFieldValue(metadata, field) +
                delimiter;
    }

    /**
     * Format given fields value.
     *
     * @param metadata the metadata holding the field
     * @param field the fields holding the value
     */
    private static String getFieldValue(Metadata metadata, Field field) {
        StringBuilder fieldValueString = new StringBuilder();
        
        try {
            appendFieldValue(metadata, field, fieldValueString);
        } catch (IllegalAccessException e) {
            logger.error("[x] Failed to process field {}", field.getName(), e);
        }

        return fieldValueString.toString();
    }

    /**
     * Append given fields value from given metadata to given string builder.
     *
     * @param metadata the metadata object holding the data
     * @param field the field to get the value from
     * @param fieldValueString the StringBuilder to append to
     * @throws IllegalAccessException if field does not exist or access is denied
     */
    private static void appendFieldValue(Metadata metadata, Field field, StringBuilder fieldValueString)
            throws IllegalAccessException {
        Object fieldValue = field.get(metadata);

        if (fieldValue == null || fieldValue.equals(""))
            fieldValueString.append("No ")
                    .append(field.getName())
                    .append(" found.");
        else if (field.getName().equals("geolocation"))
            fieldValueString.append(getGeoLocationValue(metadata, field));
        else
            fieldValueString.append(fieldValue);
    }

    private static String getGeoLocationValue(Metadata metadata, Field field) throws IllegalAccessException {
        GeoLocation geolocation = (GeoLocation) field.get(metadata);
        return "latitude: " +
                geolocation.getLatitude() +
                ", longitude: " +
                geolocation.getLatitude();
    }

}
