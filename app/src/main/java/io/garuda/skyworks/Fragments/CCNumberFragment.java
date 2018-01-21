package io.garuda.skyworks.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import io.garuda.skyworks.Fragments.CardFrontFragment;
import io.garuda.skyworks.Activities.AddPaymentMethod;
import io.garuda.skyworks.R;
import io.garuda.skyworks.CustomViews.CreditCardEditText;
import io.garuda.skyworks.CCUtils.CreditCardFormattingTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class CCNumberFragment extends Fragment {


    @BindView(R.id.et_number)
    CreditCardEditText et_number;
    TextView tv_number;

    AddPaymentMethod activity;
    CardFrontFragment cardFrontFragment;

    public CCNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ccnumber, container, false);
        ButterKnife.bind(this, view);

        activity = (AddPaymentMethod) getActivity();
        cardFrontFragment = activity.cardFrontFragment;

        tv_number = cardFrontFragment.getNumber();

        //Do your stuff
        et_number.addTextChangedListener(new CreditCardFormattingTextWatcher(et_number, tv_number,cardFrontFragment.getCardType(),new CreditCardFormattingTextWatcher.CreditCardType() {
            @Override
            public void setCardType(int type) {
                Log.d("Card", "setCardType: "+type);

                cardFrontFragment.setCardType(type);
            }
        }));

        et_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(activity!=null)
                    {
                        activity.nextClick();
                        return true;
                    }

                }
                return false;
            }
        });

        et_number.setOnBackButtonListener(new CreditCardEditText.BackButtonListener() {
            @Override
            public void onBackClick() {
                if(activity!=null)
                    activity.onBackPressed();
            }
        });

        return view;
    }

    public String getCardNumber()
    {
        if(et_number!=null)
            return et_number.getText().toString().trim();

        return null;
    }



}
