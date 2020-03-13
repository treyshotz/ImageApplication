package utils;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.SocketHandler;

public class MultiplePhotos {


    private final ArrayList<File> images;

    public MultiplePhotos(ArrayList<File> images) {
        this.images = images;
    }

    private ArrayList<String> extractAll() throws ImageProcessingException, MetadataException, IOException {
        ArrayList<String> data = new ArrayList<String>();

        for(File i : this.images) {
            ExtractMetaData e = new ExtractMetaData(i);
            String n = e.getAll().toString();
            data.add(n);
        }
        return data;
    }
}
