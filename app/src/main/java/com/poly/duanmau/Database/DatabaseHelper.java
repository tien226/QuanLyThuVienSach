package com.poly.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.poly.duanmau.DAO.HoaDonChiTietDao;
import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.DAO.TheLoaiDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_Name = "qlsfpoly.db";
    public static final int Database_Version= 1;
    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NguoiDungDao.SQL_NGUOI_DUNG);
        db.execSQL(TheLoaiDao.SQL_THE_LOAI);
        db.execSQL(SachDao.SQL_SACH);
        db.execSQL(HoaDonDao.SQL_HOA_DON);
        db.execSQL(HoaDonChiTietDao.SQL_HDCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // xóa bảng cũ và tạo bảng mới
        db.execSQL("Drop table if exists " + NguoiDungDao.TABLE_NGUOIDUNG);
        db.execSQL("Drop table if exists " + TheLoaiDao.TABLE_THELOAI);
        db.execSQL("Drop table if exists " + SachDao.TABLE_SACH);
        db.execSQL("Drop table if exists " + HoaDonDao.TABLE_HOADON);
        db.execSQL("Drop table if exists " + HoaDonChiTietDao.TABLE_HODONCHITIET);
        onCreate(db);
    }
}
