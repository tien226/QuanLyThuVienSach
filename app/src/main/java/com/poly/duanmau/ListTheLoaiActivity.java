package com.poly.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.poly.duanmau.Adapter.TheLoaiAdapter;
import com.poly.duanmau.DAO.TheLoaiDao;
import com.poly.duanmau.Model.TheLoai;

import java.util.ArrayList;
import java.util.List;

public class ListTheLoaiActivity extends AppCompatActivity {
    EditText edtsuamatheloai, edtsuatentheloai;
    ListView lv_theloai;
    TheLoaiDao theLoaiDao;
    List<TheLoai> theLoaiList = new ArrayList<>();
    TheLoaiAdapter theloaiAdapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_the_loai);
        setTitle("Danh Sách Thể Loại");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_theloai = findViewById(R.id.lvTheLoai);

        theLoaiDao = new TheLoaiDao(ListTheLoaiActivity.this);

        registerForContextMenu(lv_theloai);
        LoadTheloai();
    }

    public void LoadTheloai(){
        theLoaiList = theLoaiDao.getAllTheLoai();
        theloaiAdapter = new TheLoaiAdapter(ListTheLoaiActivity.this,theLoaiList);
        lv_theloai.setAdapter(theloaiAdapter);
        theloaiAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_theloai,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.themtheloaimenu){
            Intent intent = new Intent(ListTheLoaiActivity.this, TheLoaiActivity.class);
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
        final String postion = theLoaiList.get(pos).getMaTheLoai();

        int id = item.getItemId();

        if (id == R.id.menu_ctx_edit){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListTheLoaiActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.suathongtinntheloai,null);
            edtsuamatheloai = mview.findViewById(R.id.edtsuamatheloai);
            edtsuatentheloai = mview.findViewById(R.id.edtsuatentheloai);
            builder.setView(mview);

            edtsuamatheloai.setText(theLoaiList.get(pos).getMaTheLoai());
            edtsuatentheloai.setText(theLoaiList.get(pos).getTenTheLoai());

            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    TheLoai theLoai = new TheLoai();
                    theLoai.setMaTheLoai(edtsuamatheloai.getText().toString());
                    theLoai.setTenTheLoai(edtsuatentheloai.getText().toString());
                    long suatheloai = theLoaiDao.updateTheLoai(postion,theLoai);
                    if (suatheloai >0){
                        Toast.makeText(ListTheLoaiActivity.this, "Sửa thông tin thể loại thành công", Toast.LENGTH_SHORT).show();
                        LoadTheloai();
                    }else {
                        Toast.makeText(ListTheLoaiActivity.this, "Sửa thông tin thể loại không thành công!", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ListTheLoaiActivity.this);
            builder.setTitle("Xác nhận xóa thông tin?");
            builder.setMessage("Bạn muốn xóa danh sách này");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long xoatheloai = theLoaiDao.deleteTheLoai(postion);
                    if (xoatheloai>0){
                        Toast.makeText(ListTheLoaiActivity.this, "Xóa thể loại thành công", Toast.LENGTH_SHORT).show();
                        LoadTheloai();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(ListTheLoaiActivity.this, "Xóa thể loại không thành công!", Toast.LENGTH_SHORT).show();
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
