package com.btln9.qlsv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btln9.qlsv.database.Common;
import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.Em_Of;
import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.NhanVien_PhongBan;
import com.btln9.qlsv.model.PhongBan;

import java.util.List;
import java.util.Optional;

public class NhanVienPhongBanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Em_Of> emOfs;

    public NhanVienPhongBanAdapter(Context context, int layout, List<Em_Of> emOfs) {
        this.context = context;
        this.layout = layout;
        this.emOfs = emOfs;
    }

    @Override
    public int getCount() {
        return emOfs.size();
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
        Em_Of emOf = emOfs.get(i);

//        NhanVien nhanVien = Common.findElementInListById(dbHelper.getAllNhanVien(), NhanVien::getId, nhanVienPhongBan.getId_nv());
//        PhongBan phongBan = Common.findElementInListById(dbHelper.getAllPhongBan(), PhongBan::getId, nhanVienPhongBan.getId_pb());
//        List<NhanVien_PhongBan> list = dbHelper.getAllNhanVienPhongBan();
//        if (nhanVien != null && phongBan != null) {
            name_employee.setText(emOf.getNameEm());
            name_office.setText(emOf.getNameOf());
//        }

        return view;
    }
}
