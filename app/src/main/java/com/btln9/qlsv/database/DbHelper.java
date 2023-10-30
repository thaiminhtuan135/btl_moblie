package com.btln9.qlsv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.PhongBan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "baitaplon";
    public static final String TABLE_NAME1 = "nhanvien";
    public static final String TABLE_NAME2 = "phongban";
    public static final String TABLE_NAME3 = "nhanvien_phongban";
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME1 + "(id integer primary key autoincrement, name text, birthday text, address text) ";
        db.execSQL(query);
        query = "create table " + TABLE_NAME2 + " (id integer primary key autoincrement, name text, description text)";
        db.execSQL(query);
//        query = "create table " + TABLE_NAME3 + " (id_sinhvien integer, id_lophoc integer, kyHoc int, soTinChi int, foreign key(id_sinhvien) references sinhvien(id), foreign key(id_lophoc) references lop(id))";
//        db.execSQL(query);
        Log.e("DB", "DB");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("drop table if exists " + TABLE_NAME2);
//        db.execSQL("drop table if exists " + TABLE_NAME3);
        onCreate(db);
    }


    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sqlQuery = "Select * from " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            list.add(new NhanVien
                    (
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            );
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<PhongBan> getAllPhongBan() {
        List<PhongBan> list = new ArrayList<>();
        String sqlQuery = "Select * from " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            list.add(new PhongBan
                    (
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    )
            );
        }
        cursor.close();
        db.close();
        return list;
    }
    public List<NhanVien> getAllNhanVienByAddress() {
        List<NhanVien> list = new ArrayList<>();
        String sqlQuery = "Select * from " + TABLE_NAME1 + " where address like '%Hai Phong%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            list.add(new NhanVien
                    (
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            );
        }
        cursor.close();
        db.close();
        return list;
    }

//    public List<NhanVien> getAllNhanVienByAddress() {
//        return getAllData(NhanVien.class, "Select * from " + TABLE_NAME1 + " where address like '%Hải Phòng%'", this.getReadableDatabase());
//    }

   /* public List<PhongBan> getAllPhongBan() {
        return getAllData(PhongBan.class, "Select * from " + TABLE_NAME2, this.getReadableDatabase());
    }
*/

    public <T> List<T> getAllData(Class<T> clazz, String query, SQLiteDatabase db) {
        List<T> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            T item = createObjectFromCursor(cursor, clazz);
            list.add(item);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int addNhanVien(NhanVien nhanVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", nhanVien.getName());
        values.put("birthday", nhanVien.getBirthday());
        values.put("address", nhanVien.getAddress());
        return (int) db.insert(TABLE_NAME1, null, values);
    }
    public int addPhongBan(PhongBan phongBan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", phongBan.getName());
        values.put("description", phongBan.getDescription());
        return (int) db.insert(TABLE_NAME2, null, values);
    }

    // Helper function
    private <T> T createObjectFromCursor(Cursor cursor, Class<T> clazz) {
        try {
            T object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int columnIndex = cursor.getColumnIndex(field.getName());
                if (columnIndex != -1) {
                    field.setAccessible(true);
                    if (field.getType() == int.class) {
                        field.set(object, cursor.getInt(columnIndex));
                    } else if (field.getType() == String.class) {
                        field.set(object, cursor.getString(columnIndex));
                    } // Add more data types as needed
                    field.setAccessible(false);
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
