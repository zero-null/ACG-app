package me.lonelee.droidlove.feature.recourse.comic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Comic;
import me.lonelee.droidlove.bean.Resource;
import me.lonelee.droidlove.util.CommonUtil;

public class ComicItemAdapter extends RecyclerView.Adapter<ComicItemAdapter.ViewHolder> {

    private Activity activity;

    private List<Comic> comicList;

    ComicItemAdapter(Activity activity, List<Comic> comicList) {
        this.activity = activity;
        this.comicList = comicList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView comicItemCover;
        TextView comicItemName;
        TagContainerLayout comicItemTag;

        ViewHolder(View view) {
            super(view);
            comicItemCover = view.findViewById(R.id.comic_item_cover);
            comicItemName =  view.findViewById(R.id.comic_item_name);
            comicItemTag = view.findViewById(R.id.comic_tag);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicItemAdapter.ViewHolder holder, final int position) {
        final Comic curComic = comicList.get(position);
        Glide.with(activity).load(curComic.getCover().getFileUrl()).into(holder.comicItemCover);
        holder.comicItemName.setText(curComic.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.openWebPage(activity,curComic.getUrl());
            }
        });
        holder.comicItemTag.setTags(curComic.getTag().split("，"));
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

}