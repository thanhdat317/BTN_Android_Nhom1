package com.example.tuan7;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tuan7.adapter.RoomAdapter;
import com.example.tuan7.model.Room;
import com.example.tuan7.repository.RoomRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvRooms;
    private RoomAdapter adapter;
    private RoomRepository repository;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = RoomRepository.getInstance();
        initViews();
        setupRecyclerView();

        fabAdd.setOnClickListener(v -> {
            // Demo: Thêm nhanh một phòng để kiểm tra RecyclerView
            String id = UUID.randomUUID().toString();
            Room newRoom = new Room(id, "Phòng " + id.substring(0, 5), 2500000, false, "", "");
            repository.addRoom(newRoom);
            adapter.updateData(repository.getAllRooms());
            Toast.makeText(this, "Đã thêm phòng mới", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViews() {
        rvRooms = findViewById(R.id.rvRooms);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void setupRecyclerView() {
        adapter = new RoomAdapter(repository.getAllRooms());
        rvRooms.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData(repository.getAllRooms());
    }
}
