package com.example.roomThong.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2.R;
import com.example.roomThong.adapter.RoomAdapter;
import com.example.roomThong.model.Room;
import com.example.roomThong.repository.RoomRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnRoomItemClickListener {

    private RecyclerView recyclerViewRooms;
    private RoomAdapter adapter;
    private RoomRepository repository;

    private final ActivityResultLauncher<Intent> roomDetailLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Room room = (Room) result.getData().getSerializableExtra("ROOM_RESULT");
                    int position = result.getData().getIntExtra("POSITION", -1);

                    if (position == -1) {
                        repository.addRoom(room);
                        adapter.notifyItemInserted(repository.getAllRooms().size() - 1);
                        Toast.makeText(this, "Đã thêm phòng mới", Toast.LENGTH_SHORT).show();
                    } else {
                        repository.updateRoom(position, room);
                        adapter.notifyItemChanged(position);
                        Toast.makeText(this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thong);

        repository = RoomRepository.getInstance();
        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        FloatingActionButton fabAddRoom = findViewById(R.id.fabAddRoom);

        adapter = new RoomAdapter(repository.getAllRooms(), this);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRooms.setAdapter(adapter);

        fabAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(this, RoomDetailActivity.class);
            roomDetailLauncher.launch(intent);
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RoomDetailActivity.class);
        intent.putExtra("ROOM_DATA", repository.getAllRooms().get(position));
        intent.putExtra("POSITION", position);
        roomDetailLauncher.launch(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa phòng")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    repository.deleteRoom(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, repository.getAllRooms().size());
                    Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
