package com.btln9.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.NhanVien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhanVienDetail extends AppCompatActivity {
    EditText editName;
    EditText editBirthday;
    EditText editAddress;
    Button btnCreateEmployee;
    Button btnLK;
    NhanVienAdapter nhanVienAdapter;
    Button btnUpdateEmployee;
    Button btnDeleteEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhan_vien);

        editName = findViewById(R.id.editTenNhanVien);
        editBirthday = findViewById(R.id.editNgaySinh);
        editAddress = findViewById(R.id.editQueQuan);

        btnCreateEmployee = findViewById(R.id.btnCreateEmployee);
        btnLK = findViewById(R.id.btnLK);

        btnCreateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(getBaseContext());
                String name = editName.getText().toString().trim();
                String birthday = editBirthday.getText().toString().trim();
                String address = editAddress.getText().toString().trim();

                if(name.isEmpty() || birthday.isEmpty() || address.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidDateFormat(birthday)) {
                    Toast.makeText(getBaseContext(), "Vui lòng điền đúng định dạng ngày sinh", Toast.LENGTH_SHORT).show();
                    return;
                }

                NhanVien nhanVien = new NhanVien(name, birthday, address);
//                db.addToFile(sinhVien,"sv.txt");
                db.addNhanVien(nhanVien);
                Toast.makeText(getBaseContext(), "Add Success", Toast.LENGTH_LONG).show();
                reset();
                setResult(RESULT_OK, null);
                finish();
            }
        });

        loadListNhanVien();

        btnLK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lietKe();
            }
        });

    }
    protected void reset() {
        editName.setText("");
        editBirthday.setText("");
        editAddress.setText("");
    }
    private void loadListNhanVien() {
        DbHelper db = new DbHelper(getBaseContext());
        List<NhanVien> list = readDataFromTxtFile();
//        ArrayAdapter<NhanVien> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        nhanVienAdapter = new NhanVienAdapter(this, R.layout.item_nhanvien, list);
        ListView listView = findViewById(R.id.listNhanVien);
        listView.setAdapter(nhanVienAdapter);
    }
    private List<NhanVien> readDataFromTxtFile() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.nhanvien);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 4) {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setId(Integer.parseInt(data[0]));
                    nhanVien.setName(data[1]);
                    nhanVien.setBirthday(data[2]);
                    nhanVien.setAddress(data[3]);
                    nhanVienList.add(nhanVien);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nhanVienList;
    }
    private void lietKe() {
        DbHelper db = new DbHelper(getBaseContext());
        List<NhanVien> list = db.getAllNhanVienByAddress();
        nhanVienAdapter = new NhanVienAdapter(this, R.layout.item_nhanvien, list);
//        ArrayAdapter<NhanVien> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = findViewById(R.id.listNhanVien);
        listView.setAdapter(nhanVienAdapter);
    }
    public static boolean isValidDateFormat(String date) {
        Pattern pattern = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
        Matcher matcher = pattern.matcher(date);

        if (!matcher.matches()) {
            return false;
        }

        Calendar currentCalendar = Calendar.getInstance();
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);

        int year = Integer.parseInt(date.substring(6));
        int month = Integer.parseInt(date.substring(3, 5));
        int day = Integer.parseInt(date.substring(0, 2));

        if (year > currentYear || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }

        if (year == currentYear && month > currentMonth) {
            return false;
        }

        if (year == currentYear && month == currentMonth && day > currentDay) {
            return false;
        }

        return true;
    }
}