package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.DAO.TheLoaiDao;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class SachActivity extends AppCompatActivity {
    EditText edt_maSach, edt_tenSach, edt_giaBan, edt_nxb, edt_soLuong, edt_tacGia;
    Spinner spinner_theloai;
    SachDao sachDAO;
    String matheloai = "";
    TheLoaiDao theLoaiDao;
    List<TheLoai> theLoaiList = new ArrayList<>();
    Toolbar toolbar;
//    SachAdapter sachAdapter;
//    ListView lv_sach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        setTitle("Thêm Sách");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_tenSach = findViewById(R.id.edt_tenSach);
        edt_maSach = findViewById(R.id.edt_maSach);
        edt_tacGia = findViewById(R.id.edt_tacGia);
        edt_nxb = findViewById(R.id.edt_nxb);
        edt_soLuong = findViewById(R.id.edt_soLuong);
        edt_giaBan = findViewById(R.id.edt_giaBan);
        spinner_theloai = findViewById(R.id.spTheLoai);
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
        theLoaiArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_theloai.setAdapter(theLoaiArrayAdapter);
    }

    public void btnthemsach(View view) {
        int check =0;
        if (edt_maSach.getText().toString().isEmpty()||edt_tenSach.getText().toString().isEmpty()||edt_tacGia.getText().toString().isEmpty()||edt_nxb.getText().toString().isEmpty()||edt_giaBan.getText().toString().isEmpty()||edt_soLuong.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
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

            long themsach = sachDAO.insertSach(sach);
            if (themsach>0){
                Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Thêm sách không thành công!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnhuysach(View view) {
        edt_maSach.setText("");
        edt_giaBan.setText("");
        edt_nxb.setText("");
        edt_soLuong.setText("");
        edt_tacGia.setText("");
        edt_tenSach.setText("");
    }


    public void btndssach(View view) {
        Intent intent = new Intent(SachActivity.this,ListSachActivity.class);
        startActivity(intent);
    }
}

