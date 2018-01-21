package io.garuda.skyworks.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by joshl on 2/1/2018.
 */

public class User implements Serializable{

    String name;
    String contactNumber;
    String email;
    ArrayList<CreditCard> cards;
    ArrayList<Service> services;


    public User(String name, String contactNumber, String email, ArrayList<CreditCard> cards, ArrayList<Service> services) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.cards = cards;
        this.services = services;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public void addService(Service s) {
        this.services.add(s);
    }

    public void removeService(Service s) {
        this.services.remove(s);
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

    public ArrayList<CreditCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CreditCard> cards) {
        this.cards = cards;
    }

    public void removeCard(CreditCard c) {
        this.cards.remove(c);
    }

    public void addCard(CreditCard c) {
        this.cards.add(c);
    }



}
