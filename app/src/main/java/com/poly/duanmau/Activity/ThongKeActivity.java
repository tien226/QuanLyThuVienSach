package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import com.poly.duanmau.DAO.HoaDonChiTietDao;
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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // ánh xạ
        tvNgay = (TextView) findViewById(R.id.tvThongKeNgay);
        tvThang = (TextView) findViewById(R.id.tvThongKeThang);
        tvNam = (TextView) findViewById(R.id.tvThongKeNam);

        //lấy doanh thu theo ngày, tháng, năm
        hoaDonChiTietDAO = new HoaDonChiTietDao(ThongKeActivity.this);
        tvNgay.setText("Hôm Nay: "+hoaDonChiTietDAO.getDoanhThuTheoNgay()+" VNĐ");
        tvThang.setText("Tháng Này: "+hoaDonChiTietDAO.getDoanhThuTheoThang()+" VNĐ");
        tvNam.setText("Năm Nay: "+hoaDonChiTietDAO.getDoanhThuTheoNam()+" VNĐ");

    }
}
