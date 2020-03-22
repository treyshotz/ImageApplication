package NTNU.IDATT1002.controllers;

import java.io.File;
import java.util.List;

public class DataExchange {
    private String searchField;
    private List<File> uploadedFiles;

    public DataExchange(){
        searchField = "";
    }
    public List<File> getUploadedFiles() {
        return uploadedFiles;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setUploadedFiles(List<File> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }
}
