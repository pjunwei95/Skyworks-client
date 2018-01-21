package io.garuda.skyworks.Models;

import java.io.Serializable;

/**
 * Created by joshl on 2/1/2018.
 */

public class CreditCard implements Serializable{

    String cardNum;
    int cardType;
    String name;
    String validity;
    String CVV;

    public CreditCard(String cardNum, int cardType, String name, String validity, String CVV) {
        this.cardNum = cardNum;
        this.cardType = cardType;
        this.name = name;
        this.validity = validity;
        this.CVV = CVV;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getConcatString() {
        return cardType + " " + cardNum;
    }
}
