package io.garuda.skyworks.Models;

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
    private String userId;
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
    private ArrayList<LatLng> location;

    //new constructor
    public Service(String userId, String specialRequest, String status, String date, String customerName, String email, String contact, String type, String operatorID, int i, String time, String creditCardID, String quotation, int rating, ArrayList<LatLng> location) {
        this.userId = userId;
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

    public String getCreditCardID() {
        return creditCardID;
    }

    public void setCreditCardID(String creditCardID) {
        this.creditCardID = creditCardID;
    }

    private CreditCard paymentMethod;
    private Provider provider;

    public Service(String status, String type, String customerName, String contact, String email, String date, String time, String specialRequest, Provider provider, CreditCard paymentMethod, String quotation, int rating) {
        this.status = status;
        this.type = type;
        this.customerName = customerName;
        this.contact = contact;
        this.email = email;
        this.date = date;
        this.time = time;
        this.specialRequest = specialRequest;
        this.provider = provider;
        this.paymentMethod = paymentMethod;
        this.quotation = quotation;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public CreditCard getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(CreditCard paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
