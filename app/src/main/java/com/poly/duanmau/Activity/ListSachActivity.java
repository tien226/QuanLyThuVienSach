package com.poly.duanmau.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.poly.duanmau.Adapter.SachAdapter;
import com.poly.duanmau.DAO.SachDao;
import com.poly.duanmau.Model.Sach;
import com.poly.duanmau.R;

import java.util.ArrayList;
import java.util.List;

public class ListSachActivity extends AppCompatActivity {
    EditText edtsuatensach, edtsuagiasach;
    EditText edttimkiemsach;
    ListView lv_sach;
    SachDao sachDao;
    List<Sach>  sachList = new ArrayList<>();
    SachAdapter sachAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sach);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Danh Sách Các Sách");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lv_sach = findViewById(R.id.lv_sach);
        edttimkiemsach = findViewById(R.id.edttimkiemsach);

        sachDao = new SachDao(ListSachActivity.this);
        registerForContextMenu(lv_sach);
        Loadsach();

        //Tìm kiếm sách
        lv_sach.setTextFilterEnabled(true);
        edttimkiemsach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before){
                    sachAdapter.resetData();
                }
                sachAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void Loadsach(){
        sachList = sachDao.getAllSach();
        sachAdapter = new SachAdapter(ListSachActivity.this,sachList);
        lv_sach.setAdapter(sachAdapter);
        sachAdapter.notifyDataSetChanged();
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
            Intent intent = new Intent(ListSachActivity.this,SachActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int pos = menuInfo.position;
        final String postion = sachList.get(pos).getMaSach();

        int id = item.getItemId();

        if (id == R.id.menu_ctx_edit){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListSachActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.suathongtinsach,null);
            edtsuatensach = mview.findViewById(R.id.edtsuatensach);
            edtsuagiasach = mview.findViewById(R.id.edtsuagiasach);
            builder.setView(mview);

            edtsuatensach.setText(sachList.get(pos).getTenSach());
            edtsuagiasach.setText(String.valueOf(sachList.get(pos).getSoLuong()));

            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Sach sach = new Sach();
                    sach.setTenSach(edtsuatensach.getText().toString());
                    sach.setSoLuong(Integer.valueOf(edtsuagiasach.getText().toString()));
//                    sach.setGiaBan(Double.valueOf(edtsuagiasach.getText().toString()));
                    long suasach = sachDao.updateSach(postion,sach);
                    if (suasach >0){
                        Toast.makeText(ListSachActivity.this, "Sửa thông tin sách thành công", Toast.LENGTH_SHORT).show();
                        Loadsach();
                    }else {
                        Toast.makeText(ListSachActivity.this, "Sửa thông tin sách không thành công!", Toast.LENGTH_SHORT).show();
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

        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ListSachActivity.this);
            builder.setTitle("Xác nhận xóa thông tin?");
            builder.setMessage("Bạn muốn xóa danh sách này");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long xoasach = sachDao.deleteSach(postion);
                    if (xoasach>0){
                        Toast.makeText(ListSachActivity.this, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                        Loadsach();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(ListSachActivity.this, "Xóa sách không thành công!", Toast.LENGTH_SHORT).show();
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

}
