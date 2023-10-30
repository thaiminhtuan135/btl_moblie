package com.btln9.qlsv;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btln9.qlsv.model.PhongBan;

import java.util.List;

public class PhongBanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PhongBan> phongBans;

    public PhongBanAdapter(Context context, int layout, List<PhongBan> phongBans) {
        this.context = context;
        this.layout = layout;
        this.phongBans = phongBans;
    }

    @Override
    public int getCount() {
        return phongBans.size();
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
        Button btnDelete = view.findViewById(R.id.btnDeleteOffice);
        Button btnEdit = view.findViewById(R.id.btnEditOffice);

        TextView name = (TextView) view.findViewById(R.id.name_office);
        TextView description = (TextView) view.findViewById(R.id.description_office);
        PhongBan phongBan = phongBans.get(i);

        name.setText(phongBan.getName());
        description.setText(phongBan.getDescription());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị Toast khi nút Xóa được click
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Bạn có muốn xóa phòng ban " + phongBan.getName() + " không?");
                dialog.setPositiveButton("Có", (dialogInterface, i) -> {

                    Toast.makeText(context, "Xóa " + phongBan.getName() + phongBan.getId(), Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_phongban, null);
                EditText editTextName = (dialogView.findViewById(R.id.edit_text_name_office));
                EditText editTextDescription = (dialogView.findViewById(R.id.edit_text_description_office));
                editTextName.setText(phongBan.getName());
                editTextDescription.setText(phongBan.getDescription());
                dialog.setView(dialogView);

                dialog.setMessage("Bạn có muốn xóa món " + phongBan.getName() + " không?");
                dialog.setPositiveButton("Sửa", (dialogInterface, i) -> {

                    Toast.makeText(context, "Sửa thành công " + phongBan.getName() + phongBan.getId(), Toast.LENGTH_SHORT).show();
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
