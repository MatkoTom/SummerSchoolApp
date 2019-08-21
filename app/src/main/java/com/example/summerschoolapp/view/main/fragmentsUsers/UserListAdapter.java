package com.example.summerschoolapp.view.main.fragmentsUsers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.summerschoolapp.R;
import com.example.summerschoolapp.model.User;
import com.example.summerschoolapp.utils.Tools;
import com.example.summerschoolapp.view.editUser.EditUserActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.CustomVievHolder> {

    private List<User> data = new ArrayList<>();
    // TODO @Matko
    // context is generally not necessary as a global variable
    // you can extract it from a view
    private Context context;

    public UserListAdapter(Context context) {
        this.context = context;
    }

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
        View view = layoutInflater.inflate(R.layout.recyclerview_row_user_list, parent, false);
        return new CustomVievHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomVievHolder holder, int position) {
        User item = data.get(position);
        holder.tvUserFirstName.setText(item.getFirstName());
        holder.tvUserLastName.setText(item.getLastName());
        holder.tvUserEmail.setText(item.getEmail());

        // TODO @Matko
        // there should be an interface here and not a direct navigation from adapter
        holder.rowParentLayout.setOnClickListener(view -> {
            Tools.getSharedPreferences(view.getContext()).saveUserToEdit(data.get(position));
            Timber.d("Saved user: %s", Tools.getSharedPreferences(view.getContext()).getUserToEdit().getEmail());
            Intent i = new Intent(context.getApplicationContext(), EditUserActivity.class);
            context.startActivity(i);
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
}
