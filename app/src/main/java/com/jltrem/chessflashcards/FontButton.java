package com.jltrem.chessflashcards;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import java.util.Hashtable;

public class FontButton extends Button {
    public FontButton(Context context) {
        super(context);
    }

    public FontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public FontButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }
}

