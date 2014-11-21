package com.bideyuanli.numberwar;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siyi on 2014/11/20.
 */
public class AnimationModel implements Animator.AnimatorListener {
    private final static int kDuration = 400;
    private MainFragment main;
    private NumberView from_views[];
    private NumberView to_view;
    private List<Integer> froms;
    private int to;
    private float from_xs[];
    private float from_ys[];

    public Animator createAnimation(MainFragment main) {
        this.main = main;
        Interpolator interpolator = AnimationUtils.loadInterpolator(main.getActivity(), R.anim.interpolator);
        to_view = main.getView(to);
        AnimatorSet set = new AnimatorSet();
        for (int i = 0; i < froms.size(); i++) {
            int from = froms.get(i);
            from_views[i] = main.getView(from);
            from_xs[i] = from_views[i].getX();
            from_ys[i] = from_views[i].getY();
            from_views[i].setTranslationZ(10);

            Animator a1 = ObjectAnimator.ofFloat(from_views[i], "x", to_view.getX());
            a1.setDuration(kDuration);
            a1.setInterpolator(interpolator);
            Animator a2 = ObjectAnimator.ofFloat(from_views[i], "y", to_view.getY());
            a2.setDuration(kDuration);
            a2.setInterpolator(interpolator);
            set.playTogether(a1, a2);
        }
        if (froms.size() > 1) {
            to_view.setTranslationZ(11);
            set.play(to_view.createScaleAnimation(kDuration, 1.1f));
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

        boolean animation = froms.size() > 1;
        to_view.setNumber(model.getGrid(to), animation);

        AnimatorSet set = new AnimatorSet();
        for (int i = 0; i < froms.size(); i++) {
            NumberView from_view = from_views[i];
            from_view.setX(from_xs[i]);
            from_view.setY(from_ys[i]);
            from_views[i].setTranslationZ(0);
            from_view.setNumber(model.getGrid(froms.get(i)));

            from_view.appearAnimation(0);
        }
        AnimationModel next = model.calculate(to);
        main.updateScoreView();
        if (next == null) {
            if (froms.size() > 1) {
                to_view.createScaleAnimation(kDuration, 1.0f).start();
                to_view.setTranslationZ(0);
            }
            return;
        }
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
