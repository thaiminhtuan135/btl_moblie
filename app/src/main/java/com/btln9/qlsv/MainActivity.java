package com.btln9.qlsv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnNV, btnPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnNV = findViewById(R.id.btnNV);
        btnPB = findViewById(R.id.btnPB);

        setOnClickListenerWithIntent(btnNV, NhanVienDetail.class, 1);
        setOnClickListenerWithIntent(btnPB, PhongBanDetail.class, 1);

    }

    public void setOnClickListenerWithIntent(View button, Class<?> targetActivity, int requestCode) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), targetActivity);
                startActivityForResult(intent, requestCode);
            }
        });
    }
}