package com.example.prj_first;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="db";
    private static final int DATABASE_VERSION=1;
    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists db_kh(makh text primary key, tenkh text, sdt Interger,diachikh text,email text, mota text)");
        sqLiteDatabase.execSQL("create table if not exists customer_choose(id INTEGER primary key autoincrement ,makh text)");
        sqLiteDatabase.execSQL("create table if not exists cart(id integer primary key autoincrement , img int,tensp text,soluong text,tongtien text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS db_kh");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS customer_choose");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(sqLiteDatabase);
    }
    public Boolean insertKH(String makh,String tenkh,String sdt,String  diachikh,String email,String mota)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("makh",makh);
        values.put("tenkh",tenkh);
        values.put("sdt",sdt);
        values.put("diachikh",diachikh);
        values.put("email",email);
        values.put("mota",mota);
        long result = db.insert("db_kh",null,values);
        if(result==-1)
            return false;
        return true;
    }
    public Boolean insertCart(int img, String tensp, String soluong, String tongtien)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("img",img);
        values.put("tensp",tensp);
        values.put("soluong",soluong);
        values.put("tongtien",tongtien);
        long result = db.insert("cart",null,values);
        db.close();
        if(result==-1)
            return false;
        return true;
    }
    public Boolean insertOder(String tenkh,String soluong, String tongtien)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table if not exists donhang(id integer primary key autoincrement, tenkh text, soluong text,tongtien text)");
        ContentValues values = new ContentValues();
        values.put("tenkh",tenkh);
        values.put("soluong",soluong);
        values.put("tongtien",tongtien);
        long result = db.insert("donhang",null,values);
        db.close();
        if(result==-1)
            return false;
        return true;
    }
    public Cursor getData(String sql){
        SQLiteDatabase dtb = getReadableDatabase();
        return dtb.rawQuery(sql,null);
    }
    public void queryData(String sql){
        SQLiteDatabase dtb = getWritableDatabase();
        dtb.execSQL(sql);
        onCreate(dtb);
    }
    public void setCustomer(String makh)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from customer_choose");
        ContentValues values = new ContentValues();
        values.put("makh",makh);
        sqLiteDatabase.insert("customer_choose",null,values);
    }
    public String getCustomer()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select makh from customer_choose LIMIT 1",null);
        String makh = null;
        if(cursor.moveToFirst()) {
            makh = cursor.getString(0);
        }
        cursor.close();
        sqLiteDatabase.close();
        return makh;
    }
    public void deleteCustomer(String makh) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("customer_choose", "makh = ?", new String[] { makh });
        sqLiteDatabase.close();
    }
}
