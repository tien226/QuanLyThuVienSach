package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.poly.duanmau.Adapter.HoaDonAdapter;
import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.Database.DatabaseHelper;

public class HoaDonActivity extends AppCompatActivity {
    ListView lv_hoadon;
    DatabaseHelper databaseHelper;
    EditText edttimhoadon;
    HoaDonDao hoaDonDAO;
    HoaDonAdapter hoaDonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_hoa_don);
        setTitle("Hóa đơn");
        lv_hoadon = findViewById(R.id.lv_hoaDon);
        edttimhoadon=findViewById(R.id.edttimkiemhoadon);

//        databaseHelper = new DatabaseHelper(HoaDonActivity.this);
        hoaDonDAO = new HoaDonDao(HoaDonActivity.this);
        hoaDonAdapter = new HoaDonAdapter(HoaDonActivity.this, hoaDonDAO.getAllHoaDon());
        lv_hoadon.setAdapter(hoaDonAdapter);
        lv_hoadon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HoaDonActivity.this, HDCTActivity.class);
                Bundle bundle = new Bundle();
                String maHDgui = hoaDonDAO.getAllHoaDon().get(position).getMaHoaDon();
                bundle.putString("maHDgui", maHDgui);
                intent.putExtra("datagui", bundle);
                startActivity(intent);
            }
        });

        //Tìm kiếm hóa đơn
        lv_hoadon.setTextFilterEnabled(true);
        edttimhoadon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before){
                    hoaDonAdapter.resethoadon();
                }
                hoaDonAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_them,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.themmenu){
            Intent intent = new Intent(HoaDonActivity.this,Them_Hoa_Don_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
