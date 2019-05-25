package me.lonelee.droidlove.feature.experience;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.base.BaseFragment;
import me.lonelee.droidlove.bean.Post;
import me.lonelee.droidlove.bean.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExperienceFragment extends BaseFragment {

    private RecyclerView rvPost;

    public ExperienceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_write, container, false);

        findViews(v);

        actionBar.setTitle(R.string.channel_write_name);
        toolbar.getMenu().clear();
        getActivity().getMenuInflater().inflate(R.menu.write, toolbar.getMenu());

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.search_article).getActionView();
        searchView.setQueryHint("搜索文章...");
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getActivity().getPackageName(),SearchableActivity.class.getName())));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("author", user).order("-createdAt");
        query.findObjects(new FindListener<Post>() {

            @Override
            public void done(List<Post> object,BmobException e) {
                if(e==null){
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                    rvPost.setLayoutManager(staggeredGridLayoutManager);
                    rvPost.setAdapter(new ExperienceItemAdapter(getActivity(),object));
                }else{

                }
            }

        });
    }

    @Override
    public void findViews(View v) {
       super.findViews(v);
       rvPost = v.findViewById(R.id.rv_post);
    }


}
