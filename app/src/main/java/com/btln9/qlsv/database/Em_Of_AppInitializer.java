package com.btln9.qlsv.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.btln9.qlsv.model.Em_Off;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Em_Of_AppInitializer {
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

        Em_Off emOff1 = new Em_Off(1, "Ganacho", "ke toan");
        String key1 = String.valueOf(emOff1.getId());
        saveEm_OffToSharedPreferences(editor, key1, emOff1);


        editor.apply();
    }

    private static void saveEm_OffToSharedPreferences(SharedPreferences.Editor editor, String key, Em_Off emOff) {
        Gson gson = new Gson();
        String jsonEm_Off = gson.toJson(emOff);
        editor.putString(key, jsonEm_Off);
    }
    public static List<Em_Off> getAllEm_OffFromSharedPreferences(Context context) {
        List<Em_Off> list = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        int numberOfEm_Off = 1;

        for (int i = 1; i <= numberOfEm_Off; i++) {
            String key = String.valueOf(i);
            Em_Off emOff = getEm_OffFromSharedPreferences(sharedPreferences, key);

            if (emOff != null) {
                list.add(emOff);
            }
        }
        return list;
    }
    private static Em_Off getEm_OffFromSharedPreferences(SharedPreferences sharedPreferences, String key) {
        String jsonEm_Off = sharedPreferences.getString(key, null);

        if (jsonEm_Off != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonEm_Off, Em_Off.class);
        }
        return null;
    }
}

