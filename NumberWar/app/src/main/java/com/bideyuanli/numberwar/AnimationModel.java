package com.bideyuanli.numberwar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

/**
 * Created by Siyi on 2014/11/20.
 */
public class AnimationModel implements Animator.AnimatorListener {
    MainFragment main;
    NumberView from_view;
    NumberView to_view;
    private int from;
    private int to;
    private float from_x;
    private float from_y;

    public Animator createAnimation(MainFragment main) {
        this.main = main;
        from_view = main.getView(from);
        to_view = main.getView(to);
        from_x = from_view.getX();
        from_y = from_view.getY();
        from_view.setTranslationZ(10);

        AnimatorSet set = new AnimatorSet();
        Animator a1 = ObjectAnimator.ofFloat(from_view, "x", to_view.getX());

        a1.setDuration(800);
        Animator a2 = ObjectAnimator.ofFloat(from_view, "y", to_view.getY());
        a2.setDuration(800);
        set.playTogether(a1, a2);
        set.addListener(this);
        return set;
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        NumberModel model = NumberModel.get();
        from_view.setX(from_x);
        from_view.setY(from_y);
        to_view.setNumber(model.getGrid(to));
        from_view.setNumber(model.getGrid(from));
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
