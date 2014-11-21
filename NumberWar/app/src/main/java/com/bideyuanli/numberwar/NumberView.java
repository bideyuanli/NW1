package com.bideyuanli.numberwar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


/**
 * TODO: document your custom view class.
 */
public class NumberView extends TextView {
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
        setGravity(Gravity.CENTER);
        setTextSize(36);
        setTextColor(Color.BLACK);
    }

    public void setNumber(int n, boolean animated) {
        int color = NumberModel.get().getColor(n) | 0xFF000000;
        String text = "";
        switch (n) {
            case 0:
                break;
            case NumberModel.VALUE_ALL:
                text = "?";
                break;
            default:
                text = "" + n;
        }


        setText(text);
        setBackgroundColor(color);

    }

    public void setNumber(int n) {
        setNumber(n, false);
    }

    public void appearAnimation(int delay) {
        setScaleX(0f);
        setScaleY(0f);
        AnimatorSet set = createScaleAnimation(200, 1f);
        set.setStartDelay(delay);
        set.start();
    }

    public AnimatorSet createScaleAnimation(int duration, float scale) {
        AnimatorSet set = new AnimatorSet();
        Animator a1 = ObjectAnimator.ofFloat(this, "scaleX", scale);
        a1.setDuration(duration);

        Animator a2 = ObjectAnimator.ofFloat(this, "scaleY", scale);
        a2.setDuration(duration);
        set.playTogether(a1, a2);
        return set;
    }

}
