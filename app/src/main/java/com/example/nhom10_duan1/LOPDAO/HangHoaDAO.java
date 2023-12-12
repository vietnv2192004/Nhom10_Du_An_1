package com.example.nhom10_duan1.LOPDAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhom10_duan1.DBHELPER.Mydbhelper;
import com.example.nhom10_duan1.LOPDTO.HanghoaDTO;
import com.example.nhom10_duan1.LOPDTO.HoadonDTO;

import java.util.ArrayList;

public class HangHoaDAO {




    private Mydbhelper createData;
    private SQLiteDatabase liteDatabase;

    public HangHoaDAO(Context context) {
        createData = new Mydbhelper(context);
        liteDatabase = createData.getWritableDatabase();
    }
    public ArrayList<HanghoaDTO>  getHH(){
        ArrayList<HanghoaDTO> list = new ArrayList<>();


        SQLiteDatabase sqLiteDatabase = createData.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select*from Hanghoa",null);
        if(cursor.getCount()!= 0){
            cursor.moveToFirst();
            do {
                list.add(new HanghoaDTO(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void OPEN() {
       liteDatabase = createData.getWritableDatabase();
    }

    public void Close() {
        createData.close();
    }
    public void DELETE(int maHanghoa) {
        liteDatabase.delete(HanghoaDTO.TB_NAME, HanghoaDTO.COL_MAHD + " = ?", new String[]{String.valueOf(maHanghoa)});
    }
}
