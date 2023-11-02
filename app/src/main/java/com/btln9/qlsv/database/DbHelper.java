package com.btln9.qlsv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.btln9.qlsv.model.NhanVien;
import com.btln9.qlsv.model.NhanVien_PhongBan;
import com.btln9.qlsv.model.PhongBan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kydong";
    public static final String TABLE_NAME1 = "nhanvien";
    public static final String TABLE_NAME2 = "phongban";
    public static final String TABLE_NAME3 = "employee_office";
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
        query = "create table " + TABLE_NAME3 + " (id integer primary key autoincrement , id_nv integer, id_pb integer)";
//        query = "create table " + TABLE_NAME3 + " (id integer primary key autoincrement , id_nv integer, id_pb integer, foreign key(id_nv) references nhanvien(id), foreign key(id_pb) references phongban(id))";
        db.execSQL(query);
        Log.e("DB", "DB");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("drop table if exists " + TABLE_NAME2);
        db.execSQL("drop table if exists " + TABLE_NAME3);
        onCreate(db);
    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getDataRow(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        return c;
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
    public List<NhanVien_PhongBan> getAllNhanVienPhongBan() {
        List<NhanVien_PhongBan> list = new ArrayList<>();
        String sqlQuery = "Select * from " + TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        while (cursor.moveToNext()) {
            list.add(new NhanVien_PhongBan
                    (
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2)
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


    public int addNhanVien(NhanVien nhanVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", nhanVien.getName());
        values.put("birthday", nhanVien.getBirthday());
        values.put("address", nhanVien.getAddress());
        return (int) db.insert(TABLE_NAME1, null, values);
    }

    public NhanVien getNhanVienByName(String name) {
//        String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE name='" + id;
        String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE name = '" + name + "'";
        Cursor cursor = getDataRow(query);

        if (cursor.getCount() > 0) {
            return new NhanVien(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        return null;
    }
    public PhongBan getPhongBanByName(String name) {
//        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE id='" + id;
        String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE name = '" + name + "'";
        Cursor cursor = getDataRow(query);

        if (cursor.getCount() > 0) {
            return new PhongBan(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }
        return null;
    }

    public boolean updateNhanVien(NhanVien nhanVien) {
        String query = "UPDATE " + TABLE_NAME1 + " SET name='" + nhanVien.getName() + "', birthday='" + nhanVien.getBirthday() + "', address='" + nhanVien.getAddress() + "' WHERE id=" + nhanVien.getId();

        try {
            queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
//        return query;
    }

    public boolean deleteNhanVien(Integer id) {
        String query = "DELETE FROM " + TABLE_NAME1 + " WHERE id=" + id;
        try {
            queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean updatePhongBan(PhongBan phongBan) {
        String query = "UPDATE " + TABLE_NAME2 + " SET name='" + phongBan.getName() + "', description='" + phongBan.getDescription() + "' WHERE id=" + phongBan.getId();
        try {
            queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
//        return query;
    }

    public boolean deletePhongBan(Integer id) {
        String query = "DELETE FROM " + TABLE_NAME2 + " WHERE id=" + id;
        try {
            queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public int addPhongBan(PhongBan phongBan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", phongBan.getName());
        values.put("description", phongBan.getDescription());
        return (int) db.insert(TABLE_NAME2, null, values);
    }

    public int DangKyNhanVien(NhanVien_PhongBan nhanVienPhongBan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_nv", nhanVienPhongBan.getId_nv());
        values.put("id_pb", nhanVienPhongBan.getId_pb());
//        db.insert(TABLE_NAME3, null, values);
        return (int) db.insert(TABLE_NAME3, null, values);
    }
    // Helper function
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
//    public <T> List<T> getAllData(Class<T> clazz, String query, SQLiteDatabase db) {
//        List<T> list = new ArrayList<>();
//        Cursor cursor = db.rawQuery(query, null);
//        while (cursor.moveToNext()) {
//            T item = createObjectFromCursor(cursor, clazz);
//            list.add(item);
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME1, null, null);
        db.delete(TABLE_NAME2, null, null);
        db.delete(TABLE_NAME3, null, null);
        db.close();
    }

}
