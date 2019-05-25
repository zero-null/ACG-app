package me.lonelee.droidlove.feature.recourse.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.lonelee.droidlove.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    public static final CharSequence TITLE = "游戏";

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

}
