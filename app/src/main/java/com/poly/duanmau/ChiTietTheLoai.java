package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChiTietTheLoai extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvMaTL, tvTenTL, tvMoTa, tvViTri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_the_loai);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Chi tiết thể loại");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvMaTL = findViewById(R.id.tvMaTL);
        tvTenTL = findViewById(R.id.tvTenTL);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvViTri = findViewById(R.id.tvViTri);

        Intent intent = getIntent();
        if (intent!=null) {
            Bundle bundle = intent.getBundleExtra("bun");
            tvMaTL.setText("Mã thể loại: "+bundle.getString("MaTL_key"));
            tvTenTL.setText("Tên thể loại: "+bundle.getString("TenTL_key"));
            tvMoTa.setText("Mô tả: "+bundle.getString("MoTa_key"));
            tvViTri.setText("Vị trí: "+bundle.getString("ViTri_key"));
        }
    }
}