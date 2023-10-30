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
        List<PhongBan> list = db.getAllPhongBan();
//        ArrayAdapter<PhongBan> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        phongBanAdapter = new PhongBanAdapter(this, R.layout.item_phongban, list);
        ListView listView = findViewById(R.id.listPhongBan);
        listView.setAdapter(phongBanAdapter);
    }
    protected void reset() {
        editName.setText("");
        editDescription.setText("");
    }


}