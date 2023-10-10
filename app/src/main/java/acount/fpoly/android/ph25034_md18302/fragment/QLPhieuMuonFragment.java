package acount.fpoly.android.ph25034_md18302.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import acount.fpoly.android.ph25034_md18302.DAO.PhieuMuonDAO;
import acount.fpoly.android.ph25034_md18302.DAO.SachDAO;
import acount.fpoly.android.ph25034_md18302.DAO.ThanhVienDAO;
import acount.fpoly.android.ph25034_md18302.R;
import acount.fpoly.android.ph25034_md18302.adapter.PhieuMuonAdapter;
import acount.fpoly.android.ph25034_md18302.model.PhieuMuon;
import acount.fpoly.android.ph25034_md18302.model.Sach;
import acount.fpoly.android.ph25034_md18302.model.ThanhVien;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    RecyclerView recyclerViewQLPhieuMuon;
    ArrayList<PhieuMuon> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qlphieumuon, container, false);

        recyclerViewQLPhieuMuon = view.findViewById(R.id.recycle_QLPhieuMuon);
        FloatingActionButton floatAdd = view.findViewById(R.id. floatAdd);

        // data

        
        //adapter
        loadData();


        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view ;
    }

    private  void loadData(){

        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getDSPhieuMuon();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewQLPhieuMuon.setLayoutManager(linearLayoutManager);

        PhieuMuonAdapter adapter = new PhieuMuonAdapter(list, getContext());
        recyclerViewQLPhieuMuon.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieumuon, null);
        Spinner spn_ThanhVien = view.findViewById(R.id.spn_ThanhVien);
        Spinner spn_Sach = view.findViewById(R.id.spn_Sach);

        getDataThanhVien(spn_ThanhVien);
        getDataSach(spn_Sach);
        builder.setView(view);


        //add phiếu mượn
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //lấy mã thành viên
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spn_ThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("matv");

                //lấy mã sách
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spn_Sach.getSelectedItem();
                int masach = (int) hsSach.get("masach");

                //lấy tiền
                int tien = (int) hsSach.get("giathue");

                themPhieuMuon(matv, masach, tien);
            }
        });


        //hủy thêm
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //hiển thị thành viên lên spn
    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list){
            HashMap<String , Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }


    //hiển thị sách lên spn
    private void getDataSach(Spinner spnSach){
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sach : list){
            HashMap<String , Object> hs = new HashMap<>();
            hs.put("masach", sach.getMasach());
            hs.put("tensach", sach.getTensach());
            hs.put("giathue", sach.getGiathue());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }


    // thêm phiếu mượn
    private void themPhieuMuon(int matv, int masach, int tien){

        //lấy mã thủ thư
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", "");

        //lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay,  0, tien);
        boolean kiemtra = phieuMuonDAO.themPhieuMuon(phieuMuon);
        if (kiemtra == true){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        } else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }


    }

}
