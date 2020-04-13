package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.database.EntityManagerConfig;
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

    private EntityManager entityManager;
    public HostServices hostServices;
    private String searchField;
    private List<File> uploadedFiles;
    private Long chosenAlbumId;
    private Long chosenImg;

    public DataExchange(){
        searchField = "";
    }

    public EntityManager getEntityManager() {
        return EntityManagerConfig.getEntityManager();
    }

    public HostServices getHostServices() {
        return hostServices;
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

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
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

}

