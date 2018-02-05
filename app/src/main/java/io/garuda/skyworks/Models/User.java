package io.garuda.skyworks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshl on 2/1/2018.
 */

public class User implements Serializable{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("card_ids")
    @Expose
    private List<String> cardIds = null;
    @SerializedName("service_ids")
    @Expose
    private List<String> serviceIds = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }

    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public void addServiceIds(String serviceId) {
        this.serviceIds.add(serviceId);
    }

    public void ServiceIds(String serviceId) {
        this.serviceIds.add(serviceId);
    }

    public void removeCardId(String c) {
        this.cardIds.remove(c);
    }

    public void addCardIds(String c) {
        this.cardIds.add(c);
    }



}
