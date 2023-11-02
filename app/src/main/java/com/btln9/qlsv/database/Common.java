package com.btln9.qlsv.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.PhongBan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Common {
    //    public <T> List<T> getAllData(Class<T> clazz, String query) {
//        List<T> list = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = getCursor(query);
//        while (cursor.moveToNext()) {
//            T item = createObjectFromCursor(cursor, clazz);
//            list.add(item);
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
//    private <T> T createObjectFromCursor(Cursor cursor, Class<T> clazz) {
//        try {
//            T object = clazz.newInstance();
//            Field[] fields = clazz.getDeclaredFields();
//            for (Field field : fields) {
//                int columnIndex = cursor.getColumnIndex(field.getName());
//                if (columnIndex != -1) {
//                    field.setAccessible(true);
//                    if (field.getType() == int.class) {
//                        field.set(object, cursor.getInt(columnIndex));
//                    } else if (field.getType() == String.class) {
//                        field.set(object, cursor.getString(columnIndex));
//                    } // Add more data types as needed
//                    field.setAccessible(false);
//                }
//            }
//            return object;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public Cursor getCursor(String query) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        return db.rawQuery(query, null);
//    }

    public static <T> T findElementInListById(List<T> list, Function<T, Integer> getId, int checkId) {
        return list.stream()
                .filter(t -> getId.apply(t) == checkId)
                .findFirst()
                .orElse(null);
    }

    public static <T> T findElementInListByName(List<T> list, Function<T, String> getName, String checkName) {
        return list.stream()
                .filter(t -> Objects.equals(getName.apply(t), checkName))
                .findFirst()
                .orElse(null);
    }
}
