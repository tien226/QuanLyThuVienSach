package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.poly.duanmau.Adapter.TheLoaiAdapter;
import com.poly.duanmau.DAO.TheLoaiDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.TheLoai;

public class TheLoaiActivity extends AppCompatActivity {
    EditText edt_matheloai, edt_tentheloai, edt_vitri, edt_mota;
    TheLoaiDao theLoaiDAO;
    ListView lv_theloai;
    TheLoaiAdapter theLoaiAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        setTitle("Thêm Thể Loại");

        edt_matheloai = findViewById(R.id.edt_maTheLoai);
        edt_tentheloai = findViewById(R.id.edt_tenTheLoai);
        edt_mota = findViewById(R.id.edt_moTa);
        edt_vitri = findViewById(R.id.edt_viTri);
    }

    public void btnthemtheloai(View view) {
        if (edt_matheloai.getText().toString().isEmpty()||edt_tentheloai.getText().toString().isEmpty()||edt_mota.getText().toString().isEmpty()||edt_vitri.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn phải nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
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
