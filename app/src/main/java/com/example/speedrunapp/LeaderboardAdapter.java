package com.example.speedrunapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter<Run> {

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView place;
        TextView username;
        TextView time;
        ImageView flag;
    }

    public LeaderboardAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Run> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String place = getItem(position).getPlace();
        String username = "Player";
        String time = getItem(position).getTime();
        String imgURL = getItem(position).getImgURL();
        String colorStart = "#FFFFFF";
        String colorEnd = "#FFFFFF";

        if (time != "Time") {
            colorStart = getItem(position).getUser().getColorStart();
            colorEnd = getItem(position).getUser().getColorEnd();
            username = getItem(position).getUser().getUsername();
        }

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.place = (TextView) convertView.findViewById(R.id.tvPlace);
            holder.username = (TextView) convertView.findViewById(R.id.tvUsername);
            holder.time = (TextView) convertView.findViewById(R.id.tvTime);
            holder.flag = (ImageView) convertView.findViewById(R.id.ivFlag);

            holder.flag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.flag.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // If user doesn't have a country, they won't have a flag
                    if (imgURL.compareTo("https://www.speedrun.com/images/flags/default.png") == 0) {
                        holder.username.setTranslationX(-30);
                    }
                    // Other users get a flag next to their name
                    else {
                        Glide.with(mContext)
                                .load(imgURL)
                                .override(300, 200)
                                .into(holder.flag);
                    }

                }
            });

            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Setting up correct info for each run
        holder.place.setText(place);
        holder.username.setText(username);
        holder.time.setText(time);

        // Setting up the color (gradient) for each player
        holder.username.setTextColor(Color.parseColor(colorStart));
        Shader textShader = new LinearGradient(0, 0, holder.username.getPaint().measureText(holder.username.getText().toString()), holder.username.getTextSize(),
                new int[]{Color.parseColor(colorStart), Color.parseColor(colorEnd)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        holder.username.getPaint().setShader(textShader);

        // Condition for which background the run should have
        if (position % 2 == 0 && getItem(position).getTime() != "Time") {
            convertView.setBackgroundColor(Color.parseColor("#171717"));
        }

        return convertView;
    }
}
