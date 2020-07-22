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

import com.poly.duanmau.Adapter.NguoiDungAdapter;
import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.NguoiDung;

import java.util.List;

public class ListNguoiDungActivity extends AppCompatActivity {
    EditText edtsuaphone, edtsuahoten;
    ListView lvNguoiDung;
    NguoiDungDao nguoiDungDAO;
    NguoiDungAdapter nguoiDungAdapter;
    List<NguoiDung> nguoiDungList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi_dung);
        lvNguoiDung = findViewById(R.id.lvNguoiDung);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("NGƯỜI DÙNG");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nguoiDungDAO = new NguoiDungDao(ListNguoiDungActivity.this);
        registerForContextMenu(lvNguoiDung);
        Loadnguoidung();
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListNguoiDungActivity.this, ChiTietNguoiDung.class);

                Bundle bundle = new Bundle();
                bundle.putString("userName_key",nguoiDungList.get(position).getUserName());
                bundle.putString("password_key",nguoiDungList.get(position).getPassWord());
                bundle.putString("phone_key",nguoiDungList.get(position).getPhone());
                bundle.putString("hoTen_key",nguoiDungList.get(position).getHoTen());

                intent.putExtra("bun",bundle);
                startActivity(intent);
            }
        });

    }

    public void Loadnguoidung(){
        nguoiDungList = nguoiDungDAO.getAllNguoiDung();
        nguoiDungAdapter = new NguoiDungAdapter(nguoiDungList,ListNguoiDungActivity.this);
        lvNguoiDung.setAdapter(nguoiDungAdapter);
        nguoiDungAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add){
            Intent intent = new Intent(ListNguoiDungActivity.this,NguoiDungActivity.class);
            startActivity(intent);
        } else if (id == R.id.changePass){
            Intent intent = new Intent(ListNguoiDungActivity.this,ChangePassActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(ListNguoiDungActivity.this,LoginActivity.class);
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
        final String postion = nguoiDungList.get(pos).getUserName();

        int id = item.getItemId();

        if (id == R.id.menu_ctx_edit){
            AlertDialog.Builder builder = new AlertDialog.Builder(ListNguoiDungActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.suathongtinnguoidung,null);
            edtsuaphone = mview.findViewById(R.id.edtsuaphonenguoidung);
            edtsuahoten = mview.findViewById(R.id.edtsuahotennguoidung);
            builder.setView(mview);

            edtsuaphone.setText(nguoiDungList.get(pos).getPhone());
            edtsuahoten.setText(nguoiDungList.get(pos).getHoTen());

            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setPhone(edtsuaphone.getText().toString());
                    nguoiDung.setHoTen(edtsuahoten.getText().toString());
                    long suanguoidung = nguoiDungDAO.updateNguoiDung(postion,nguoiDung);
                    if (suanguoidung >0){
                        Toast.makeText(ListNguoiDungActivity.this, "Sửa thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
                        Loadnguoidung();
                    }else {
                        Toast.makeText(ListNguoiDungActivity.this, "Sửa thông tin người dùng không thành công!", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ListNguoiDungActivity.this);
            builder.setTitle("Xác nhận xóa thông tin?");
            builder.setMessage("Bạn muốn xóa danh sách này");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long xoanguoidung = nguoiDungDAO.deleteNguoiDung(postion);
                    if (xoanguoidung>0){
                        Toast.makeText(ListNguoiDungActivity.this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
                        Loadnguoidung();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(ListNguoiDungActivity.this, "Xóa người dùng không thành công!", Toast.LENGTH_SHORT).show();
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
