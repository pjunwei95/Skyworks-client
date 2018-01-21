package io.garuda.skyworks.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Provider implements Serializable{

    private String name;
    private String overview;
    private String posterPath;
    private String licenseNumber;
    private ArrayList<String> Gallery;

    public Provider(String name, String overview, String posterPath, String licenseNumber, ArrayList<String> gallery) {
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.licenseNumber = licenseNumber;
        Gallery = gallery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public ArrayList<String> getGallery() {
        return Gallery;
    }

    public void setGallery(ArrayList<String> gallery) {
        Gallery = gallery;
    }
}
