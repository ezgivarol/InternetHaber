package com.nomad.internethaber.view;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

public final class StyledToolbar extends Toolbar {
    
    public StyledToolbar(Context context) {
        super(context);

        if (!isInEditMode()) {
            init();
        }
    }

    public StyledToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init();
        }
    }

    public StyledToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init();
        }
    }
    
    private void init(){
        setSubtitleTextAppearance(getContext(), android.support.v7.appcompat.R.style.Base_TextAppearance_AppCompat_Small);
    }
}
