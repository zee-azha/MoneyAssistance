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

    public Cursor readAllIncome() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                Contract.TransactionEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public boolean addIncome(Utils utils) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.TransactionEntry.COLUMN_DATE , utils.getDate());
        values.put(Contract.TransactionEntry.COLUMN_AMOUNT , utils.getAmount());
        values.put(Contract.TransactionEntry.COLUMN_SUBJECT , utils.getSubject());
        values.put(Contract.TransactionEntry.COLUMN_Reciept , utils.getImageAsString());

        return db.insert(Contract.TransactionEntry.TABLE_NAME, null, values) != -1;
    }
    public boolean addExpense(Utils2 utils) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.TransactionEntry.COLUMN_DATE , utils.getDate());
        values.put(Contract.TransactionEntry.COLUMN_AMOUNT , utils.getAmount());
        values.put(Contract.TransactionEntry.COLUMN_SUBJECT , utils.getSubject());
        values.put(Contract.TransactionEntry.COLUMN_Reciept , utils.getImageAsString());

        return db.insert(Contract.TransactionEntry.TABLE_NAME2, null, values) != -1;
    }
public void insertIncome(String date,String amount,String subject,byte[] reciept){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="INSERT INTO INCOME VAlUES(null,?,?,?,?)";
    SQLiteStatement statement = db.compileStatement(sql);
    statement.clearBindings();
    statement.bindString(1,date);
    statement.bindString(2,amount);
    statement.bindString(3,subject);
    statement.bindBlob(4,reciept);
    statement.executeInsert();

}
}