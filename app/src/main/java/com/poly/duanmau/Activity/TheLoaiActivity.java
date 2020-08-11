package com.poly.duanmau.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.poly.duanmau.Adapter.TheLoaiAdapter;
import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.DAO.TheLoaiDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.NguoiDung;
import com.poly.duanmau.Model.TheLoai;
import com.poly.duanmau.R;

public class TheLoaiActivity extends AppCompatActivity {
    EditText edt_matheloai, edt_tentheloai, edt_vitri, edt_mota;
    Button btnthem_tl, btnthoat_tl, btnds_tl;
    TheLoaiDao theLoaiDAO;
    TextInputLayout textInputLayoutEdt_maTheLoai,textInputLayoutEdt_tenTheLoai, textInputLayoutEdt_moTa, textInputLayoutEdt_viTri;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        setTitle("Quản Lý Sách");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //ánh xạ
        btnthem_tl = findViewById(R.id.btnthem_theloai);
        btnthoat_tl = findViewById(R.id.btnhuy_theloai);
        btnds_tl = findViewById(R.id.btnds_theloai);
        edt_matheloai = findViewById(R.id.edt_maTheLoai);
        edt_tentheloai = findViewById(R.id.edt_tenTheLoai);
        edt_mota = findViewById(R.id.edt_moTa);
        edt_vitri = findViewById(R.id.edt_viTri);
        textInputLayoutEdt_maTheLoai = findViewById(R.id.textInputLayoutEdt_maTheLoai);
        textInputLayoutEdt_tenTheLoai = findViewById(R.id.textInputLayoutEdt_tenTheLoai);
        textInputLayoutEdt_moTa = findViewById(R.id.textInputLayoutEdt_moTa);
        textInputLayoutEdt_viTri = findViewById(R.id.textInputLayoutEdt_viTri);

        //button thêm thể loại
        btnthem_tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TheLoaiActivity.this,R.anim.alpha_click);
                btnthem_tl.startAnimation(animation);

                if (textInputLayoutEdt_maTheLoai.getEditText().length()==0){
                    textInputLayoutEdt_maTheLoai.setError("Không được bỏ trống!");
                } else if (textInputLayoutEdt_tenTheLoai.getEditText().length()==0){
                    textInputLayoutEdt_tenTheLoai.setError("Không được bỏ trống!");
                } else if (textInputLayoutEdt_moTa.getEditText().length()==0){
                    textInputLayoutEdt_moTa.setError("Không được bỏ trống!");
                } else if (textInputLayoutEdt_viTri.getEditText().length()==0){
                    textInputLayoutEdt_viTri.setError("Không được bỏ trống!");
                }  else {

                    textInputLayoutEdt_maTheLoai.setError("");
                    textInputLayoutEdt_tenTheLoai.setError("");
                    textInputLayoutEdt_moTa.setError("");
                    textInputLayoutEdt_viTri.setError("");

                    theLoaiDAO = new TheLoaiDao(TheLoaiActivity.this);
                    TheLoai theLoai = new TheLoai();
                    theLoai.setMaTheLoai(edt_matheloai.getText().toString());
                    theLoai.setTenTheLoai(edt_tentheloai.getText().toString());
                    theLoai.setMoTa(edt_mota.getText().toString());
                    theLoai.setViTri(Integer.valueOf(edt_vitri.getText().toString()));
                    if (theLoaiDAO.insertTheLoai(theLoai) > 0){
                        Toast.makeText(TheLoaiActivity.this, "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TheLoaiActivity.this, "Thêm thể loại không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //button thoát thể loại
        btnthoat_tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TheLoaiActivity.this,R.anim.alpha_click);
                btnthoat_tl.startAnimation(animation);

                edt_matheloai.setText("");
                edt_tentheloai.setText("");
                edt_vitri.setText("");
                edt_mota.setText("");
                startActivity(new Intent(TheLoaiActivity.this,MainActivity.class));
            }
        });

        //button danh sách thể loại
        btnds_tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TheLoaiActivity.this,R.anim.alpha_click);
                btnds_tl.startAnimation(animation);

                Intent intent = new Intent(TheLoaiActivity.this,ListTheLoaiActivity.class);
                startActivity(intent);
            }
        });
    }

}
