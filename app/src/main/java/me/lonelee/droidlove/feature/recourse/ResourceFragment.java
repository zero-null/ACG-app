package me.lonelee.droidlove.feature.recourse;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.feature.MainActivity;
import me.lonelee.droidlove.feature.recourse.ResourcePageAdapter;
import me.lonelee.droidlove.base.BaseFragment;

public class ResourceFragment extends BaseFragment {

    private TabLayout tabLearn;
    private ViewPager pagerLearn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learn, container, false);

        findViews(v);

        actionBar =  ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.channel_learn_name);
        toolbar.getMenu().clear();
        getActivity().getMenuInflater().inflate(R.menu.learn, toolbar.getMenu());

        // 将标签与页面绑定
        tabLearn.setupWithViewPager(pagerLearn);
        pagerLearn.setAdapter(new ResourcePageAdapter(getActivity().getSupportFragmentManager()));
        return v;
    }

    @Override
    public void findViews(View v) {
        super.findViews(v);
        pagerLearn = (ViewPager) v.findViewById(R.id.pager_learn);
        tabLearn = (TabLayout) v.findViewById(R.id.tab_learn);
    }

}




