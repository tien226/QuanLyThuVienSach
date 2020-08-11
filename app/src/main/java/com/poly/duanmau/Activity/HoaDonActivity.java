package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.R;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HoaDonActivity extends AppCompatActivity {
    TextView tv_mahoadon, tv_ngaymua;
    Button btn_pickDate, btn_themHD, btnds_hd;
    HoaDonDao hoaDonDAO;
    Toolbar toolbarhoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        setTitle("Quản Lý Sách");
        toolbarhoadon = findViewById(R.id.toolbar_hoadon);
        setSupportActionBar(toolbarhoadon);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // ánh xạ
        tv_mahoadon = findViewById(R.id.edt_maHoaDon);
        tv_ngaymua = findViewById(R.id.tv_ngayMuaHD);
        btn_pickDate = findViewById(R.id.btn_picDate);
        btn_themHD = findViewById(R.id.btnthem_HoaDon);
        btnds_hd = findViewById(R.id.btndanhsach_HoaDon);

        //button chọn ngày
        btn_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(HoaDonActivity.this,R.anim.alpha_click);
                btn_pickDate.startAnimation(animation);

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(HoaDonActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        tv_ngaymua.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //button thêm hóa đơn
        btn_themHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(HoaDonActivity.this,R.anim.alpha_click);
                btn_themHD.startAnimation(animation);

                Date date = null;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = simpleDateFormat.parse(tv_ngaymua.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(tv_mahoadon.getText().toString());
                hoaDon.setNgayMua(date);

                if (tv_mahoadon.getText().toString().isEmpty()||tv_ngaymua.getText().toString().isEmpty()){
                    Toast.makeText(HoaDonActivity.this, "Bạn phải nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    hoaDonDAO = new HoaDonDao(HoaDonActivity.this);
                    long value = hoaDonDAO.insertHoaDon(hoaDon);
                    if (value > 0) {
                        Toast.makeText(HoaDonActivity.this, "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HoaDonActivity.this, "Thêm hóa đơn không thành công!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(HoaDonActivity.this, ThemHDCTActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("maHD", hoaDon.getMaHoaDon());
                    intent.putExtra("dataHD", bundle);
                    startActivity(intent);
                }
            }
        });

        //button danh sách hóa đơn
        btnds_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(HoaDonActivity.this,R.anim.alpha_click);
                btnds_hd.startAnimation(animation);

                Intent intent=new Intent(HoaDonActivity.this, ListHoaDonActivity.class);
                startActivity(intent);
            }
        });

    }
}
