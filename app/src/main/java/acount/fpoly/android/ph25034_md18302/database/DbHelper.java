package acount.fpoly.android.ph25034_md18302.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, "DANGKIMONHOC", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbThuThu = "CREATE TABLE THUTHU(matt text primary key, hoten text, matkhau text)";
        sqLiteDatabase.execSQL(dbThuThu);

        String dbThanhVien = "CREATE TABLE THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
        sqLiteDatabase.execSQL(dbThanhVien);

        String dbLoai = "CREATE TABLE LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        sqLiteDatabase.execSQL(dbLoai);

        String dbSach = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        sqLiteDatabase.execSQL(dbSach);

        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer primary key autoincrement, matv integer references THANHVIEN(matv), matt text references THUTHU(matv), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer )";
        sqLiteDatabase.execSQL(dbPhieuMuon);

        //data mẫu
        sqLiteDatabase.execSQL("INSERT INTO LOAISACH VALUES(1, 'Thiếu Nhi'), (2, 'Tình cảm'),(3, 'Giáo khoa')");
        sqLiteDatabase.execSQL("INSERT INTO SACH VALUES(1, 'Hãy đợi đấy', 2400, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình android', 7000, 3)");
        sqLiteDatabase.execSQL("INSERT INTO THUTHU VALUES('thuthu01','nguyễn văn a','abc123'), ('thuthu02','nguyễn văn b','def456')");
        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN VALUES(1, 'Nguyễn huy lâm', '2003'), (2, 'Nguyễn Huy Khởi', '2005')");

        //1- trả sách, 0- chưa trả sách
        sqLiteDatabase.execSQL("INSERT INTO PHIEUMUON VALUES(1,1,'thuthu01',1,'15/10/2023',0,2500), (2,1,'thuthu02', 2, '20/10/2023',1,3000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THUTHU");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS LOAISACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SACH");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(sqLiteDatabase);
        }

    }
}
