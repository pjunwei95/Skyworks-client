package io.garuda.skyworks.Models;

import android.graphics.Point;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joshl on 1/1/2018.
 */

public class Service implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String specialRequest;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerEmail")
    @Expose
    private String email;
    @SerializedName("customerPhone")
    @Expose
    private String contact;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("operatorID")
    @Expose
    private String operatorID;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("creditCardID")
    @Expose
    private String creditCardID;
    @SerializedName("quotation")
    @Expose
    private String quotation;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("location")
    @Expose
    private ArrayList<SerializableLatLng> location;


    public Service(String id, String specialRequest, String status, String date, String customerName, String email, String contact, String type, String operatorID, String time, String creditCardID, String quotation, int rating, ArrayList<SerializableLatLng> location) {
        this.id = id;
        this.specialRequest = specialRequest;
        this.status = status;
        this.date = date;
        this.customerName = customerName;
        this.email = email;
        this.contact = contact;
        this.type = type;
        this.operatorID = operatorID;
        this.time = time;
        this.creditCardID = creditCardID;
        this.quotation = quotation;
        this.rating = rating;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreditCardID() {
        return creditCardID;
    }

    public void setCreditCardID(String creditCardID) {
        this.creditCardID = creditCardID;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<SerializableLatLng> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<SerializableLatLng> location) {
        this.location = location;
    }

}
