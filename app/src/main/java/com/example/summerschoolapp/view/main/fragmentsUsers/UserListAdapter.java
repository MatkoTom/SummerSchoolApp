package com.example.summerschoolapp.view.main.fragmentsUsers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
//    private List<User> userListFull;

    public void setData(List<User> newData) {
        if (newData != null && !newData.isEmpty()) {
            Collections.sort(newData, (user, t1) -> user.getEmail().compareTo(t1.getEmail()));
            this.data.clear();
            this.data.addAll(newData);
//            userListFull = new ArrayList<>(data);
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
        holder.tvUserEmail.setText(item.getEmail());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }

//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<User> filteredList = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0) {
//                filteredList.addAll(userListFull);
//            } else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//
//                for (User user : userListFull) {
//                    if (user.getEmail().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(user);
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            data.clear();
//            data.addAll((List) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };

    class CustomVievHolder extends RecyclerView.ViewHolder {

        TextView tvUserName;
        TextView tvUserEmail;
        ImageView ivUSerImg;

        public CustomVievHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserEmail = itemView.findViewById(R.id.tv_user_email);
            ivUSerImg = itemView.findViewById(R.id.iv_user_img);
        }
    }
}
