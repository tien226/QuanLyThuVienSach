package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.NguoiDung;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassWord;
    private CheckBox remember;
    List<NguoiDung> nguoiDungList;
    NguoiDungDao nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng nhập");

        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        remember = findViewById(R.id.remember);
        Laytaikhoan();
    }

    public void Luutaikhoan(String user, String pass, boolean check) {
        //tạo đối tượng getSharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userFile", MODE_PRIVATE);
        // tạo đối tượng editor để lưu thay đổi
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!check) {
            //xóa tình trạng lưu trước đó
            edit.clear();
        } else {
            //lưu dữ liệu
            edit.putString("username", user);
            edit.putString("pass", pass);
            edit.putBoolean("remember", check);
        }
        edit.commit();
    }

    public void Laytaikhoan() {
        SharedPreferences sharedPreferences = getSharedPreferences("userFile", MODE_PRIVATE);
        if (sharedPreferences != null) {
            String user = sharedPreferences.getString("username", null);
            String pass = sharedPreferences.getString("pass", null);
            boolean ck = sharedPreferences.getBoolean("remember", false);
            edtUserName.setText(user);
            edtPassWord.setText(pass);
            remember.setChecked(ck);
        }
    }

    public void btndangnhaplogin(View view) {
        String struser = edtUserName.getText().toString();
        String strpass = edtPassWord.getText().toString();
        boolean check = remember.isChecked();

//        DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
        nguoiDungDAO = new NguoiDungDao(LoginActivity.this);
        nguoiDungList = nguoiDungDAO.getAllNguoiDung();

        if (struser.isEmpty() || strpass.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không được bỏ trống!", Toast.LENGTH_SHORT).show();
        } else {
            if (struser.equalsIgnoreCase("admin") && strpass.equalsIgnoreCase("admin")) {
                Luutaikhoan(struser, strpass, check);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                for (int i = 0; i<nguoiDungList.size(); i++){
                    if (struser.equals(nguoiDungList.get(i).getUserName()) && strpass.equals(nguoiDungList.get(i).getPassWord())){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "Tên đăng nhập và mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public void btnCancel(View view) {
        edtUserName.setText("");
        edtPassWord.setText("");
    }

}
