package com.btln9.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.btln9.qlsv.database.Common;
import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.NhanVien_PhongBan;
import com.btln9.qlsv.model.PhongBan;

import java.util.ArrayList;
import java.util.List;

public class NhanVienPhongBanDetail extends AppCompatActivity {
    NhanVienPhongBanAdapter nhanVienPhongBanAdapter;
    EditText editNameEmployee;
    EditText editNameOffice;
    Button btnCreateEmployeeOffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhanvien_phongban);
        editNameEmployee = findViewById(R.id.input_name_employee);
        editNameOffice = findViewById(R.id.input_name_office);
        btnCreateEmployeeOffice = findViewById(R.id.btnCreateEmployeeOffice);
        btnCreateEmployeeOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(getBaseContext());
                String nameEmployee = editNameEmployee.getText().toString().trim();
                String nameOffice = editNameOffice.getText().toString().trim();

                if (nameEmployee.isEmpty() || nameOffice.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                NhanVien nhanVien = Common.findElementInListByName(db.getAllNhanVien(), NhanVien::getName, nameEmployee);
                PhongBan phongBan = Common.findElementInListByName(db.getAllPhongBan(), PhongBan::getName, nameOffice);

                if (nhanVien != null && phongBan != null) {
                    NhanVien_PhongBan nhanVienPhongBan = new NhanVien_PhongBan(nhanVien.getId(), phongBan.getId());
                    db.DangKyNhanVien(nhanVienPhongBan);
                    Toast.makeText(getBaseContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    reset();
                    setResult(RESULT_OK, null);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
        loadList();
    }
        private void loadList() {
        DbHelper db = new DbHelper(getBaseContext());
        List<NhanVien_PhongBan> list = db.getAllNhanVienPhongBan();
        nhanVienPhongBanAdapter = new NhanVienPhongBanAdapter(this, R.layout.item_nhanvien_phongban, list);
        ListView listView = findViewById(R.id.listEmployeeOffice);
        listView.setAdapter(nhanVienPhongBanAdapter);
    }

    protected void reset() {
        editNameEmployee.setText("");
        editNameOffice.setText("");
    }
}