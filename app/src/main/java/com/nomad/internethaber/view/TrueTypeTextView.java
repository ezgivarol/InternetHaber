package com.nomad.internethaber.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by caner on 6.4.2015.
 */
public class TrueTypeTextView extends TextView {
    public TrueTypeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TrueTypeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrueTypeTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
       Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-CondBold.ttf");
        setTypeface(tf);
    }
}
