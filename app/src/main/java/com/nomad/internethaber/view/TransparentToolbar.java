package com.nomad.internethaber.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

public final class TransparentToolbar extends Toolbar {

    public TransparentToolbar(Context context) {
        super(context);

        if (!isInEditMode()) {
            init();
        }
    }

    public TransparentToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init();
        }
    }

    public TransparentToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init();
        }
    }
    
    private void init(){
        setBackgroundColor(Color.TRANSPARENT);
    }
}
