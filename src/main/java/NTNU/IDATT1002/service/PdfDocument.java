package NTNU.IDATT1002.service;

import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Metadata;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


/**
 * Class PdfDocument. Generates a pdf document displaying a given album.
 *
 * @version 1.0 22.03.20
 */
public class PdfDocument implements AlbumDocument {

    /**
     * Height ratio satisfying a 16:9 ratio.
     */
    private final double HEIGHT_RATIO = 5.3;

    private Album album;

    private Document document;

    private String DESTINATION_FILE;

    private String defaultTitle = "Album";

    private static Logger logger = LoggerFactory.getLogger(PdfDocument.class);


    /**
     * Standard fonts.
     */
    private Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

    public PdfDocument(Album album, String DESTINATION_FILE) {
        this.album = album;
        this.DESTINATION_FILE = DESTINATION_FILE;
        this.document = new Document();
    }

    public File getDocument() {
        return new File(DESTINATION_FILE);
    }

    /**
     * Create a new pdf document.
     */
    public void create() {
        try {
            generatePdfDocument();
        } catch (IOException | DocumentException e) {
            logger.error("[x] An error occurred when trying to save pdf", e);
        }
    }

    /**
     * Try to generate a pdf document.
     *
     * @throws IOException
     * @throws DocumentException
     */
    private void generatePdfDocument() throws IOException, DocumentException {
        PdfWriter.getInstance(document, new FileOutputStream(DESTINATION_FILE));
        document.open();

        addHeadlines();
        addContent();

        document.close();
    }

    /**
     * Add default headlines to the document, ie document title, ownership and date.
     *
     * @throws DocumentException
     */
    private void addHeadlines() throws DocumentException {
        Paragraph headline = new Paragraph();

        addEmptyLineTo(headline, 1);

        headline.add(new Paragraph(defaultTitle, headerFont));
        addEmptyLineTo(headline, 1);

        headline.add(new Paragraph(
                "Generated by: " + album.getUser().getUsername() + ", "
                        + new Date(),
                smallFont));
        addEmptyLineTo(headline, 2);

        document.add(headline);
    }

    /**
     * Add main content to the document. This entails album meta and all images.
     *
     * @throws DocumentException
     * @throws IOException
     */
    private void addContent() throws DocumentException, IOException {
        addAlbumMeta();
        addImagesContainer();
    }

    /**
     * Add album meta to the document, such as title, user etc.
     *
     * @throws DocumentException
     */
    private void addAlbumMeta() throws DocumentException {
        Paragraph albumMeta = new Paragraph();
        String albumMetaContent = formatAlbumMeta();
        albumMeta.add(new Paragraph(
                albumMetaContent,
                smallFont));

        addEmptyLineTo(albumMeta, 1);
        document.add(albumMeta);
    }

    /**
     * Add container to contain images and a headline.
     *
     * @throws DocumentException
     * @throws IOException
     */
    private void addImagesContainer() throws DocumentException, IOException {
        Paragraph imagesContainer = new Paragraph();

        imagesContainer.add(new Paragraph("Images:", subFont));
        document.add(imagesContainer);
        addAllImages();
    }

    /**
     * Add all images in the album to the document.
     *
     * @throws IOException
     * @throws DocumentException
     */
    private void addAllImages() throws IOException, DocumentException {
        for (Image image : album.getImages())
            addSingleImage(image);
    }

    /**
     * Add a single image to the document.
     *
     * @param image the image to add.
     * @throws IOException
     * @throws DocumentException
     */
    private void addSingleImage(Image image) throws IOException, DocumentException {
        com.itextpdf.text.Image displayImage = getImageFileFromBytes(image.getRawImage());
        scaleImage(displayImage);

        document.add(displayImage);
        addImageMetaData(image);
    }

    /**
     * Convert an array of bytes to {@link com.itextpdf.text.Image}.
     *
     * @param imageBytes the array of bytes to convert
     * @return the image to display
     * @throws IOException
     * @throws BadElementException
     */
    private com.itextpdf.text.Image getImageFileFromBytes(byte[] imageBytes)
            throws IOException, BadElementException {
        return com.itextpdf.text.Image.getInstance(imageBytes);
    }

    /**
     * Scale given {@link com.itextpdf.text.Image image} to stretch
     * half the width of the page, remaining a 16:9 ratio.
     *
     * @param image the image to scale
     */
    private void scaleImage(com.itextpdf.text.Image image) {
        float documentWidth = PageSize.A4.getWidth() - 2 * PageSize.A4.getBorder();
        float scaledHeight = (float) (documentWidth / 2 * HEIGHT_RATIO);

        image.scaleToFit(documentWidth / 2, scaledHeight);
    }

    /**
     * Add an images metadata to the document.
     *
     * @param image the image holding the metadata
     * @throws DocumentException
     */
    private void addImageMetaData(Image image) throws DocumentException {
        Metadata metadata = image.getMetadata();
        if (metadata != null)
            document.add(new Paragraph(metadata.toString()));
    }

    /**
     * Format the album meta.
     *
     * @return the formatted album
     */
    private String formatAlbumMeta() {
        return new StringBuilder()
                .append("Title: ")
                .append(album.getTitle())
                .append("\n")
                .append("User: ")
                .append(album.getUser().getUsername())
                .append("\n")
                .append("Created at: ")
                .append(album.getCreatedAt())
                .append("\n")
                .append("Description: ")
                .append(album.getDescription())
                .append("\n")
                .append("Tags: ")
                .append(formatTags())
                .toString();
    }

    /**
     * Format the albums tags, separated by a comma.
     *
     * @return the formatted tags
     */
    private String formatTags() {
        StringBuilder tags = new StringBuilder();
        album.getTags().forEach(tag -> tags.append(tag.getName()).append(", "));

        return tags.toString();
    }

    /**
     * Add an empty line to the document.
     *
     * @param paragraph the paragraph to insert an empty line into
     * @param number the number of empty lines desired
     */
    private void addEmptyLineTo(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
