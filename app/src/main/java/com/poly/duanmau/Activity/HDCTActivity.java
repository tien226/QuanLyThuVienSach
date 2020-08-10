package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.poly.duanmau.Adapter.HoaDonChiTietAdapter;
import com.poly.duanmau.DAO.HoaDonChiTietDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.HoaDonChiTiet;
import com.poly.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class HDCTActivity extends AppCompatActivity {
    HoaDonChiTietAdapter hoaDonChiTietAdapter;
    ListView lv_hdct;
    DatabaseHelper databaseHelper;
    HoaDonChiTiet hoaDonChiTiet = null;
    HoaDonChiTietDao hoaDonChiTietDAO;
    List<HoaDonChiTiet> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdct);
        setTitle("Hóa đơn chi tiết");
        lv_hdct = findViewById(R.id.lv_hdct);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("datagui");
        String maHDnhan = bundle.getString("maHDgui");
        Log.e("mã hóa đơn đã nhận: ", " " + maHDnhan);
//        databaseHelper = new DatabaseHelper(HDCTActivity.this);
        hoaDonChiTietDAO = new HoaDonChiTietDao(HDCTActivity.this);
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietDAO.getAllHoaDonChiTiet();
        for (int i = 0; i < hoaDonChiTietList.size(); i++) {
            if (hoaDonChiTietList.get(i).getHoaDon().getMaHoaDon().equals(maHDnhan)) {
                hoaDonChiTiet = hoaDonChiTietList.get(i);
                myList.add(hoaDonChiTiet);
            }
        }
        Log.e("số phần tử tìm được ", "" + myList);
        hoaDonChiTietAdapter = new HoaDonChiTietAdapter(HDCTActivity.this, myList);
        lv_hdct.setAdapter(hoaDonChiTietAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_them,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id==R.id.themmenu){
//            Intent intent = new Intent(HDCTActivity.this,ThemHDCTActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
