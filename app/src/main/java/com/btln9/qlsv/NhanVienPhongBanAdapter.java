package com.btln9.qlsv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btln9.qlsv.database.Common;
import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.Em_Off;
import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.NhanVien_PhongBan;
import com.btln9.qlsv.model.PhongBan;

import java.util.List;
import java.util.Optional;

public class NhanVienPhongBanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Em_Off> emOffs;

    public NhanVienPhongBanAdapter(Context context, int layout, List<Em_Off> Em_Off) {
        this.context = context;
        this.layout = layout;
        this.emOffs = Em_Off;
    }

    @Override
    public int getCount() {
        return emOffs.size();
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

        TextView name_employee = (TextView) view.findViewById(R.id.nvpb_name_employee);
        TextView name_office = (TextView) view.findViewById(R.id.nvpb_name_office);
        Em_Off emOff = emOffs.get(i);

//        NhanVien nhanVien = Common.findElementInListById(dbHelper.getAllNhanVien(), NhanVien::getId, nhanVienPhongBan.getId_nv());
//        PhongBan phongBan = Common.findElementInListById(dbHelper.getAllPhongBan(), PhongBan::getId, nhanVienPhongBan.getId_pb());
//        List<NhanVien_PhongBan> list = dbHelper.getAllNhanVienPhongBan();
//        if (emOffs != null) {
            name_employee.setText("Ganacho");
            name_office.setText("ke toan");
//        }

        return view;
    }
}
