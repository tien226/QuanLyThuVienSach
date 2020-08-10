package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.poly.duanmau.Adapter.SachAdapter;
import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class TopBookActivity extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    SachAdapter adapter = null;
    SachDao sachDAO;
    EditText edThang;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_sach_theo_thang);
        setTitle("TOP 10 SÁCH BÁN CHẠY");

        toolbar = findViewById(R.id.toolbartopsach);
        lvBook = (ListView) findViewById(R.id.lvBookTop);
        edThang = (EditText) findViewById(R.id.edThang);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void VIEW_SACH_TOP_10(View view) {
        if (edThang.getText().toString().isEmpty()){
            Toast.makeText(this, "Mời bạn nhập tháng tìm kiếm!", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(edThang.getText().toString()) > 12 || Integer.parseInt(edThang.getText().toString()) < 0) {
            Toast.makeText(TopBookActivity.this, "Bạn phải nhập sai tháng(Nhập tháng trong khoảng 1-12)!", Toast.LENGTH_SHORT).show();
        } else {
            sachDAO = new SachDao(TopBookActivity.this);
            dsSach = sachDAO.getSachTop10(edThang.getText().toString());
            List<Sach> listSach = sachDAO.getAllSach();
            List<Sach> sachList = new ArrayList<>();
            for (int j = 0 ; j < dsSach.size();j++){
                for (int i = 0;i < listSach.size(); i++){
                    if (dsSach.get(j).getMaSach().equals(listSach.get(i).getMaSach())){
                        dsSach.get(j).setTenSach(listSach.get(i).getTenSach());
                        sachList.add(dsSach.get(j));
                    }
                }
            }
            adapter = new SachAdapter(this, sachList);
            lvBook.setAdapter(adapter);
        }
    }
}
