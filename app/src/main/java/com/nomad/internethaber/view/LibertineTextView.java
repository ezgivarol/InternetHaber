package com.nomad.internethaber.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public final class LibertineTextView extends TextView {

    public LibertineTextView(Context context) {
        super(context);

        if (!isInEditMode())
            init();
    }

    public LibertineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode())
            init();
    }

    public LibertineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode())
            init();
    }

    private void init(){
        Typeface typeface = FontProvider.getInstance(getContext());
        setTypeface(typeface);
    }

    private static class FontProvider{
        private static Typeface sTypeface;

        public static Typeface getInstance(Context context) {
            if (sTypeface == null)
                sTypeface = Typeface.createFromAsset(context.getAssets(), "Libertine.ttf");

            return sTypeface;
        }
    }
}
