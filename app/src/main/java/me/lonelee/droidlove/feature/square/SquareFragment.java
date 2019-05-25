package me.lonelee.droidlove.feature.square;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.base.BaseFragment;
import me.lonelee.droidlove.feature.square.passerby.PasserbyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SquareFragment extends BaseFragment {

    public SquareFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_square, container, false);

        findViews(v);

        actionBar.setTitle(R.string.channel_consult_name);
        toolbar.getMenu().clear();
        getActivity().getMenuInflater().inflate(R.menu.consult, toolbar.getMenu());

        CircleMenu circleMenu = v.findViewById(R.id.square_menu);

        circleMenu.setMainMenu(getResources().getColor(R.color.colorPrimary), R.drawable.ic_home, R.drawable.ic_close)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.ic_square)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.ic_passerby)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch (index){
                            case 1:
                                getActivity().startActivity(new Intent(getActivity(), PasserbyActivity.class));
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
            }

        });

        return v;
    }

    @Override
    public void findViews(View v) {
        super.findViews(v);
    }

}
