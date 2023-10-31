package com.btln9.qlsv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.NhanVien;

import java.util.List;

public class NhanVienAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<NhanVien> nhanViens;

    public NhanVienAdapter(Context context, int layout, List<NhanVien> nhanViens) {
        this.context = context;
        this.layout = layout;
        this.nhanViens = nhanViens;
    }

    @Override
    public int getCount() {
        return nhanViens.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        DbHelper dbHelper = new DbHelper(context);
        Button btnDelete = view.findViewById(R.id.btnDeleteEmployee);
        Button btnEdit = view.findViewById(R.id.btnEditEmployee);
        // anh xa view
        TextView name = (TextView) view.findViewById(R.id.name_employee);
        TextView birthday = (TextView) view.findViewById(R.id.birthday_employee);
        TextView address = (TextView) view.findViewById(R.id.address_employee);
        NhanVien nhanVien = nhanViens.get(i);

        name.setText(nhanVien.getName());
        birthday.setText(nhanVien.getBirthday());
        address.setText(nhanVien.getAddress());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Bạn có muốn xóa nhân viên " + nhanVien.getName() + " không?");
                dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                    if (dbHelper.deleteNhanVien(nhanVien.getId())) {
                        nhanViens.remove(nhanVien);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa " + nhanVien.getName() + " thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }

//                    HomeActivity.dao.deleteFoodSavedByFoodIdAndSize(foodSize.getFoodId(), foodSize.getSize());
//                    SavedFragment.saved_container.removeView(this);

                });
                dialog.setNegativeButton("Không", (dialogInterface, i) -> {
                });
                dialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị Toast khi nút Xóa được click
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_nhanvien, null);
                EditText editTextName = (dialogView.findViewById(R.id.edit_text_name_employee));
                EditText editTextBirthday = (dialogView.findViewById(R.id.edit_text_birthday_employee));
                EditText editTextAddress = (dialogView.findViewById(R.id.edit_text_address_employee));
                editTextName.setText(nhanVien.getName());
                editTextBirthday.setText(nhanVien.getBirthday());
                editTextAddress.setText(nhanVien.getAddress());
                dialog.setView(dialogView);


                dialog.setPositiveButton("Sửa", (dialogInterface, i) -> {
//                    dialog.setMessage("Bạn có muốn xóa món " + nhanVien.getName() + " không?");

                    String newName = editTextName.getText().toString().trim();
                    String newBirthday = editTextBirthday.getText().toString().trim();
                    String newAddress = editTextAddress.getText().toString().trim();
                    nhanVien.setName(newName);
                    nhanVien.setBirthday(newBirthday);
                    nhanVien.setAddress(newAddress);
                    if (dbHelper.updateNhanVien(nhanVien)) {
                        Toast.makeText(context, "Sửa thành công ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, NhanVienDetail.class);

                    } else {
                        Toast.makeText(context, "Sửa thất bại ", Toast.LENGTH_SHORT).show();
                    }

                });
                dialog.setNegativeButton("Hủy", (dialogInterface, i) -> {
                });
                dialog.show();
            }
        });
        return view;
    }
}
