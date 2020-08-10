package com.poly.duanmau.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.poly.duanmau.R;

public class ManhinhchaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);

        TextView wellcomlibrary = (TextView) findViewById(R.id.Et_title);
        ImageView iconlibrary = (ImageView) findViewById(R.id.icon_library);
        ImageView Loading_library = findViewById(R.id.loading_library);

        Glide.with(this).load(R.drawable.loading).into(Loading_library);
//         Loading_library.setImageResource();
        iconlibrary.setImageResource(R.drawable.splash1);
        wellcomlibrary.setText("\"Chào mừng bạn đến với ứng dụng quản lý thư viện\"");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManhinhchaoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
