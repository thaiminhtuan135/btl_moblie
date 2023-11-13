package com.btln9.qlsv.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.btln9.qlsv.model.PhongBan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
public class PhongBanAppInitializer {
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

        PhongBan nhanVien1 = new PhongBan(1, "ke toan", "tinh tien");
        String key1 = String.valueOf(nhanVien1.getId());
        savePhongBanToSharedPreferences(editor, key1, nhanVien1);

        PhongBan nhanVien2 = new PhongBan(2, "nhan su", "quan lý nhan vien");
        String key2 = String.valueOf(nhanVien2.getId());
        savePhongBanToSharedPreferences(editor, key2, nhanVien2);

        editor.apply();
    }

    private static void savePhongBanToSharedPreferences(SharedPreferences.Editor editor, String key, PhongBan nhanVien) {
        Gson gson = new Gson();
        String jsonPhongBan = gson.toJson(nhanVien);
        editor.putString(key, jsonPhongBan);
    }

    public static List<PhongBan> getAllPhongBanFromSharedPreferences(Context context) {
        List<PhongBan> list = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int numberOfPhongBan = 2;

        for (int i = 1; i <= numberOfPhongBan; i++) {
            String key = String.valueOf(i);
            PhongBan nhanVien = getPhongBanFromSharedPreferences(sharedPreferences, key);

            if (nhanVien != null) {
                list.add(nhanVien);
            }
        }
        return list;
    }

    private static PhongBan getPhongBanFromSharedPreferences(SharedPreferences sharedPreferences, String key) {
        String jsonPhongBan = sharedPreferences.getString(key, null);

        if (jsonPhongBan != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonPhongBan, PhongBan.class);
        }

        return null;
    }
}