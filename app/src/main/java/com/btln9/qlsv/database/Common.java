//package com.btln9.qlsv.database;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Common {
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
//}
