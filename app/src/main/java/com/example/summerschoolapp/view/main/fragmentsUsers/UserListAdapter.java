package com.example.summerschoolapp.view.main.fragmentsUsers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.CustomVievHolder> {

    private List<User> data = new ArrayList<>();

    public void setData(List<User> newData) {
        if (newData != null && !newData.isEmpty()) {
            Collections.sort(newData, (user, t1) -> user.getEmail().compareTo(t1.getEmail()));
            this.data.clear();
            this.data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public CustomVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new CustomVievHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomVievHolder holder, int position) {
        User item = data.get(position);
        holder.tvUserFirstName.setText(item.getFirstName());
        holder.tvUserLastName.setText(item.getLastName());
        holder.tvUserEmail.setText(item.getEmail());
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

        public CustomVievHolder(@NonNull View itemView) {
            super(itemView);

            tvUserFirstName = itemView.findViewById(R.id.tv_user_firstname);
            tvUserLastName = itemView.findViewById(R.id.tv_user_lastname);
            tvUserEmail = itemView.findViewById(R.id.tv_user_email);
            ivUSerImg = itemView.findViewById(R.id.iv_user_img);
        }
    }
}
