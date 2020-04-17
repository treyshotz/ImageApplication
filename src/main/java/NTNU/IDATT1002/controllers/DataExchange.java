package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.Config;
import com.google.maps.GeoApiContext;
import javafx.application.HostServices;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;

/**
 * Class for storing temporary variables between controllers,
 * when the scene changes
 * @version 1.0 22.03.2020
 */
public class DataExchange {

    private String apiKey;
    public HostServices hostServices;
    private GeoApiContext geoApiContext;
    private String searchField;
    private List<File> uploadedFiles;
    private Long chosenAlbumId;
    private Long chosenImg;
    private final String defaultFont;

    public DataExchange(){
        searchField = "";
        defaultFont = "System";
        apiKey = Config.getGoogleApiKey();
        geoApiContext = new GeoApiContext.Builder()
                .apiKey(getApiKey())
                .build();
    }

    public EntityManager getEntityManager() {
        return Config.createEntityManager();
    }

    public String getApiKey() {
        return apiKey;
    }

    public HostServices getHostServices() {
        return hostServices;
    }

    public GeoApiContext getGeoApiContext() {
        return geoApiContext;
    }

    public List<File> getUploadedFiles() {
        return uploadedFiles;
    }

    public String getSearchField() {
        return searchField;
    }

    public Long getChosenAlbumId() {
        return chosenAlbumId;
    }

    public Long getChosenImg() {
        return chosenImg;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    public void setUploadedFiles(List<File> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public void setChosenAlbumId(Long chosenAlbumId) {
        this.chosenAlbumId = chosenAlbumId;
    }

    public void setChosenImg(Long chosenImg) {
        this.chosenImg = chosenImg;
    }

    public String getDefaultFont() {
        return defaultFont;
    }
}

