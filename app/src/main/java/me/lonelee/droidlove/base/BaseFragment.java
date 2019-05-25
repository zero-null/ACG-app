package me.lonelee.droidlove.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.feature.MainActivity;


public class BaseFragment extends Fragment {

    public ActionBar actionBar;
    public Toolbar toolbar;

    public BaseFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        return textView;
    }

    public void findViews(View v){
        actionBar =  ((MainActivity) getActivity()).getSupportActionBar();
        toolbar = getActivity().findViewById(R.id.toolbar);
    }

}
