package io.garuda.skyworks.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by joshl on 2/1/2018.
 */

public class CreditCard implements Serializable{


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("card_num")
    @Expose
    private String cardNum;
    @SerializedName("card_type")
    @Expose
    private String cardType;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("CVV")
    @Expose
    private String cVV;
    @SerializedName("name")
    @Expose
    private String name;


    public CreditCard(String id, String cardNum, String ownerId, String validity, String cVV, String name, String cardType) {
        this.id = id;
        this.cardNum = cardNum;
        this.ownerId = ownerId;
        this.validity = validity;
        this.cVV = cVV;
        this.name = name;
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getcVV() {
        return cVV;
    }

    public void setcVV(String cVV) {
        this.cVV = cVV;
    }

    public String getConcatString() {
        return cardType + " " + cardNum;
    }
}
