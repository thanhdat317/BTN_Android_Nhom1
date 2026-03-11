package com.example.baitapnhom1;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> rooms;
    private OnRoomClickListener listener;

    public interface OnRoomClickListener {
        void onItemClick(Room room, int position);
        void onItemLongClick(Room room, int position);
    }

    public RoomAdapter(List<Room> rooms, OnRoomClickListener listener) {
        this.rooms = rooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.tvRoomName.setText(room.getName());
        holder.tvPrice.setText("Giá: " + String.format("%.0f", room.getPrice()) + " VND");
        
        if (room.isRented()) {
            holder.tvStatus.setText("Trạng thái: Đã thuê");
            holder.tvStatus.setTextColor(Color.RED);
            holder.tvTenantInfo.setVisibility(View.VISIBLE);
            holder.tvTenantInfo.setText("Người thuê: " + room.getTenantName() + " (" + room.getTenantPhone() + ")");
        } else {
            holder.tvStatus.setText("Trạng thái: Còn trống");
            holder.tvStatus.setTextColor(Color.GREEN);
            holder.tvTenantInfo.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(room, position));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(room, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomName, tvPrice, tvStatus, tvTenantInfo;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTenantInfo = itemView.findViewById(R.id.tvTenantInfo);
        }
    }
}
