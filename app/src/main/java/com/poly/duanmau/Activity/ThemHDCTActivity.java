package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.poly.duanmau.DAO.HoaDonChiTietDao;
import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.DAO.TheLoaiDao;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.Model.HoaDonChiTiet;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.Model.TheLoai;
import com.poly.duanmau.R;

import java.util.List;

public class ThemHDCTActivity extends AppCompatActivity {
    EditText edt_mahd_HDCT, edt_soluong_HDCT;
    Spinner edt_masach_HDCT;
    TextView tv_thanhtoan_HDCT;
    Button btn_themHDCT, btn_thanhtoanHDCT;
    HoaDonDao hoaDonDAO;
    SachDao sachDAO;
    HoaDonChiTietDao hoaDonChiTietDAO;
    int soluong;
    Toolbar toolbarhdct;
    List<Sach> listSach;
    String maSach="";
    TextInputLayout textInputLayoutMaHDCT, textInputLayoutMaSach, textInputLayoutSoLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hdct);
        setTitle("Quản Lý Sách");
        toolbarhdct = findViewById(R.id.toolbar_hdct);
        setSupportActionBar(toolbarhdct);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //ánh xạ
        edt_mahd_HDCT = findViewById(R.id.edt_mahd_HDCT);
        edt_masach_HDCT = findViewById(R.id.edt_masach_HDCT);
        edt_soluong_HDCT = findViewById(R.id.edt_soluong_HDCT);
        tv_thanhtoan_HDCT = findViewById(R.id.tv_thanhTienHDCT);
        btn_themHDCT = findViewById(R.id.btn_themHDCT);
        btn_thanhtoanHDCT = findViewById(R.id.btn_thanhtoanHDCT);

        textInputLayoutMaHDCT = findViewById(R.id.textInputLayoutMaHDCT);
        textInputLayoutMaSach = findViewById(R.id.textInputLayoutMaSach);
        textInputLayoutSoLuong = findViewById(R.id.textInputLayoutSoLuong);

        //lấy thông tin mã hóa đơn
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dataHD");
        final String maHD = bundle.getString("maHD");
        edt_mahd_HDCT.setText(maHD);

        //button thêm hdct
        btn_themHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ThemHDCTActivity.this,R.anim.alpha_click);
                btn_themHDCT.startAnimation(animation);

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
                    String ms = maSach;
                    if (ms.equals(sachs.get(i).getMaSach())) {
                        s = sachs.get(i);
                    }
                }
                if (textInputLayoutMaHDCT.getEditText().length() == 0) {
                    textInputLayoutMaHDCT.setError("Không được bỏ trống!");
                }else if (textInputLayoutSoLuong.getEditText().length()==0){
                    textInputLayoutSoLuong.setError("Không được bỏ trống!");
                }else {
                    textInputLayoutMaHDCT.setError("");
                    textInputLayoutSoLuong.setError("");
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

        // buton thanh toán hdct
        btn_thanhtoanHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ThemHDCTActivity.this,R.anim.alpha_click);
                btn_thanhtoanHDCT.startAnimation(animation);

                if (timHoaDon(maHD) == 0) {
                    Toast.makeText(ThemHDCTActivity.this, "Không tìm thấy mã hóa đơn", Toast.LENGTH_SHORT).show();
                }
//                String masach = edt_masach_HDCT.getText().toString();
                soluong = Integer.valueOf(edt_soluong_HDCT.getText().toString());
                double a = timSach(maSach, soluong);
                if (a == 2) {
                    Toast.makeText(ThemHDCTActivity.this, "Quá số lượng hiện có", Toast.LENGTH_SHORT).show();
                }
                if (a != 0 && a != 2) {
                    double thanhtien = a * soluong;
                    tv_thanhtoan_HDCT.setText("Thành tiền : " + thanhtien);
                }
            }
        });
        getTheloai();
        edt_masach_HDCT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(edt_masach_HDCT.getSelectedItemPosition()).getMaSach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public int timHoaDon(String mahoadon) {
        int result = 0;
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

    public void getTheloai(){
        sachDAO = new SachDao(this);
        listSach = sachDAO.getAllSach();
        ArrayAdapter<Sach> theLoaiArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listSach);
        theLoaiArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        edt_masach_HDCT.setAdapter(theLoaiArrayAdapter);
    }
}
