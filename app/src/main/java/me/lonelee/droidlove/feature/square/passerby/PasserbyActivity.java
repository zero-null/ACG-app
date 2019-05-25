package me.lonelee.droidlove.feature.square.passerby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.User;
import me.lonelee.droidlove.util.ToastUtil;

public class PasserbyActivity extends AppCompatActivity {

    private RecyclerView recyclerSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passerby);

        Toolbar toolbar = findViewById(R.id.passerby_toolbar);
        setSupportActionBar(toolbar);


        findViews();
        User userInfo = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereNotEqualTo("objectId", userInfo.getObjectId());
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    setTitle("路人（" + object.size() + "）");
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(PasserbyActivity.this,2);
                    recyclerSquare.setLayoutManager(gridLayoutManager);
                    recyclerSquare.setAdapter(new PasserbyItemAdapter(PasserbyActivity.this,object));
                }else{
                    ToastUtil.show(PasserbyActivity.this,"路人数据加载失败");
                }
            }
        });

    }

    private void findViews() {
        recyclerSquare = findViewById(R.id.recycle_square);
    }
}
