package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class TheLoaiActivity extends AppCompatActivity {
    EditText edt_matheloai, edt_tentheloai, edt_vitri, edt_mota;
    TheLoaiDao theLoaiDAO;
    TextInputLayout textInputLayoutEdt_maTheLoai,textInputLayoutEdt_tenTheLoai, textInputLayoutEdt_moTa, textInputLayoutEdt_viTri;
    ListView lv_theloai;
    TheLoaiAdapter theLoaiAdapter;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        setTitle("Thêm Thể Loại");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_matheloai = findViewById(R.id.edt_maTheLoai);
        edt_tentheloai = findViewById(R.id.edt_tenTheLoai);
        edt_mota = findViewById(R.id.edt_moTa);
        edt_vitri = findViewById(R.id.edt_viTri);
        textInputLayoutEdt_maTheLoai = findViewById(R.id.textInputLayoutEdt_maTheLoai);
        textInputLayoutEdt_tenTheLoai = findViewById(R.id.textInputLayoutEdt_tenTheLoai);
        textInputLayoutEdt_moTa = findViewById(R.id.textInputLayoutEdt_moTa);
        textInputLayoutEdt_viTri = findViewById(R.id.textInputLayoutEdt_viTri);
    }

    public void btnthemtheloai(View view) {
        if (edt_matheloai.getText().toString().isEmpty()){
            textInputLayoutEdt_maTheLoai.setError("Không được bỏ trống!");
        } else if (edt_tentheloai.getText().toString().isEmpty()){
            textInputLayoutEdt_tenTheLoai.setError("Không được bỏ trống!");
        } else if (edt_mota.getText().toString().isEmpty()){
            textInputLayoutEdt_moTa.setError("Không được bỏ trống!");
        } else if (edt_vitri.getText().toString().isEmpty()){
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

            long themtheloai = theLoaiDAO.insertTheLoai(theLoai);
            if (themtheloai > 0){
                Toast.makeText(this, "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Thêm thể loại không thành công!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnhuytheloai(View view) {
        edt_matheloai.setText("");
        edt_tentheloai.setText("");
        edt_vitri.setText("");
        edt_mota.setText("");
    }

    public void btndstheloai(View view) {
        Intent intent = new Intent(TheLoaiActivity.this,ListTheLoaiActivity.class);
        startActivity(intent);
    }
}
