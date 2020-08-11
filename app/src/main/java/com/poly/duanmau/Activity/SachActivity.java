package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.DAO.TheLoaiDao;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.Model.TheLoai;
import com.poly.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class SachActivity extends AppCompatActivity {
    EditText edt_maSach, edt_tenSach, edt_giaBan, edt_nxb, edt_soLuong, edt_tacGia;
    Button btnthem_sach, btnthoat_sach, btnds_sach;
    Spinner spinner_theloai;
    SachDao sachDAO;
    String matheloai = "";
    TheLoaiDao theLoaiDao;
    List<TheLoai> theLoaiList = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        setTitle("Quản Lý Sách");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // ánh xạ
        btnthem_sach = findViewById(R.id.btnthem_Sach);
        btnthoat_sach = findViewById(R.id.btnthoat_Sach);
        btnds_sach = findViewById(R.id.btndanhsach_Sach);
        edt_tenSach = findViewById(R.id.edt_tenSach);
        edt_maSach = findViewById(R.id.edt_maSach);
        edt_tacGia = findViewById(R.id.edt_tacGia);
        edt_nxb = findViewById(R.id.edt_nxb);
        edt_soLuong = findViewById(R.id.edt_soLuong);
        edt_giaBan = findViewById(R.id.edt_giaBan);
        spinner_theloai = findViewById(R.id.spTheLoai);

        //button thêm sách
        btnthem_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(SachActivity.this,R.anim.alpha_click);
                btnthem_sach.startAnimation(animation);

                int check =0;
                if (edt_maSach.getText().toString().isEmpty()||edt_tenSach.getText().toString().isEmpty()||edt_tacGia.getText().toString().isEmpty()||edt_nxb.getText().toString().isEmpty()||edt_giaBan.getText().toString().isEmpty()||edt_soLuong.getText().toString().isEmpty()){
                    Toast.makeText(SachActivity.this, "Bạn phải nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    sachDAO = new SachDao(SachActivity.this);
                    Sach sach = new Sach();
                    sach.setMaSach(edt_maSach.getText().toString());
                    sach.setMaTheLoai(matheloai);
                    sach.setTenSach(edt_tenSach.getText().toString());
                    sach.setTacGia(edt_tacGia.getText().toString());
                    sach.setNXB(edt_nxb.getText().toString());
                    sach.setGiaBan(Double.valueOf(edt_giaBan.getText().toString()));
                    sach.setSoLuong(Integer.valueOf(edt_soLuong.getText().toString()));

                    if (sachDAO.insertSach(sach)>0){
                        Toast.makeText(SachActivity.this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SachActivity.this, "Thêm sách không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // button thoát sách
        btnthoat_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(SachActivity.this,R.anim.alpha_click);
                btnthoat_sach.startAnimation(animation);

                startActivity(new Intent(SachActivity.this,MainActivity.class));
            }
        });

        // button danh sách các sách
        btnds_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(SachActivity.this,R.anim.alpha_click);
                btnds_sach.startAnimation(animation);

                Intent intent = new Intent(SachActivity.this,ListSachActivity.class);
                startActivity(intent);
            }
        });

        //lấy dữ liệu truyền vào spinner
        getTheloai();
        spinner_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matheloai = theLoaiList.get(spinner_theloai.getSelectedItemPosition()).getMaTheLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void getTheloai(){
        theLoaiDao = new TheLoaiDao(this);
        theLoaiList = theLoaiDao.getAllTheLoai();
        ArrayAdapter<TheLoai> theLoaiArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,theLoaiList);
        theLoaiArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_theloai.setAdapter(theLoaiArrayAdapter);
    }

}

