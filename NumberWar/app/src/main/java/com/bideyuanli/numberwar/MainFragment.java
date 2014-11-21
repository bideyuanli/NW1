package com.bideyuanli.numberwar;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        model.reset();

        int width = root.getWidth() - root.getPaddingLeft() - root.getPaddingRight();
        int height = root.getHeight();
        Log.d("=====", "" + width);
        int padding = 20;
        int start_y = 300;
        int start_x = padding;
        width -= padding * 2;
        height -= start_y + padding * 2;
        int step = width / model.getWidth();
        int tile_size = step - padding;


        current.setMinimumWidth(tile_size);
        current.setMinimumHeight(tile_size);
        current.setNumber(model.getCurrent());

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

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clickNumber(index);
                }
            });
        }
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
        current = (NumberView) rootView.findViewById(R.id.current);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!initialized && getView().getWidth() > 0) {
                    reset();
                    initialized = true;
                }
            }
        });
        //reset();
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
