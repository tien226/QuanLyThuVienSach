package com.poly.duanmau;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.poly.duanmau.DAO.NguoiDungDao;
import com.poly.duanmau.Database.DatabaseHelper;
import com.poly.duanmau.Model.NguoiDung;

public class NguoiDungActivity extends AppCompatActivity {
    TextInputEditText edUser, edPassWord, edRePass, edPhone, edfullName;
    TextInputLayout textInputLayoutedUser, textInputLayoutedPassWord, textInputLayoutedRePass, textInputLayoutedfullName, textInputLayoutEdPhone;
    NguoiDungDao nguoiDungDAO;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        setTitle("Thêm Người dùng");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edUser = findViewById(R.id.edUser);
        edPassWord = findViewById(R.id.edPassWord);
        edRePass = findViewById(R.id.edRePassWord);
        edPhone = findViewById(R.id.edPhone);
        edfullName = findViewById(R.id.edfullName);
        textInputLayoutedUser = findViewById(R.id.textInputLayoutEdUser);
        textInputLayoutedPassWord = findViewById(R.id.textInputLayoutEdPassWord);
        textInputLayoutedRePass = findViewById(R.id.textInputLayoutEdRePassWord);
        textInputLayoutedfullName = findViewById(R.id.textInputLayoutEdfullName);
        textInputLayoutEdPhone = findViewById(R.id.textInputLayoutEdPhone);

    }


    public void btnthemnguoidung(View view) {

        if (edUser.getText().toString().isEmpty()){
            textInputLayoutedUser.setError("Không được bỏ trống!");
        } else if (edPassWord.getText().toString().isEmpty()){
            textInputLayoutedPassWord.setError("Không được bỏ trống!");
        } else if (edRePass.getText().toString().isEmpty()){
            textInputLayoutedRePass.setError("Không được bỏ trống!");
        } else if (edPhone.getText().toString().isEmpty()){
            textInputLayoutEdPhone.setError("Không được bỏ trống!");
        } else if (edfullName.getText().toString().isEmpty()){
            textInputLayoutedfullName.setError("Không được bỏ trống!");
        } else if (edPassWord.length()<6){
            textInputLayoutedPassWord.setError("Bạn phải nhập mật khẩu ít nhất 6 kí tự!");
        }else if (!edPassWord.getText().toString().equals(edRePass.getText().toString())){
            Toast.makeText(this, "Mật khẩu nhập lại không trùng khớp!", Toast.LENGTH_SHORT).show();
        }else if (edPhone.length()<10){
            textInputLayoutEdPhone.setError("Bạn phải nhập số điện thoại ít nhất 10 kí tự!");
        }else {

            textInputLayoutedUser.setError("");
            textInputLayoutedPassWord.setError("");
            textInputLayoutedRePass.setError("");
            textInputLayoutEdPhone.setError("");
            textInputLayoutedfullName.setError("");

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
        textInputLayoutedUser.setError("");
        textInputLayoutedPassWord.setError("");
        textInputLayoutedRePass.setError("");
        textInputLayoutEdPhone.setError("");
        textInputLayoutedfullName.setError("");
    }

    public void btndsnguoidung(View view) {
        Intent intent= new Intent(NguoiDungActivity.this,ListNguoiDungActivity.class);
        startActivity(intent);
    }

}
