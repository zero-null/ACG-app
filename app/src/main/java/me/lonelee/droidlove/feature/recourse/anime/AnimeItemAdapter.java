package me.lonelee.droidlove.feature.recourse.anime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.bean.Anime;

public class AnimeItemAdapter extends RecyclerView.Adapter<AnimeItemAdapter.ViewHolder> {

    private Activity activity;

    private List<Anime> animeList;

    AnimeItemAdapter(Activity activity, List<Anime> animeList) {
        this.activity = activity;
        this.animeList = animeList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView animeItemCover;
        TextView animeItemName;

        ViewHolder(View view) {
            super(view);
            animeItemCover = view.findViewById(R.id.anime_item_cover);
            animeItemName =  view.findViewById(R.id.anime_item_name);
        }
    }


        @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_anime, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeItemAdapter.ViewHolder holder, final int position) {
        final Anime curAnime = animeList.get(position);
        Glide.with(activity).load(curAnime.getCover().getFileUrl()).into(holder.animeItemCover);
        holder.animeItemName.setText(curAnime.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AnimeDetailActivity.class);
                intent.putExtra("coverUrl", curAnime.getCover().getFileUrl());
                intent.putExtra("name", curAnime.getName());
                intent.putExtra("description", curAnime.getDescription());
                intent.putExtra("label", curAnime.getLabel());
                intent.putExtra("url", curAnime.getUrl());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

}
