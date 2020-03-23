package NTNU.IDATT1002.controllers;

import java.io.File;
import java.util.List;

/**
 * Class for storing temporary variables between controllers,
 * when tha stage changes
 * @version 1.0 22.03.2020
 */
public class DataExchange {
    private String searchField;
    private List<File> uploadedFiles;
    private Long albumId;
    private String chosenImg;

    public DataExchange(){
        searchField = "";
    }
    public List<File> getUploadedFiles() {
        return uploadedFiles;
    }

    public String getSearchField() {
        return searchField;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public String getChosenImg() {
        return chosenImg;
    }

    public void setUploadedFiles(List<File> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public void setChosenImg(String chosenImg) {
        this.chosenImg = chosenImg;
    }
}
