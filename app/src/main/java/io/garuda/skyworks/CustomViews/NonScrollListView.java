package io.garuda.skyworks.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by joshl on 2/1/2018.
 *
 * This is a custom view for the gallery list to be placed in scrollview in Provider Detail activity
 */

public class NonScrollListView extends ListView {

    public NonScrollListView(Context context) {
        super(context);
    }
    public NonScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
