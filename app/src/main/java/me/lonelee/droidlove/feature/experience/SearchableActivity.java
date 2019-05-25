package me.lonelee.droidlove.feature.experience;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.lonelee.droidlove.bean.Post;
import me.lonelee.droidlove.bean.User;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String queryString = intent.getStringExtra(SearchManager.QUERY);

            User user = BmobUser.getCurrentUser(User.class);
            BmobQuery<Post> query = new BmobQuery<>();
            query.addWhereEqualTo("title", queryString).addWhereEqualTo("author",user);
            query.findObjects(new FindListener<Post>() {

                @Override
                public void done(List<Post> object, BmobException e) {
                    if(e==null){
                        setListAdapter(new SearchedPostAdapater(SearchableActivity.this,object));
                    }else{

                    }
                }

            });
        }

    }
}
