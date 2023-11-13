package com.btln9.qlsv.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.btln9.qlsv.model.NhanVien;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NhanVienAppInitializer {
    private static final String PREF_NAME = "my_preferences";
    private static final String KEY_INITIALIZED = "initialized";

    public static void initialize(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        boolean initialized = sharedPreferences.getBoolean(KEY_INITIALIZED, false);

        if (!initialized) {
            createSampleData(context);

            sharedPreferences.edit().putBoolean(KEY_INITIALIZED, true).apply();
        }
    }
    private static void createSampleData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        NhanVien nhanVien1 = new NhanVien(1, "John Doe", "01/01/1990", "123 Main St");
        String key1 = String.valueOf(nhanVien1.getId());
        saveNhanVienToSharedPreferences(editor, key1, nhanVien1);

        NhanVien nhanVien2 = new NhanVien(2, "Ganacho", "02/02/1995", "456 Oak St");
        String key2 = String.valueOf(nhanVien2.getId());
        saveNhanVienToSharedPreferences(editor, key2, nhanVien2);

        editor.apply();
    }

    private static void saveNhanVienToSharedPreferences(SharedPreferences.Editor editor, String key, NhanVien nhanVien) {
        Gson gson = new Gson();
        String jsonNhanVien = gson.toJson(nhanVien);
        editor.putString(key, jsonNhanVien);
    }
    public static List<NhanVien> getAllNhanVienFromSharedPreferences(Context context) {
        List<NhanVien> list = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int numberOfNhanVien = 2;

        for (int i = 1; i <= numberOfNhanVien; i++) {
            String key = String.valueOf(i);
            NhanVien nhanVien = getNhanVienFromSharedPreferences(sharedPreferences, key);

            if (nhanVien != null) {
                list.add(nhanVien);
            }
        }
        return list;
    }
    private static NhanVien getNhanVienFromSharedPreferences(SharedPreferences sharedPreferences, String key) {
        String jsonNhanVien = sharedPreferences.getString(key, null);

        if (jsonNhanVien != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonNhanVien, NhanVien.class);
        }
        return null;
    }
}
