package com.btln9.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.btln9.qlsv.database.Common;
import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.Em_Of;
import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.NhanVien_PhongBan;
import com.btln9.qlsv.model.PhongBan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NhanVienPhongBanDetail extends AppCompatActivity {
    NhanVienPhongBanAdapter nhanVienPhongBanAdapter;
    EditText editNameEmployee;
    EditText editNameOffice;
    Button btnCreateEmployeeOffice;
    Spinner spinnerPhongBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhanvien_phongban);
        DbHelper db = new DbHelper(getBaseContext());
        editNameEmployee = findViewById(R.id.input_name_employee);
        editNameOffice = findViewById(R.id.input_name_office);
        btnCreateEmployeeOffice = findViewById(R.id.btnCreateEmployeeOffice);
        spinnerPhongBan = findViewById(R.id.spinnerPhongBan);
        List<String> phongBanList = new ArrayList<>();
        phongBanList.add("");
        phongBanList.addAll(db.getAllPhongBan().stream().map(PhongBan::getName).collect(Collectors.toList()));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, phongBanList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhongBan.setAdapter(spinnerAdapter);
        btnCreateEmployeeOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
//        spinnerPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                String selectedPhongBan = (String) parentView.getItemAtPosition(position);
//                loadListSearch(selectedPhongBan);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });
        loadList();
    }

    private void loadList() {
        DbHelper db = new DbHelper(getBaseContext());
        List<Em_Of> list = readDataFromTxtFile();
        nhanVienPhongBanAdapter = new NhanVienPhongBanAdapter(this, R.layout.item_nhanvien_phongban, list);
        ListView listView = findViewById(R.id.listEmployeeOffice);
        listView.setAdapter(nhanVienPhongBanAdapter);
    }
    private List<Em_Of> readDataFromTxtFile() {
        List<Em_Of> emOfList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.nhanvien_phongban);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 3) {
                    Em_Of emOf = new Em_Of();
                    emOf.setId(Integer.parseInt(data[0]));
                    emOf.setNameEm(data[1]);
                    emOf.setNameOf(data[2]);
                    emOfList.add(emOf);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emOfList;
    }
//    private void loadListSearch(String nameOffice) {
//
//        DbHelper db = new DbHelper(getBaseContext());
//        PhongBan phongBan = Common.findElementInListByName(db.getAllPhongBan(), PhongBan::getName, nameOffice);
//        if (phongBan != null) {
//            List<NhanVien_PhongBan> list = db.getAllNhanVienPhongBan()
//                    .stream().filter(nhanVienPhongBan -> nhanVienPhongBan.getId_pb() == phongBan.getId())
//                    .collect(Collectors.toList());
//            nhanVienPhongBanAdapter = new NhanVienPhongBanAdapter(this, R.layout.item_nhanvien_phongban, list);
//            ListView listView = findViewById(R.id.listEmployeeOffice);
//            listView.setAdapter(nhanVienPhongBanAdapter);
//        }
//    }

    protected void reset() {
        editNameEmployee.setText("");
        editNameOffice.setText("");
    }
}