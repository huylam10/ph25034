package acount.fpoly.android.ph25034_md18302.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import acount.fpoly.android.ph25034_md18302.DAO.PhieuMuonDAO;
import acount.fpoly.android.ph25034_md18302.R;
import acount.fpoly.android.ph25034_md18302.model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {

    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycle_phieumuon, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_mapm.setText("Mã phiếu mượn: " + list.get(position).getMapm());
        holder.txt_matv.setText("Mã thành viên: " + list.get(position).getMatv());
        holder.txt_tentv.setText("Tên thành viên: " + list.get(position).getTentv());
        holder.txt_matt.setText("Mã thủ thư: " + list.get(position).getMatt());
        holder.txt_tentt.setText("Mã tên thủ thư: " + list.get(position).getTentt());
        holder.txt_masach.setText("Mã sách: " + list.get(position).getMasach());
        holder.txt_tensach.setText("Mã phiếu mượn: " + list.get(position).getMasach());
        holder.txt_ngay.setText("Mã phiếu mượn: " + list.get(position).getNgay());
        String trangThai = "";
        if (list.get(position).getTrasach() == 1){
            trangThai = "Đã trả sách";
            holder.btn_trasach.setVisibility(View.GONE);
        } else {
            trangThai = "Chưa trả sách";
        }
        holder.txt_trangthai.setText("Trạng Thái: " + trangThai);
        holder.txt_tien.setText("Tiền thuê: " + list.get(position).getTienthue());

        holder.btn_trasach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemtra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if (kiemtra){
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Thay đổi trạng thái 0 thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_mapm, txt_matv, txt_tentv, txt_matt, txt_tentt, txt_masach, txt_tensach, txt_ngay, txt_trangthai, txt_tien;
        Button btn_trasach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_mapm = itemView.findViewById(R.id.maPM_txt);
            txt_matv = itemView.findViewById(R.id.maTV_txt);
            txt_tentv = itemView.findViewById(R.id.tenTV_txt);
            txt_matt = itemView.findViewById(R.id.maTT_txt);
            txt_tentt = itemView.findViewById(R.id.tenTT_txt);
            txt_masach = itemView.findViewById(R.id.maSACH_txt);
            txt_tensach = itemView.findViewById(R.id.tenSach_txt);
            txt_ngay = itemView.findViewById(R.id.ngay_txt);
            txt_trangthai = itemView.findViewById(R.id.trangThai_txt);
            txt_tien = itemView.findViewById(R.id.tienthue_txt);
            btn_trasach = itemView.findViewById(R.id.trasach_btn);



        }
    }
}
