package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.Config;
import com.google.maps.GeoApiContext;
import javafx.application.HostServices;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.ArrayList;
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
    private ArrayList<String> FXMLHistory;

    public DataExchange(){
        FXMLHistory = new ArrayList<>();
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


    /**
     * Method for adding new page to previousFXML list.
     * Will not add if the last element of the list is
     * the same as the page loaded.
     *
     * @param FXML new fxml page loaded
     */
    public void newPage(String FXML){
        if (FXMLHistory.size() == 0) {
            FXMLHistory.add(FXML);
        }
        else if (!FXMLHistory.get(FXMLHistory.size()-1).equals(FXML)){
            FXMLHistory.add(FXML);
        }
    }

    /**
     * Method for going back. Checks if there is a previous page
     * and makes it the current one
     * @return previous page if it exists or null if not
     */
    public String previousPage(){
        if (FXMLHistory.size() > 1){
            FXMLHistory.remove(FXMLHistory.size()-1); //Removes the current page
            return FXMLHistory.get(FXMLHistory.size()-1); //Returns the new current page
        }
        return null;
    }

    /**
     * Method that empties the array of visited pages.
     * This method is called when logout button is pressed.
     */
    public void emptyPageLog(){
        FXMLHistory = new ArrayList<>();
    }
}

