package com.example.moneyassistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.view.View;

import com.bumptech.glide.util.Util;

import java.util.Date;

public class dbhelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DATE_TYPE = " DATETIME";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";
    private static final String NOTNULL = "not null";
    private static final String DATABASE_NAME = "Money.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract.TransactionEntry.TABLE_NAME + " (" +
                    Contract.TransactionEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_DATE + DATE_TYPE +NOTNULL+ COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_AMOUNT + INTEGER_TYPE+ NOTNULL + COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_SUBJECT + TEXT_TYPE +NOTNULL+ COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_Reciept + BLOB_TYPE +"Null"+ " )";
    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + Contract.TransactionEntry.TABLE_NAME2 + " (" +
                    Contract.TransactionEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_DATE + DATE_TYPE + COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_SUBJECT + TEXT_TYPE + COMMA_SEP +
                    Contract.TransactionEntry.COLUMN_Reciept + BLOB_TYPE +"Null"+ " )";

    public dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
public void open(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
}


public void insertIncome(String date,String amount,String subject,byte[] reciept){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="INSERT INTO Income VAlUES(null,?,?,?,?)";
    SQLiteStatement statement = db.compileStatement(sql);
    statement.clearBindings();
    statement.bindString(1,date);
    statement.bindString(2,amount);
    statement.bindString(3,subject);
    statement.bindBlob(4,reciept);
    statement.executeInsert();

}
    public void insertExpense(String date,String amount,String subject,byte[] reciept){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="INSERT INTO Expense VAlUES(null,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,date);
        statement.bindString(2,amount);
        statement.bindString(3,subject);
        statement.bindBlob(4,reciept);
        statement.executeInsert();

    }
    public Cursor DisplayIncome() {
        SQLiteDatabase database = getWritableDatabase();
        String[] columns = new String[] { "_id", "date","amount","subject"};
        Cursor cursor = database.query(Contract.TransactionEntry.TABLE_NAME,columns,null,null,null,null,"date DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }

    public Cursor DisplayExpense() {
        SQLiteDatabase database = getWritableDatabase();
        String[] columns = new String[] { "_id", "date","amount","subject"};
        Cursor cursor = database.query(Contract.TransactionEntry.TABLE_NAME2,columns,null,null,null,null,"date DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor getData(String Id){
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery("Select*from Income where _id=?",new String[]{Id});
    }
    public int Update_Income(String Id,String date,String amount,String subject) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("amount", amount);
        contentValues.put("subject", subject);
        int i = database.update("Income", contentValues,
                "_id"+ " = " +Id, null);
        return i;
    }
    public void Delete_Income(String Id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("Income", "_id" + "="
                + Id, null);
    }
    public Cursor getDataex(String Id){
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery("Select*from Expense where _id=? ",new String[]{Id});
    }
    public int Update_Expense(String Id,String date,String amount,String subject) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("amount", amount);
        contentValues.put("subject", subject);
        int i = database.update("Expense", contentValues,
                "_id"+ " = " +Id, null);
        return i;
    }
    public void Delete_Expense(String Id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("Expense", "_id" + "="
                + Id, null);
    }
    public Cursor Report(){
        SQLiteDatabase database = getWritableDatabase();

        Cursor  cursor = database.rawQuery("SELECT strftime('%m-%Y',date) ,_id from Income Group By strftime('%m-%Y',date)",null);
        if (cursor != null) {

            cursor.moveToFirst();
        }
        return cursor;
    }
    public Cursor ReportIncome(String strf){
        SQLiteDatabase database = getWritableDatabase();

        return database.rawQuery("SELECT strftime('%m-%Y',date) ,sum(amount)  from Income where strftime('%m-%Y',date) =? ",new String[]{strf});

    }
    public Cursor ReportExpense(String strf){
        SQLiteDatabase database = getWritableDatabase();

        return database.rawQuery("SELECT strftime('%m-%Y',date) ,sum(amount) from Expense where strftime('%m-%Y',date) =? ",new String[]{strf});

    }
}