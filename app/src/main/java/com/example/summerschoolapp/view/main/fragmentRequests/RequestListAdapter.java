package com.example.summerschoolapp.view.main.fragmentRequests;

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
import com.example.summerschoolapp.model.Request;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.CustomVievHolder> {

    private List<Request> data = new ArrayList<>();
    private RequestListInteraction listener;

    public RequestListAdapter(RequestListInteraction listener) {
        this.listener = listener;
    }

    public void setData(List<Request> newData) {
        if (newData != null && !newData.isEmpty()) {
            this.data.clear();
            this.data.addAll(newData);
            notifyDataSetChanged();
        }
    }

    public void clearList(List<Request> data) {
        this.data.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestListAdapter.CustomVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_request_list, parent, false);
        return new RequestListAdapter.CustomVievHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter.CustomVievHolder holder, int position) {
        Request item = data.get(position);

        holder.tvRequestTitle.setText(item.getTitle());
        String latitude = String.valueOf(item.getLocation_latitude());
        Timber.d("TEMCOORDS" + item.getLocation_latitude() + " " + item.getLocation_longitude());
        String longitude = String.valueOf(item.getLocation_longitude());
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=15&size=400x400&key=AIzaSyD_S_EijHqDQvHbgkvSzPLz5KD-0dEKNTQ";
        Glide.with(holder.ivRequestLocation.getContext())
                .load(url)
                .into(holder.ivRequestLocation);

        holder.tvRequestType.setText(item.getRequestType());
        holder.tvRequestMessage.setText(item.getMessage());
        holder.tvRequestAddress.setText(item.getAddress());

        holder.rowParentLayout.setOnClickListener(view -> {
            if (listener != null) {
                listener.onRequestClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CustomVievHolder extends RecyclerView.ViewHolder {

        TextView tvRequestTitle;
        TextView tvRequestType;
        TextView tvRequestMessage;
        TextView tvRequestAddress;
        ImageView ivRequestLocation;
        ConstraintLayout rowParentLayout;

        public CustomVievHolder(@NonNull View itemView) {
            super(itemView);

            tvRequestTitle = itemView.findViewById(R.id.tv_request_title);
            tvRequestType = itemView.findViewById(R.id.tv_request_type);
            tvRequestMessage = itemView.findViewById(R.id.tv_request_message);
            tvRequestAddress = itemView.findViewById(R.id.tv_request_address);
            ivRequestLocation = itemView.findViewById(R.id.iv_request_location);
            rowParentLayout = itemView.findViewById(R.id.request_row_parent_layout);
        }
    }

    interface RequestListInteraction {
        void onRequestClicked(Request request);
    }
}
