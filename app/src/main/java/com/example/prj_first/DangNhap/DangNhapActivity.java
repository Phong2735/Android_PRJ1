package com.example.prj_first.DangNhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prj_first.R;
import com.example.prj_first.TrangChu.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DangNhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        TextInputEditText txte_user = (TextInputEditText) findViewById(R.id.txtieu);
        TextInputLayout txtl_user = (TextInputLayout) findViewById(R.id.txtilu);
        TextInputEditText txte_pass = (TextInputEditText) findViewById(R.id.txtiep);
        TextInputLayout txtl_pass = (TextInputLayout) findViewById(R.id.txtilp);
        ImageView im = (ImageView) findViewById(R.id.img1);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        View view = findViewById(android.R.id.content);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txte_user.getText().toString().equals("")||txte_pass.getText().toString().equals(""))
                {
                    Toast.makeText(DangNhapActivity.this,"Bạn phải điền đầy đủ thông tin đăng nhập",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent;
                    intent = new Intent(DangNhapActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
