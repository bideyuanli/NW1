package com.bideyuanli.numberwar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;


/**
 * TODO: document your custom view class.
 */
public class NumberView extends TextSwitcher {
    private String mExampleString = "11";
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 100; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public NumberView(Context context) {
        super(context);
        init(null, 0);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }


    private void init(AttributeSet attrs, int defStyle) {
        setElevation(10);
        // Load attributes
        setFactory(new ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(getContext());
                myText.setGravity(Gravity.CENTER);
                myText.setTextSize(36);
                myText.setTextColor(Color.BLACK);
                return myText;
            }
        });

        // Declare the in and out animations and initialize them
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);

        // set the animation type of textSwitcher
        setInAnimation(in);
        setOutAnimation(out);
    }

    public void setNumber(int n, boolean animated) {
        String text = "";
        if (n > 0) text = "" + n;

        if (animated) {
            setText(text);
        } else {
            setCurrentText(text);
        }
        int c = 255 - n * 20;
        int color = Color.rgb(c, c, c);
        setBackgroundColor(color);
    }
    public void setNumber(int n) {
        setNumber(n, false);
    }
}
