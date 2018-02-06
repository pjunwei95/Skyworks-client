package io.garuda.skyworks.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider implements Serializable{

    @SerializedName("gallery")
    @Expose
    private List<String> gallery;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("about_us")
    @Expose
    private String overview;
    @SerializedName("license_number")
    @Expose
    private String licenseNumber;
    @SerializedName("logo_path")
    @Expose
    private String posterPath;



    public Provider(String name, String overview, String posterPath, String licenseNumber, List<String> gallery) {
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.licenseNumber = licenseNumber;
        this.gallery = gallery;
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

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        gallery = gallery;
    }
}
