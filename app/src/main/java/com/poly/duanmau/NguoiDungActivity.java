package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.NguoiDung;

public class NguoiDungActivity extends AppCompatActivity {
    EditText edUser, edPassWord, edRePass, edPhone, edfullName;
    NguoiDungDao nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        setTitle("Thêm Người dùng");

        edUser = findViewById(R.id.edUser);
        edPassWord = findViewById(R.id.edPassWord);
        edRePass = findViewById(R.id.edRePassWord);
        edPhone = findViewById(R.id.edPhone);
        edfullName = findViewById(R.id.edfullName);

    }

    public void btnthemnguoidung(View view) {
        if (edUser.getText().toString().isEmpty()||edPassWord.getText().toString().isEmpty()||edRePass.getText().toString().isEmpty()||edPhone.getText().toString().isEmpty()||edfullName.getText().toString().isEmpty()){
            Toast.makeText(this, "Bạn phải nhập đầy đủ Thông tin!", Toast.LENGTH_SHORT).show();
        }else if (edPassWord.length()<6){
            Toast.makeText(this, "Bạn phải nhập mật khẩu ít nhất 6 kí tự!", Toast.LENGTH_SHORT).show();
        }else if (!edPassWord.getText().toString().equals(edRePass.getText().toString())){
            Toast.makeText(this, "Mật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
        }else if (edPhone.length()<10){
            Toast.makeText(this, "Bạn phải nhập số điện thoại ít nhất 10 kí tự!", Toast.LENGTH_SHORT).show();
        }else {
            nguoiDungDAO = new NguoiDungDao(NguoiDungActivity.this);

            NguoiDung nguoiDung = new NguoiDung();
            nguoiDung.setUserName(edUser.getText().toString());
            nguoiDung.setPassWord(edPassWord.getText().toString());
            nguoiDung.setPhone(edPhone.getText().toString());
            nguoiDung.setHoTen(edfullName.getText().toString());

            long themnguoidung = nguoiDungDAO.insertNguoiDung(nguoiDung);
            if (themnguoidung >0){
                Toast.makeText(getApplicationContext(),"Thêm Người dùng thành công",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Thêm Người dùng không thành công!",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void btnhuynguoidung(View view) {
        edUser.setText("");
        edfullName.setText("");
        edRePass.setText("");
        edPhone.setText("");
        edPassWord.setText("");
    }

    public void btndsnguoidung(View view) {
        Intent intent= new Intent(NguoiDungActivity.this,ListNguoiDungActivity.class);
        startActivity(intent);
    }

}
