package acount.fpoly.android.ph25034_md18302;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import acount.fpoly.android.ph25034_md18302.DAO.ThuThuDAO;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUseName = findViewById(R.id.edt_useNameLogin);
        EditText edtPassWord = findViewById(R.id.edt_passWordLogin);
        Button btnSendLogin = findViewById(R.id.btn_sendLogin);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btnSendLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUseName.getText().toString();
                String pass = edtPassWord.getText().toString();
                if (thuThuDAO.checkLogin(user, pass)){
                    // lưu share
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt", user);
                    editor.commit();

                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(login.this, "Usename pass không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}