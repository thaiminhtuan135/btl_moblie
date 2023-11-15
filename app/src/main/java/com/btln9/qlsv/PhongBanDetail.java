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
import com.btln9.qlsv.model.PhongBan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PhongBanDetail extends AppCompatActivity {


    EditText editName;
    EditText editDescription;

    Button btnCreateOffice;
    PhongBanAdapter phongBanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phong_ban);

        editName = findViewById(R.id.editTenPhongBan);
        editDescription = findViewById(R.id.editMoTa);

        btnCreateOffice = findViewById(R.id.btnCreateOffice);

        btnCreateOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(getBaseContext());
                String name = editName.getText().toString().trim();
                String description = editDescription.getText().toString().trim();

                if(name.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Vui lòng điền đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhongBan phongBan = new PhongBan(name, description);
                db.addPhongBan(phongBan);
                Toast.makeText(getBaseContext(), "Add Success", Toast.LENGTH_LONG).show();
                reset();
                setResult(RESULT_OK, null);
                finish();
            }
        });
        loadListPhongBan();

    }
    private void loadListPhongBan() {
        DbHelper db = new DbHelper(getBaseContext());
        List<PhongBan> list = readDataFromTxtFile();
//        ArrayAdapter<PhongBan> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        phongBanAdapter = new PhongBanAdapter(this, R.layout.item_phongban, list);
        ListView listView = findViewById(R.id.listPhongBan);
        listView.setAdapter(phongBanAdapter);
    }
    private List<PhongBan> readDataFromTxtFile() {
        List<PhongBan> phongBanList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.phongban);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length >= 3) {
                    PhongBan phongBan = new PhongBan();
                    phongBan.setId(Integer.parseInt(data[0]));
                    phongBan.setName(data[1]);
                    phongBan.setDescription(data[2]);
                    phongBanList.add(phongBan);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phongBanList;
    }
    protected void reset() {
        editName.setText("");
        editDescription.setText("");
    }


}