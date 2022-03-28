package com.example.speedrunapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<Game> {

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView gameName;
        TextView releaseYear;
        ImageView coverImage;
    }

    public GameAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Game> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    public Game getGame(int position){
        return getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String imgURL = getItem(position).getCoverImageLink();
        String gameName = getItem(position).getGameName();
        String releaseYear = "Released: " + getItem(position).getReleaseYear();

        final View result;

        GameAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new GameAdapter.ViewHolder();
            holder.gameName = (TextView) convertView.findViewById(R.id.tvGame);
            holder.releaseYear = (TextView) convertView.findViewById(R.id.tvReleased);
            holder.coverImage = (ImageView) convertView.findViewById(R.id.ivCover);

            holder.coverImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.coverImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    //Load an image to your IMAGEVIEW here
                }
            });
            convertView.setTag(holder);
        }
        else {
            holder = (GameAdapter.ViewHolder) convertView.getTag();
        }

        Glide.with(mContext)
                .load(imgURL)
                .override(0, 0)
                .into(holder.coverImage);

        holder.gameName.setText(gameName);
        holder.releaseYear.setText(releaseYear);
        //convertView.setBackgroundColor(Color.parseColor("#000000"));

        return convertView;
    }

}
