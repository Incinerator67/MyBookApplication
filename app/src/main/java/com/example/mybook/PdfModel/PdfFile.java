package com.example.mybook.PdfModel;

public class PdfFile {
    private String fileName;
    private String filePath;
    private boolean favorites;
    private boolean WantToRead;
    public PdfFile(String fileName, String filePath, boolean favorites, boolean WantToRead) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.favorites= favorites;
        this.WantToRead= WantToRead;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isFavorites() {
        return favorites;
    }

    public void setFavorites(boolean favorites) {
        this.favorites = favorites;
    }

    public boolean isWantToRead() {
        return WantToRead;
    }

    public void setWantToRead(boolean wantToRead) {
        WantToRead = wantToRead;
    }
}