package io.garuda.skyworks.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.garuda.skyworks.Activities.MyWallet;
import io.garuda.skyworks.Activities.ProviderDetail;
import io.garuda.skyworks.Models.CreditCard;
import io.garuda.skyworks.Models.Provider;
import io.garuda.skyworks.R;

import static io.garuda.skyworks.CCUtils.CreditCardUtils.AMEX;
import static io.garuda.skyworks.CCUtils.CreditCardUtils.DISCOVER;
import static io.garuda.skyworks.CCUtils.CreditCardUtils.MASTERCARD;
import static io.garuda.skyworks.CCUtils.CreditCardUtils.NONE;
import static io.garuda.skyworks.CCUtils.CreditCardUtils.VISA;

/**
 * Created by joshl on 3/1/2018.
 */

public class CreditCardListAdapter extends ArrayAdapter<CreditCard> {

    private final Activity context;
    private final ArrayList<CreditCard> cards;
    private LayoutInflater inflater;
    TextView cardNumView;
    ImageView imgView;
    String cardNum;
    int cardType;

    public CreditCardListAdapter (Activity context, ArrayList<CreditCard> cards) {
        super(context, R.layout.cc_list_item, cards);
        this.context = context;
        this.cards = cards;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (null == convertView) {
            convertView = inflater.inflate(R.layout.cc_list_item, null, false);
        }

        cardNum = cards.get(position).getCardNum();
        cardType = Integer.parseInt(cards.get(position).getCardType());

        cardNumView = (TextView) convertView.findViewById(R.id.ccNum);
        imgView = (ImageView) convertView.findViewById(R.id.ccImage);

        cardNumView.setText(cardNum.substring(15));
        setCardType(cardType);


        return convertView;
    }

    public void setCardType(int type)
    {
        switch(type)
        {
            case VISA:
                imgView.setImageResource(R.drawable.ic_visa);
                break;
            case MASTERCARD:
                imgView.setImageResource(R.drawable.ic_mastercard);
                break;
            case AMEX:
                imgView.setImageResource(R.drawable.ic_amex);
                break;
            case DISCOVER:
                imgView.setImageResource(R.drawable.ic_discover);
                break;
            case NONE:
                imgView.setImageResource(android.R.color.transparent);
                break;

        }


    }



}



