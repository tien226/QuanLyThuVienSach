package com.poly.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public static final String TABLE_SACH = "sach";
    public static final String COLUMN_MA_SACH = "masach";
    public static final String COLUMN_MA_THE_LOAI = "matheloai";
    public static final String COLUMN_TEN_SACH = "tensach";
    public static final String COLUMN_TAC_GIA = "tacgia";
    public static final String COLUMN_NXB = "nxb";
    public static final String COLUMN_GIA_BAN = "giaban";
    public static final String COLUMN_SO_LUONG = "soluong";

    public static final String SQL_SACH = "CREATE TABLE " + TABLE_SACH +
            " (" + COLUMN_MA_SACH + " text primary key, " + COLUMN_MA_THE_LOAI +
            " text, " + COLUMN_TEN_SACH + " text, " + COLUMN_TAC_GIA + " text, " +
            COLUMN_NXB + " text, " + COLUMN_GIA_BAN + " double, " + COLUMN_SO_LUONG +
            " integer);";

    public SachDao(Context context) {
        databaseHelper =new DatabaseHelper(context);
        sqLiteDatabase=databaseHelper.getWritableDatabase();
    }

    public long insertSach(Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_SACH, sach.getMaSach());
        contentValues.put(COLUMN_MA_THE_LOAI, sach.getMaTheLoai());
        contentValues.put(COLUMN_TEN_SACH, sach.getTenSach());
        contentValues.put(COLUMN_TAC_GIA, sach.getTacGia());
        contentValues.put(COLUMN_NXB, sach.getNXB());
        contentValues.put(COLUMN_GIA_BAN, sach.getGiaBan());
        contentValues.put(COLUMN_SO_LUONG, sach.getSoLuong());

        return sqLiteDatabase.insert(TABLE_SACH, null, contentValues);
    }

    public long updateSach(String masach,Sach sach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_SACH, sach.getMaSach());
        contentValues.put(COLUMN_MA_THE_LOAI, sach.getMaTheLoai());
        contentValues.put(COLUMN_TEN_SACH, sach.getTenSach());
        contentValues.put(COLUMN_TAC_GIA, sach.getTacGia());
        contentValues.put(COLUMN_NXB, sach.getNXB());
        contentValues.put(COLUMN_GIA_BAN, sach.getGiaBan());
        contentValues.put(COLUMN_SO_LUONG, sach.getSoLuong());

        return sqLiteDatabase.update(TABLE_SACH, contentValues, COLUMN_MA_SACH + "=?", new String[]{masach});
    }

    public long deleteSach(String masach) {
        return sqLiteDatabase.delete(TABLE_SACH, COLUMN_MA_SACH + "=?", new String[]{masach});
    }

    public List<Sach> getAllSach() {
//        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Sach> sachList = new ArrayList<>();
        String Select = "SELECT * FROM " + TABLE_SACH;
        Cursor cursor = sqLiteDatabase.rawQuery(Select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Sach sach = new Sach();
            sach.setMaSach(cursor.getString(cursor.getColumnIndex(COLUMN_MA_SACH)));
            sach.setMaTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_MA_THE_LOAI)));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_SACH)));
            sach.setTacGia(cursor.getString(cursor.getColumnIndex(COLUMN_TAC_GIA)));
            sach.setNXB(cursor.getString(cursor.getColumnIndex(COLUMN_NXB)));
            sach.setGiaBan(cursor.getDouble(cursor.getColumnIndex(COLUMN_GIA_BAN)));
            sach.setSoLuong(cursor.getInt(cursor.getColumnIndex(COLUMN_SO_LUONG)));
            sachList.add(sach);
            cursor.moveToNext();
        }
        cursor.close();
        return sachList;
    }

    // truy vấn top sách
    public List<Sach> getSachTop10(String month) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        List<Sach> dsSach = new ArrayList<>();
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        String sSQL = "SELECT masach, SUM(soluongmua) as soluong FROM hoadonchitiet INNER JOIN hoadon " +
                "ON hoadon.mahoadon = hoadonchitiet.mahoadon WHERE strftime('%m',hoadon.ngaymua) = '" +
                month + "' " + "GROUP BY masach ORDER BY soluong DESC LIMIT 10";
        Cursor c = sqLiteDatabase.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Sach s = new Sach();
            s.setMaSach(c.getString(c.getColumnIndex(COLUMN_MA_SACH)));
            s.setSoLuong(c.getInt(c.getColumnIndex(COLUMN_SO_LUONG)));
            s.setMaTheLoai("");
            s.setTenSach("");
            s.setTacGia("");
            s.setNXB("");
            dsSach.add(s);
            c.moveToNext();
        }
        c.close();
        return dsSach;
    }
}
