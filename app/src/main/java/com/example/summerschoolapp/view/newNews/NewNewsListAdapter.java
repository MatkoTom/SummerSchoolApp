package com.example.summerschoolapp.view.newNews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.Photo;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewNewsListAdapter extends RecyclerView.Adapter<NewNewsListAdapter.CustomVievHolder> {

    private List<Photo> data = new ArrayList<>();

    private PhotoListInteraction listener;
//
//    public NewNewsListAdapter(NewNewsListAdapter.PhotoListInteraction listener) {
//        this.listener = listener;
//    }

    public void setData(List<Photo> newData) {
        if (newData != null && !newData.isEmpty()) {
            this.data.clear();
            this.data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public NewNewsListAdapter.CustomVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_photos, parent, false);
        return new NewNewsListAdapter.CustomVievHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewNewsListAdapter.CustomVievHolder holder, int position) {
        Photo item = data.get(position);

        if (item.getImages() != null) {
            Glide.with(holder.ivPhoto.getContext())
                    .asBitmap()
                    .fitCenter()
                    .load(item.getImages())
                    .into(holder.ivPhoto);
        }

        //ignore
//        holder.rowParentLayout.setOnClickListener(view -> {
//            if (listener != null) {
//                listener.onUserClicked(item);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomVievHolder extends RecyclerView.ViewHolder {

        ImageView ivPhoto;

        public CustomVievHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.iv_news_photo);
        }
    }

    interface PhotoListInteraction {
        void onPhotoClicked(Photo photo);
    }
}
