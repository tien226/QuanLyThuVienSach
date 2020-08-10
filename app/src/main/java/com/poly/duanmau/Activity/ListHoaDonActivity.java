package com.poly.duanmau.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.poly.duanmau.Adapter.HoaDonAdapter;
import com.poly.duanmau.DAO.HoaDonDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.HoaDon;
import com.poly.duanmau.R;

import java.util.List;

public class ListHoaDonActivity extends AppCompatActivity {
    ListView lv_hoadon;
    DatabaseHelper databaseHelper;
    EditText edttimhoadon;
    HoaDonDao hoaDonDAO;
    HoaDonAdapter hoaDonAdapter;
    Toolbar toolbar;
    List<HoaDon> hoaDonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_hoa_don);
        setTitle("Danh Sách Hóa Đơn");
        lv_hoadon = findViewById(R.id.lv_hoaDon);
        edttimhoadon=findViewById(R.id.edttimkiemhoadon);
        toolbar = findViewById(R.id.toolbarhoadon);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        hoaDonDAO = new HoaDonDao(ListHoaDonActivity.this);
        registerForContextMenu(lv_hoadon);
        Loadhoadon();

        lv_hoadon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListHoaDonActivity.this, HDCTActivity.class);
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
            Intent intent = new Intent(ListHoaDonActivity.this,Them_Hoa_Don_Activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_xoathongtin,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int pos = menuInfo.position;
        final String postion = hoaDonList.get(pos).getMaHoaDon();
        
        int id = item.getItemId();
        if (id == R.id.menu_xoa){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListHoaDonActivity.this);
            builder.setTitle("Xác nhận xóa thông tin?");
            builder.setMessage("Bạn muốn xóa danh sách này");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long xoasach = hoaDonDAO.deleteHoaDon(postion);
                    if (xoasach>0){
                        Toast.makeText(ListHoaDonActivity.this, "Xóa hóa đơn thành công", Toast.LENGTH_SHORT).show();
                        Loadhoadon();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(ListHoaDonActivity.this, "Xóa hóa đơn không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onContextItemSelected(item);
    }

    private void Loadhoadon() {
        hoaDonList = hoaDonDAO.getAllHoaDon();
        hoaDonAdapter = new HoaDonAdapter(ListHoaDonActivity.this, hoaDonDAO.getAllHoaDon());
        lv_hoadon.setAdapter(hoaDonAdapter);
        hoaDonAdapter.notifyDataSetChanged();
    }
}
