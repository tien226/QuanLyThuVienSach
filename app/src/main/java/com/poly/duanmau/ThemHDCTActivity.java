package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.duanmau.DAO.HoaDonChiTietDao;
import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.Model.HoaDonChiTiet;
import com.poly.duanmau.Model.Sach;

import java.util.List;

public class ThemHDCTActivity extends AppCompatActivity {
    EditText edt_mahd_HDCT, edt_masach_HDCT, edt_soluong_HDCT;
    TextView tv_thanhtoan_HDCT;
    Button btn_themHDCT, btn_thanhtoanHDCT;
//    DatabaseHelper databaseHelper;
    HoaDonDao hoaDonDAO;
    SachDao sachDAO;
    HoaDonChiTietDao hoaDonChiTietDAO;
    int soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hdct);
        setTitle("Thêm hóa đơn chi tiết");
        edt_mahd_HDCT = findViewById(R.id.edt_mahd_HDCT);
        edt_masach_HDCT = findViewById(R.id.edt_masach_HDCT);
        edt_soluong_HDCT = findViewById(R.id.edt_soluong_HDCT);
        tv_thanhtoan_HDCT = findViewById(R.id.tv_thanhTienHDCT);
        btn_themHDCT = findViewById(R.id.btn_themHDCT);
        btn_thanhtoanHDCT = findViewById(R.id.btn_thanhtoanHDCT);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataHD");
        final String maHD = bundle.getString("maHD");
        edt_mahd_HDCT.setText(maHD);

        btn_thanhtoanHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timHoaDon(maHD) == 0) {
                    Toast.makeText(ThemHDCTActivity.this, "Không tìm thấy mã hóa đơn", Toast.LENGTH_SHORT).show();
                }
                String masach = edt_masach_HDCT.getText().toString();
                soluong = Integer.valueOf(edt_soluong_HDCT.getText().toString());
                double a = timSach(masach, soluong);
                if (a == 2) {
                    Toast.makeText(ThemHDCTActivity.this, "Quá số lượng hiện có", Toast.LENGTH_SHORT).show();
                }
                if (a != 0 && a != 2) {
                    double thanhtien = a * soluong;
                    tv_thanhtoan_HDCT.setText("Thành tiền : " + thanhtien);
                }
            }
        });
        btn_themHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseHelper = new DatabaseHelper(ThemHDCTActivity.this);
                hoaDonChiTietDAO = new HoaDonChiTietDao(ThemHDCTActivity.this);
                hoaDonDAO = new HoaDonDao(ThemHDCTActivity.this);
                sachDAO = new SachDao(ThemHDCTActivity.this);
                List<HoaDon> hoaDons = hoaDonDAO.getAllHoaDon();
                List<Sach> sachs = sachDAO.getAllSach();
                HoaDon hd = null;
                Sach s = null;
                for (int i = 0; i < hoaDons.size(); i++) {
                    if (maHD.equals(hoaDons.get(i).getMaHoaDon())) {
                        hd = hoaDons.get(i);
                    }
                }
                for (int i = 0; i < sachs.size(); i++) {
                    String ms = edt_masach_HDCT.getText().toString();
                    if (ms.equals(sachs.get(i).getMaSach())) {
                        s = sachs.get(i);
                    }
                }
                if (edt_mahd_HDCT.getText().toString().isEmpty()||edt_soluong_HDCT.getText().toString().isEmpty()){
                    Toast.makeText(ThemHDCTActivity.this, "YBạn phải nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(1, hd, s, Integer.valueOf(edt_soluong_HDCT.getText().toString()));
                    long t = hoaDonChiTietDAO.insertHDCT(hoaDonChiTiet);
                    if (t > 0) {
                        Toast.makeText(ThemHDCTActivity.this, "Thêm hóa đơn chi tiết thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ThemHDCTActivity.this, "Thêm hóa đơn chi tiết không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public int timHoaDon(String mahoadon) {
        int result = 0;
//        databaseHelper = new DatabaseHelper(ThemHDCTActivity.this);
        hoaDonDAO = new HoaDonDao(ThemHDCTActivity.this);
        List<HoaDon> hoaDonList = hoaDonDAO.getAllHoaDon();
        for (HoaDon hoaDon : hoaDonList) {
            if (mahoadon.equals(hoaDon.getMaHoaDon())) {
                result = 1;
            }
        }
        return result;
    }

    public double timSach(String masach, int soluong) {
        double result = 0;
//        databaseHelper = new DatabaseHelper(ThemHDCTActivity.this);
        sachDAO = new SachDao(ThemHDCTActivity.this);
        List<Sach> sachList = sachDAO.getAllSach();
        for (int i = 0; i < sachList.size(); i++) {
            if (masach.equals(sachList.get(i).getMaSach())) {
                if (soluong <= sachList.get(i).getSoLuong()) {
                    result = sachList.get(i).getGiaBan();
                } else {
                    Log.e("soluong: ", soluong + "");
                    result = 2;
                }
            }
        }
        return result;
    }
}
