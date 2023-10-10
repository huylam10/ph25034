package acount.fpoly.android.ph25034_md18302;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

public class manHinhchao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinhchao);

        ImageView ivlogo = findViewById(R.id.ivlogo);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(manHinhchao.this, "FPT POLYTECHNIC", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(manHinhchao.this, login.class));
            }
        },2000);

    }
}