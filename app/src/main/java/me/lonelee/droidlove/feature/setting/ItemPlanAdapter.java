package me.lonelee.droidlove.feature.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Plan;
import me.lonelee.droidlove.bean.User;
import me.lonelee.droidlove.util.ToastUtil;

public class ItemPlanAdapter extends RecyclerView.Adapter<ItemPlanAdapter.ViewHolder> {

    private Context context;

    private List<Plan> planList = new ArrayList<>();

    public ItemPlanAdapter(Context c) {
        this.context = c;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public CardView cvItemPlan;
        public TextView tvPlanName;
        public TextView tvPlanDate;

        public ViewHolder(View v) {
            super(v);
            view = v;
            cvItemPlan = v.findViewById(R.id.cv_item_plan);
            tvPlanName = v.findViewById(R.id.tv_plan_name);
            tvPlanDate = v.findViewById(R.id.tv_plan_date);
        }
    }


        @NonNull
    @Override
    public ItemPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_plan,parent,false);
            ViewHolder vh =  new ViewHolder(v);
            return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemPlanAdapter.ViewHolder holder, final int position) {
        final Plan curPlan = planList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"确定删除吗？",Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Plan plan = new Plan();
                        plan.setObjectId(curPlan.getObjectId());
                        plan.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    User user = BmobUser.getCurrentUser(User.class);
                                    BmobQuery<Plan> query = new BmobQuery<>();
                                    query.addWhereEqualTo("owner",user);
                                    query.order("date");
                                    query.findObjects(new FindListener<Plan>() {
                                        @Override
                                        public void done(List<Plan> object, BmobException e) {
                                            if(e==null){
                                                if (object.isEmpty()){
                                                    PlanFragment.tvNonePlan.setVisibility(View.VISIBLE);
                                                }else {
                                                    PlanFragment.tvNonePlan.setVisibility(View.GONE);
                                                    PlanFragment.rvPlans.setVisibility(View.VISIBLE);
                                                }
                                                PlanFragment.itemPlanAdapter.setPlans(object);
                                                PlanFragment.itemPlanAdapter.notifyDataSetChanged();
                                                PlanFragment.progressBar.setVisibility(View.GONE);
                                                ToastUtil.show(context,"删除成功");
                                            }else{

                                            }
                                        }
                                    });
                                }else{
                                    ToastUtil.show(context,"删除失败");
                                }
                        }
                    });
                    }
                }).show();

            }
        });
        holder.tvPlanName.setText(curPlan.getName());
        holder.tvPlanDate.setText(curPlan.getDate().getDate());
        if ( position == (planList.size() - 2)){
            holder.view.findViewById(R.id.plan_item_endflag).setVisibility(View.GONE);

        }
        if ( position == (planList.size() - 1)){
           holder.view.findViewById(R.id.plan_item_endflag).setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }


    public  List<Plan> getPlans(){
        return planList;
    }

    public void setPlans(List<Plan> plans){
        this.planList = plans;
    }

}
