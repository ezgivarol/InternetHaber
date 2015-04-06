package com.nomad.internethaber.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

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
        AssetManager assets = getContext().getAssets();
        Typeface typeface = TypefaceProvider.getInstance(assets);
        setTypeface(typeface);
    }

    private static class TypefaceProvider{

        private static Typeface sTypeface;
        public static Typeface getInstance(AssetManager manager) {
            if (sTypeface == null) {
                sTypeface = Typeface.createFromAsset(manager, "fonts/OpenSans-CondBold.ttf");
            }

            return sTypeface;
        }
    }
}
