package io.garuda.skyworks.Models;

import java.io.Serializable;

/**
 * Created by joshl on 1/1/2018.
 */

public class Service implements Serializable {

    private String status;
    private String type;
    private String customerName;
    private String contact;
    private String email;
    private String date;
    private String time;
    private String specialRequest;
    private Provider provider;
    private CreditCard paymentMethod;
    private String quotation;
    private int rating;

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
