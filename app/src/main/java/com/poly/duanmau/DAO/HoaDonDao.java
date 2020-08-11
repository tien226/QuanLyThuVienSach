package com.poly.duanmau.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.HoaDon;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public static final String TABLE_HOADON = "hoadon";
    public static final String COLUMN_MA_HD = "mahoadon";
    public static final String COLUMN_NGAY_MUA = "ngaymua";
    public static final String SQL_HOA_DON = "CREATE TABLE " + TABLE_HOADON + " ( " +
            COLUMN_MA_HD + " text primary key, " + COLUMN_NGAY_MUA + " date);";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonDao(Context context) {
        databaseHelper= new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    //thêm hóa đơn
    public long insertHoaDon(HoaDon hoaDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_HD, hoaDon.getMaHoaDon());
        contentValues.put(COLUMN_NGAY_MUA, sdf.format(hoaDon.getNgayMua()));

        return sqLiteDatabase.insert(TABLE_HOADON, null, contentValues);
    }

    //sửa hóa đơn
    public long updateHoaDon(HoaDon hoaDon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MA_HD, hoaDon.getMaHoaDon());
        contentValues.put(COLUMN_NGAY_MUA, sdf.format(hoaDon.getNgayMua()));

        return sqLiteDatabase.update(TABLE_HOADON, contentValues, COLUMN_MA_HD + "=?", new String[]{hoaDon.getMaHoaDon()});
    }

    //xóa hóa đơn
    public long deleteHoaDon(String mahoadon) {
        return sqLiteDatabase.delete(TABLE_HOADON, COLUMN_MA_HD + "=?", new String[]{mahoadon});
    }

    //lấy toàn bộ danh sách hóa đơn
    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String Select = "SELECT * FROM " + TABLE_HOADON;
        Cursor cursor = sqLiteDatabase.rawQuery(Select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(cursor.getString(cursor.getColumnIndex(COLUMN_MA_HD)));
            try {
                hoaDon.setNgayMua((Date) sdf.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_MUA))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hoaDonList.add(hoaDon);
            cursor.moveToNext();
        }
        cursor.close();
        return hoaDonList;
    }
}
