package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.poly.duanmau.DAO.HoaDonChiTietDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.R;

public class ThongKeActivity extends AppCompatActivity {
    TextView tvNgay, tvThang, tvNam;
    HoaDonChiTietDao hoaDonChiTietDAO;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tong_hop_thong_ke);
        setTitle("Quản Lý Sách");
        toolbar = findViewById(R.id.toolbarthongke);
        tvNgay = (TextView) findViewById(R.id.tvThongKeNgay);
        tvThang = (TextView) findViewById(R.id.tvThongKeThang);
        tvNam = (TextView) findViewById(R.id.tvThongKeNam);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        hoaDonChiTietDAO = new HoaDonChiTietDao(ThongKeActivity.this);

        tvNgay.setText("Hôm Nay: "+hoaDonChiTietDAO.getDoanhThuTheoNgay());
        tvThang.setText("Tháng Này: "+hoaDonChiTietDAO.getDoanhThuTheoThang());
        tvNam.setText("Năm Nay: "+hoaDonChiTietDAO.getDoanhThuTheoNam());

    }
}
