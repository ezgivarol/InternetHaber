package com.nomad.internethaber.view;


import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.nomad.internethaber.R;

public class BannerView extends Toolbar {
    public BannerView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init();
        }
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init();
        }
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init();
        }
    }

    private void init(){

        setBackground(R.drawable.image_internet_haber);

    }

    private void setBackground(int image_internet_haber) {

    }
}
