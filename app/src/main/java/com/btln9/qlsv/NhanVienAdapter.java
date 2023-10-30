package com.btln9.qlsv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                // Hiển thị Toast khi nút Xóa được click
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Bạn có muốn xóa món " + nhanVien.getName() + " không?");
                dialog.setPositiveButton("Có", (dialogInterface, i) -> {

                    Toast.makeText(context, "Xóa " + nhanVien.getName() + nhanVien.getId(), Toast.LENGTH_SHORT).show();
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
                dialog.setMessage("Bạn có muốn xóa món " + nhanVien.getName() + " không?");
                dialog.setPositiveButton("Sửa", (dialogInterface, i) -> {

                    Toast.makeText(context, "Sửa thành công " + nhanVien.getName() + nhanVien.getId(), Toast.LENGTH_SHORT).show();
//                    HomeActivity.dao.deleteFoodSavedByFoodIdAndSize(foodSize.getFoodId(), foodSize.getSize());
//                    SavedFragment.saved_container.removeView(this);

                });
                dialog.setNegativeButton("Hủy", (dialogInterface, i) -> {
                });
                dialog.show();
            }
        });


        return view;
    }
}
