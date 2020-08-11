package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.Model.NguoiDung;
import com.poly.duanmau.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassWord;
    private CheckBox remember;
    TextView tvdangky;
    List<NguoiDung> nguoiDungList;
    NguoiDungDao nguoiDungDAO;
    Button btndangnhap_lg, btnthoat_lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Đăng nhập");

        //ánh xạ
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        remember = findViewById(R.id.remember);
        btndangnhap_lg = findViewById(R.id.btnlogin_Login);
        btnthoat_lg = findViewById(R.id.btncancel_Login);
        tvdangky = findViewById(R.id.tvdangkytk_Login);

        Laytaikhoan();

        //button đăng nhập
        btndangnhap_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.alpha_click);
                btndangnhap_lg.startAnimation(animation);

                String struser = edtUserName.getText().toString();
                String strpass = edtPassWord.getText().toString();
                boolean check = remember.isChecked();

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
                                Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        //button thoát login
        btnthoat_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.alpha_click);
                btnthoat_lg.startAnimation(animation);


            }
        });

        //textview đăng ký tài khảon
        tvdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this,R.anim.alpha_click);
                tvdangky.startAnimation(animation);

            }
        });

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



}
