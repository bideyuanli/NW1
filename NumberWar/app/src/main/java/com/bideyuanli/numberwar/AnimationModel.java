package com.bideyuanli.numberwar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siyi on 2014/11/20.
 */
public class AnimationModel implements Animator.AnimatorListener {
    MainFragment main;
    NumberView from_views[];
    NumberView to_view;
    private List<Integer> froms;
    private int to;
    private float from_xs[];
    private float from_ys[];

    public Animator createAnimation(MainFragment main) {
        this.main = main;
        to_view = main.getView(to);
        AnimatorSet set = new AnimatorSet();
        for (int i = 0; i < froms.size(); i++) {
            int from = froms.get(i);
            from_views[i] = main.getView(from);
            from_xs[i] = from_views[i].getX();
            from_ys[i] = from_views[i].getY();
            from_views[i].setTranslationZ(10);

            Animator a1 = ObjectAnimator.ofFloat(from_views[i], "x", to_view.getX());
            a1.setDuration(800);
            a1.setInterpolator(new AccelerateDecelerateInterpolator());
            Animator a2 = ObjectAnimator.ofFloat(from_views[i], "y", to_view.getY());
            a2.setDuration(800);
            set.playTogether(a1, a2);
        }
        set.addListener(this);
        return set;
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        NumberModel model = NumberModel.get();

        AnimatorSet set = new AnimatorSet();
        for (int i = 0; i < froms.size(); i++) {
            NumberView from_view = from_views[i];
            from_view.setAlpha(0f);
            from_view.setX(from_xs[i]);
            from_view.setY(from_ys[i]);
            to_view.setNumber(model.getGrid(to));
            from_view.setNumber(model.getGrid(froms.get(i)));

            Animator a1 = ObjectAnimator.ofFloat(from_view, "alpha", 1f);
            a1.setDuration(300);
            set.play(a1);
        }
        set.start();
        AnimationModel next =  model.calculate(to);
        if (next == null) return;
        next.createAnimation(main).start();

    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    public void setFrom(int from) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(from);
        setFroms(list);
    }

    public void setFroms(List<Integer> froms) {
        this.froms = froms;
        from_xs = new float[froms.size()];
        from_ys = new float[froms.size()];
        from_views = new NumberView[froms.size()];
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
