package com.btln9.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.btln9.qlsv.database.DbHelper;
import com.btln9.qlsv.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhanVienDetail extends AppCompatActivity {
    EditText editName;
    EditText editBirthday;
    EditText editAddress;
    Button btnThemNV;
    Button btnLK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhan_vien);

        editName = findViewById(R.id.editTenNhanVien);
        editBirthday = findViewById(R.id.editNgaySinh);
        editAddress = findViewById(R.id.editQueQuan);

        btnThemNV = findViewById(R.id.btnThemNV);
        btnLK = findViewById(R.id.btnLK);

        btnThemNV.setOnClickListener(new View.OnClickListener() {
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
        List<NhanVien> list = db.getAllNhanVien();
        ArrayAdapter<NhanVien> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = findViewById(R.id.listNhanVien);
        listView.setAdapter(adapter);
    }
    private void lietKe() {
        DbHelper db = new DbHelper(getBaseContext());
        List<NhanVien> list = db.getAllNhanVienByAddress();
        ArrayAdapter<NhanVien> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        ListView listView = findViewById(R.id.listNhanVien);
        listView.setAdapter(adapter);
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