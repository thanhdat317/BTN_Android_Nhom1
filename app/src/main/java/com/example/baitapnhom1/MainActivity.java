package com.example.baitapnhom1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RoomAdapter.OnRoomClickListener {

    private RecyclerView rvRooms;
    private RoomAdapter adapter;
    private RoomRepository repository;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = RoomRepository.getInstance();
        roomList = repository.getAllRooms();

        rvRooms = findViewById(R.id.rvRooms);
        rvRooms.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new RoomAdapter(roomList, this);
        rvRooms.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showAddEditDialog(null, -1));
    }

    // Nhiệm vụ của Hiệp (Create) và Văn (Update)
    private void showAddEditDialog(Room room, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_room, null);
        builder.setView(view);

        EditText etRoomId = view.findViewById(R.id.etRoomId);
        EditText etRoomName = view.findViewById(R.id.etRoomName);
        EditText etPrice = view.findViewById(R.id.etPrice);
        CheckBox cbIsRented = view.findViewById(R.id.cbIsRented);
        EditText etTenantName = view.findViewById(R.id.etTenantName);
        EditText etTenantPhone = view.findViewById(R.id.etTenantPhone);

        boolean isEdit = (room != null);
        builder.setTitle(isEdit ? "Sửa thông tin phòng" : "Thêm phòng mới");

        if (isEdit) {
            etRoomId.setText(room.getId());
            etRoomId.setEnabled(false); // Không cho sửa mã phòng
            etRoomName.setText(room.getName());
            etPrice.setText(String.valueOf(room.getPrice()));
            cbIsRented.setChecked(room.isRented());
            etTenantName.setText(room.getTenantName());
            etTenantPhone.setText(room.getTenantPhone());
        }

        builder.setPositiveButton(isEdit ? "Cập nhật" : "Thêm", (dialog, which) -> {
            String id = etRoomId.getText().toString().trim();
            String name = etRoomName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();
            boolean isRented = cbIsRented.isChecked();
            String tenantName = etTenantName.getText().toString().trim();
            String tenantPhone = etTenantPhone.getText().toString().trim();

            // Validate dữ liệu (Nhiệm vụ của Hiệp)
            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá thuê phải là số", Toast.LENGTH_SHORT).show();
                return;
            }

            Room newRoom = new Room(id, name, price, isRented, tenantName, tenantPhone);

            if (isEdit) {
                // Nhiệm vụ của Văn (Update)
                repository.updateRoom(position, newRoom);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
            } else {
                // Nhiệm vụ của Hiệp (Create)
                repository.addRoom(newRoom);
                adapter.notifyItemInserted(roomList.size() - 1);
                Toast.makeText(this, "Đã thêm phòng", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public void onItemClick(Room room, int position) {
        // Click vào item -> mở màn hình sửa (Nhiệm vụ của Văn)
        showAddEditDialog(room, position);
    }

    @Override
    public void onItemLongClick(Room room, int position) {
        // Nhấn giữ -> Xóa phòng (Nhiệm vụ của Văn)
        showDeleteConfirmDialog(position);
    }

    // Nhiệm vụ của Văn (Delete)
    private void showDeleteConfirmDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa phòng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    repository.deleteRoom(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, roomList.size());
                    Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
