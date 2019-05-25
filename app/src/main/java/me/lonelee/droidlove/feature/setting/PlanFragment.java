package me.lonelee.droidlove.feature.setting;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.base.BaseFragment;
import me.lonelee.droidlove.bean.Plan;
import me.lonelee.droidlove.bean.User;
import me.lonelee.droidlove.util.ToastUtil;

public class PlanFragment extends BaseFragment {

    public static ProgressBar progressBar;
    public static TextView tvNonePlan;

    public static RecyclerView rvPlans;
    public static ItemPlanAdapter itemPlanAdapter;

    private FloatingActionButton fabNewPlan;

    public static AlarmManager alarmManager;
    public static Map<String,PendingIntent> pendingIntentMap = new HashMap<>();

    public PlanFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);

        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        findViews(v);

        actionBar.setTitle(R.string.channel_plan_name);
        toolbar.getMenu().clear();
        getActivity().getMenuInflater().inflate(R.menu.plan, toolbar.getMenu());

        itemPlanAdapter =  new ItemPlanAdapter(getActivity());

        rvPlans.setHasFixedSize(true);
         LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvPlans.setLayoutManager(mLayoutManager);

        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Plan> query = new BmobQuery<>();
        query.addWhereEqualTo("owner", user);
        query.order("date");
        query.findObjects(new FindListener<Plan>() {
            @Override
            public void done(List<Plan> object, BmobException e) {
                if(e == null){
                    if (object.isEmpty()){
                        tvNonePlan.setVisibility(View.VISIBLE);
                    }else {
                        tvNonePlan.setVisibility(View.GONE);
                        rvPlans.setVisibility(View.VISIBLE);
                    }
                    itemPlanAdapter.setPlans(object);
                    rvPlans.setAdapter(itemPlanAdapter);
                    progressBar.setVisibility(View.GONE);
                }else{
                    ToastUtil.show(getActivity(),"查询失败");
                }
            }
        });

        fabNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogPlanAdd = new AlertDialog.Builder(getActivity());
                View dialogPlanAddView = getLayoutInflater().inflate(R.layout.dialog_plan_add, null);
                final EditText planAddName = dialogPlanAddView.findViewById(R.id.plan_add_name);
                CalendarView planAddDay = dialogPlanAddView.findViewById(R.id.plan_add_day);
                TimePicker planAddTime= dialogPlanAddView.findViewById(R.id.plan_add_time);

                // 日期+时间的容器
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());

                // 获取日期
                planAddDay.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }
                });

                // 获取时间
                planAddTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                    }
                });
                dialogPlanAdd.setTitle("新建计划")
                        .setView(dialogPlanAddView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                User user = BmobUser.getCurrentUser(User.class);
                                Plan plan = new Plan();
                                plan.setName(planAddName.getText().toString());
                                plan.setDate(new BmobDate(c.getTime()));
                                plan.setOwner(user);
                                plan.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {
                                        if(e==null){
                                            // 根据计划设定时间，跳转到指定活动
                                            Intent intent = new Intent(getActivity(), PlanRemindActivity.class);
                                            intent.setAction(Intent.ACTION_MAIN);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                            intent.putExtra("plan_remind_name",planAddName.getText().toString());
                                            PendingIntent alarmIntent = PendingIntent.getActivity(getActivity(), objectId.hashCode(), intent, 0);
                                            pendingIntentMap.put(objectId,alarmIntent);
                                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);

                                            // 更新完毕后重新查询
                                            User user = BmobUser.getCurrentUser(User.class);
                                            BmobQuery<Plan> query = new BmobQuery<>();
                                            query.addWhereEqualTo("owner",user);
                                            query.order("date");
                                            query.findObjects(new FindListener<Plan>() {
                                                @Override
                                                public void done(List<Plan> object, BmobException e) {
                                                    if(e==null){
                                                        if (object.size() == 0){
                                                            tvNonePlan.setVisibility(View.VISIBLE);
                                                        }else {
                                                            tvNonePlan.setVisibility(View.GONE);
                                                            rvPlans.setVisibility(View.VISIBLE);
                                                        }
                                                        itemPlanAdapter.setPlans(object);
                                                        itemPlanAdapter.notifyDataSetChanged();
                                                        progressBar.setVisibility(View.GONE);
                                                        ToastUtil.show(getActivity(),"添加计划成功");
                                                    } else {

                                                    }
                                                }
                                            });
                                        } else {
                                            ToastUtil.show(getActivity(),"添加计划失败");
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
            }
        });

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.plan, menu);
    }

    @Override
    public void findViews(View v)
    {
        super.findViews(v);
        tvNonePlan = v.findViewById(R.id.tv_none_plan);
        progressBar = v.findViewById(R.id.plan_progressbar);
        rvPlans = v.findViewById(R.id.rv_plans);
        fabNewPlan = v.findViewById(R.id.fab_new_plan);
    }

}
