package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
    ListView lvBook;
    Button btntim_topsach;
    SachAdapter adapter = null;
    SachDao sachDAO;
    EditText edThang;
    Toolbar toolbar;
    public static List<Sach> dsSach = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_sach_theo_thang);
        setTitle("TOP 10 SÁCH BÁN CHẠY");
        toolbar = findViewById(R.id.toolbartopsach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // ánh xạ
        lvBook = (ListView) findViewById(R.id.lvBookTop);
        edThang = (EditText) findViewById(R.id.edThang);
        btntim_topsach = findViewById(R.id.btntimkiem_TopSach);

        //button tìm kiếm top 10 sách
        btntim_topsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(TopBookActivity.this,R.anim.alpha_click);
                btntim_topsach.startAnimation(animation);

                if (edThang.getText().toString().isEmpty()){
                    Toast.makeText(TopBookActivity.this, "Mời bạn nhập tháng tìm kiếm!", Toast.LENGTH_SHORT).show();
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
                    adapter = new SachAdapter(TopBookActivity.this, sachList);
                    lvBook.setAdapter(adapter);
                }
            }
        });


    }
}
