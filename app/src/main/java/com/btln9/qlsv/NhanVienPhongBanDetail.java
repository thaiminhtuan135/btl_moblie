package com.btln9.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.NhanVien_PhongBan;
import com.btln9.qlsv.model.PhongBan;

import java.util.ArrayList;
import java.util.List;

public class NhanVienPhongBanDetail extends AppCompatActivity {
    NhanVienPhongBanAdapter nhanVienPhongBanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhanvien_phongban);
        loadList();
    }
    private void loadList() {
        DbHelper db = new DbHelper(getBaseContext());
//        List<PhongBan> list = db.getAllPhongBan();
        List<NhanVien_PhongBan> list = new ArrayList<>();
        list.add(new NhanVien_PhongBan(2, 3));
        list.add(new NhanVien_PhongBan(1, 1));
        list.add(new NhanVien_PhongBan(4, 8));
//        ArrayAdapter<PhongBan> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        nhanVienPhongBanAdapter = new NhanVienPhongBanAdapter(this, R.layout.item_nhanvien_phongban, list);
        ListView listView = findViewById(R.id.listEmployeeOffice);
        listView.setAdapter(nhanVienPhongBanAdapter);
    }
//    protected void reset() {
//        editName.setText("");
//        editDescription.setText("");
//    }
}