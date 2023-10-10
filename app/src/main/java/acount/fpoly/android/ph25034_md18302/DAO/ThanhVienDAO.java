package acount.fpoly.android.ph25034_md18302.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import acount.fpoly.android.ph25034_md18302.database.DbHelper;
import acount.fpoly.android.ph25034_md18302.model.ThanhVien;

public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return list;
    }
}
