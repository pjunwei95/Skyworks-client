package io.garuda.skyworks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Joshua on 07/02/2018.
 */

public class SerializableLatLng implements Serializable {

    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("lng")
    @Expose
    public double lng;

    public SerializableLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
