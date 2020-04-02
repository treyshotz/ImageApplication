package NTNU.IDATT1002.service;

import java.io.File;

/**
 * Album Document Interface. Defines operations for getting and creating documents.
 *
 * @author Eirik Steira
 * @version 1.0 30.03.20
 */
public interface AlbumDocument {

    File getDocument();

    void create();

}
