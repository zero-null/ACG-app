package me.lonelee.droidlove.feature.recourse.anime;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Anime;
import me.lonelee.droidlove.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnimeFragment extends Fragment {

    public static final CharSequence TITLE = "动画";

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;

    public AnimeFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_official, container, false);

        findView(view);

        // 动画列表数据获取
        BmobQuery<Anime> query = new BmobQuery<>();
        query.findObjects(new FindListener<Anime>() {
            @Override
            public void done(List<Anime> object, BmobException e) {
                if(e == null){
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    mRecyclerView.setAdapter(new AnimeItemAdapter(getActivity(), object));
                    progressBar.setVisibility(View.GONE);
                }else{
                    ToastUtil.show(getActivity(),"动画列表数据获取失败");
                }
            }
        });

        return view;
    }

    private void findView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_leran_offical);
        progressBar = view.findViewById(R.id.progressbar);
    }

}
