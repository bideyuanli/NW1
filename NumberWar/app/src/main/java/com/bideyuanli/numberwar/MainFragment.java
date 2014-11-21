package com.bideyuanli.numberwar;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private NumberView[] views;
    private RelativeLayout root;
    private NumberView current;
    private NumberModel model = NumberModel.get();
    private NumberView score_view;
    private Button button;
    private boolean initialized = false;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void reset() {
        int delay = 500;
        int duration = 500;
        if (current != null) {
            root.removeAllViews();
            delay = 0;
        }
        model.reset();

        int width = root.getWidth() - root.getPaddingLeft() - root.getPaddingRight();
        int height = root.getHeight();
        int padding = 20;
        int start_y = 300;
        int start_x = padding;
        width -= padding * 2;
        height -= start_y + padding * 2;
        int step = width / model.getWidth();
        int tile_size = step - padding;

        int x_line = start_x;
        int y_line = start_y - step - 50;
        {
            button = new Button(getActivity());
            root.addView(button);
            button.setX(x_line);
            button.setY(y_line);
            button.setText("+");
            button.setTextColor(Color.WHITE);
            button.setTextSize(28);
            button.setElevation(10);

            button.setMinimumWidth(tile_size);
            button.setWidth(tile_size);
            button.setHeight(tile_size);
            button.setBackgroundResource(R.drawable.round_button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    reset();
                }
            });
        }
        {
            score_view = new NumberView(getActivity());
            root.addView(score_view);
            x_line = width / 4;
            score_view.setX(x_line);
            score_view.setY(y_line);
            score_view.setMinimumWidth(width / 2);
            score_view.setMinimumHeight(tile_size);
            score_view.setBackgroundResource(R.drawable.numberrect);
            score_view.setTextSize(24);
            score_view.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            score_view.setPadding(0, 0, 40, 0);
            updateScoreView();
        }
        {
            current = new NumberView(getActivity());
            root.addView(current);
            x_line = start_x + step * (model.getWidth() -1);
            current.setX(x_line);
            current.setY(y_line);
            current.setMinimumWidth(tile_size);
            current.setMinimumHeight(tile_size);
            current.setNumber(model.getCurrent());
            current.appearAnimation(delay + 200, duration);
        }

        views = new NumberView[model.getSize()];
        for (int i = 0; i < views.length; i++) {
            NumberView view = new NumberView(getActivity());
            final int index = i;
            views[i] = view;
            root.addView(view);
            int x = start_x + step * (i % model.getWidth());
            int y = start_y + step * (i / model.getWidth());
            view.setMinimumWidth(tile_size);
            view.setMinimumHeight(tile_size);
            view.setX(x);
            view.setY(y);
            view.setNumber(model.getGrid(i));
            view.appearAnimation(delay + (i % model.getWidth() + i / model.getWidth()) * 100, duration);

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickNumber(index);
                }
            });
        }
    }

    public void updateScoreView() {
        score_view.setText("" + model.getScore());
    }


    public void clickNumber(int position) {
        AnimationModel am = model.clickNumber(position);
        if (am == null) return;
        am.createAnimation(this).start();
    }

    public NumberView getView(int index) {
        if (index == NumberModel.CURRENT_INDEX) return current;
        return views[index];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        root = (RelativeLayout) rootView.findViewById(R.id.root);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!initialized && getView().getWidth() > 0) {
                    reset();
                    initialized = true;
                }
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
