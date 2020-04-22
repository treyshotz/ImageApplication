package NTNU.IDATT1002.service;

import java.io.File;

/**
 * Album Document Interface. Defines operations for getting and creating documents.
 *
 * @version 1.0 30.03.20
 */
public interface AlbumDocument {

    /**
     * Get the created document.
     *
     * @return the document as a file
     */
    File getDocument();

    /**
     * Create the document.
     */
    void create();

}
