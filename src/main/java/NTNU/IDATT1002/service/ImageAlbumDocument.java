package NTNU.IDATT1002.service;

import java.io.File;

/**
 * Image Album Document Interface. Defines operations for getting and creating documents.
 *
 * @author Eirik Steira
 * @version 1.0 30.03.20
 */
public interface ImageAlbumDocument {

    File getDocument();

    void createDocument();

}
