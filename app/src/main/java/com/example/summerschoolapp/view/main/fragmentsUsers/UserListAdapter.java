package com.example.summerschoolapp.view.main.fragmentsUsers;

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
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.CustomVievHolder> {

    private List<User> data = new ArrayList<>();

    private UserListInteraction listener;

    public UserListAdapter(UserListInteraction listener) {
        this.listener = listener;
    }

    public void setData(List<User> newData) {
        if (newData != null && !newData.isEmpty()) {
            Collections.sort(newData, (user, t1) -> {
                if (user.getEmail() != null) {
                    return user.getEmail().compareTo(t1.getEmail());
                } else {
                    return 0;
                }
            });

            this.data.clear();
            this.data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CustomVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_row_user_list, parent, false);
        return new CustomVievHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomVievHolder holder, int position) {
        User item = data.get(position);

        if (item.getName() == null) {
            holder.tvUserFirstName.setText(item.getFirstName());
            holder.tvUserLastName.setText(item.getLastName());
        } else {
            holder.tvUserFirstName.setText(item.getName());
        }

        holder.tvUserEmail.setText(item.getEmail());

        if (item.getPhoto() != null) {
            Glide.with(holder.ivUSerImg.getContext())
                    .asBitmap()
                    .fitCenter()
                    .load(Const.Api.API_GET_IMAGE + item.getPhoto())
                    .into(holder.ivUSerImg);
        } else {
            Glide.with(holder.ivUSerImg.getContext())
                    .asBitmap()
                    .fitCenter()
                    .load(holder.ivUSerImg.getResources().getDrawable(R.drawable.nav_users_icon))
                    .into(holder.ivUSerImg);
        }

        holder.rowParentLayout.setOnClickListener(view -> {
            if (listener != null) {
                listener.onUserClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomVievHolder extends RecyclerView.ViewHolder {

        TextView tvUserFirstName;
        TextView tvUserLastName;
        TextView tvUserEmail;
        ImageView ivUSerImg;
        ConstraintLayout rowParentLayout;

        public CustomVievHolder(@NonNull View itemView) {
            super(itemView);

            tvUserFirstName = itemView.findViewById(R.id.tv_user_firstname);
            tvUserLastName = itemView.findViewById(R.id.tv_user_lastname);
            tvUserEmail = itemView.findViewById(R.id.tv_user_email);
            ivUSerImg = itemView.findViewById(R.id.iv_user_img);
            rowParentLayout = itemView.findViewById(R.id.row_parent_layout);
        }
    }

    interface UserListInteraction {
        void onUserClicked(User user);
    }
}
