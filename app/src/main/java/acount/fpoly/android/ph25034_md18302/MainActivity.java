package acount.fpoly.android.ph25034_md18302;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import acount.fpoly.android.ph25034_md18302.DAO.ThuThuDAO;
import acount.fpoly.android.ph25034_md18302.fragment.QLLoaiSachFragment;
import acount.fpoly.android.ph25034_md18302.fragment.QLPhieuMuonFragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_Bar);
        FrameLayout frameLayout = findViewById(R.id.fram_layout);
        NavigationView navigationView = findViewById(R.id.navigation_View);
        drawerLayout = findViewById(R.id.draer_layout);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.mQLPhieuMuon:
                        fragment  = new QLPhieuMuonFragment();
                        break;

                    case R.id.mQLloaiSach:
                        fragment = new QLLoaiSachFragment();
                        break;

                    case R.id.mThoat:
                        Intent intent = new Intent(MainActivity.this, login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    case R.id.mDoiMK:
                        showDialogDoiMK();
                        break;

                    default:
                        fragment = new QLPhieuMuonFragment();
                        break;
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fram_layout, fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMK(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cập nhật", null)
                .setPositiveButton("Hủy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);

        EditText edt_pwmk= view.findViewById(R.id.edt_pwdmk);
        EditText edt_newpwmk = view.findViewById(R.id.edt_newpwdmk);
        EditText edt_renewpwmk = view.findViewById(R.id.edt_renewpwdmk);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = edt_pwmk.getText().toString();
                String newpass = edt_newpwmk.getText().toString();
                String renewpass = edt_renewpwmk.getText().toString();
                if (pass.equals("") || newpass.equals("") || renewpass.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (newpass.equals(renewpass)){
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt", "");
                        // cập  nhật
                        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                        int check = thuThuDAO.capNhatMatKhau(matt, pass, newpass);
                        if (check == 1){
                            Toast.makeText(MainActivity.this, "cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (check == 0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Nhập lại khẩu! mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}