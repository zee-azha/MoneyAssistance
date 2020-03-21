package com.example.moneyassistance;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Utils2 {
    private static final float PREFERRED_WIDTH = 250;
    private static final float PREFERRED_HEIGHT = 250;
    private String date;
    private String amount;
    private String subject;
    private String reciept;
    private SimpleDateFormat dateFormatter;

    public static final int COL_ID = 0;
    public static final int COL_DATE = 1;
    public static final int COL_AMOUNT = 2;
    public static final int COL_SUBJECT = 3;
    public static final int COL_RECIEPT = 4;

    public Utils2(Cursor cursor) {
        this.date = cursor.getString(COL_DATE);
        this.amount = cursor.getString(COL_AMOUNT);
        this.subject = cursor.getString(COL_SUBJECT);
        this.reciept = cursor.getString(COL_RECIEPT);
    }

    public Utils2(String Date, String Amount, String Subject, Bitmap image) {


        this.date = Date ;
        this.amount = Amount ;
        this.subject = Subject;
        this.reciept = bitmapToString(resizeBitmap(image));
    }

    public String getDate() {
        return this.date;
    }
    public String getAmount() {
        return this.amount;
    }
    public String getSubject() {
        return this.subject;
    }

    public Bitmap get() {
        return stringToBitmap(this.reciept);
    }

    public String getImageAsString() {
        return this.reciept;
    }

    private static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }





    private static Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = PREFERRED_WIDTH / width;
        float scaleHeight = PREFERRED_HEIGHT / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }
}
