package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ChiTietNguoiDung extends AppCompatActivity {
    TextView edUser,edPass;
    TextView edPhone,edFullName;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nguoi_dung);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chi tiết người dùng");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edUser = (TextView) findViewById(R.id.edUserName);
        edPass = (TextView) findViewById(R.id.edPassword);
        edPhone = findViewById(R.id.edPhone);
        edFullName = findViewById(R.id.edFullName);
        Intent intent = getIntent();
        if (intent!=null) {
            Bundle bundle = intent.getBundleExtra("bun");
            edUser.setText("Tên đăng nhập: "+bundle.getString("userName_key"));
            edPass.setText("Mật khẩu: "+bundle.getString("password_key"));
            edPhone.setText("SĐT: "+bundle.getString("phone_key"));
            edFullName.setText("Họ Tên: "+bundle.getString("hoTen_key"));
        }
    }
}