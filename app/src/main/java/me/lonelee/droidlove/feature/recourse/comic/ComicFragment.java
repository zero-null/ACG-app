package me.lonelee.droidlove.feature.recourse.comic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Comic;
import me.lonelee.droidlove.bean.Resource;
import me.lonelee.droidlove.util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicFragment extends Fragment {

    public static final CharSequence TITLE = "漫画";

    private RecyclerView comicRecycle;
    private ProgressBar comicProgressbar;

    public ComicFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_comic, container, false);

        // 漫画列表数据获取
        BmobQuery<Comic> query = new BmobQuery<>();
        query.findObjects(new FindListener<Comic>() {
            @Override
            public void done(List<Comic> object, BmobException e) {
                if (e == null){
                    comicRecycle = view.findViewById(R.id.comic_recycle);
                    comicRecycle.setHasFixedSize(true);
                    comicRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                    comicRecycle.setAdapter(new ComicItemAdapter(getActivity(), object));
                    comicProgressbar = view.findViewById(R.id.comic_progressbar);
                    comicProgressbar.setVisibility(View.GONE);
                }else{
                    ToastUtil.show(getActivity(),"漫画列表数据获取失败");
                }
            }
        });
        return view;
    }

}
