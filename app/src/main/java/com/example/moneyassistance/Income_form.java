package com.example.moneyassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Income_form extends AppCompatActivity {
private dbhelper DbHelper;

EditText Date;
EditText Amount;
EditText Subject;
ImageView Reciept;
ImageView Back;
Button Delete,Edit,setdate;
private SimpleDateFormat dateFormatter;
private String Id;
private DatePickerDialog datePickerDialog;
    AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form);
        DbHelper = new dbhelper(this);
        Date = (EditText) findViewById(R.id.date);
        Amount = (EditText) findViewById(R.id.setAmount);
        Subject = (EditText) findViewById(R.id.setNote);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);;
        Back = (ImageView) findViewById(R.id.back);
        setdate =(Button) findViewById(R.id.setDate);
        Edit =(Button) findViewById(R.id.edit);
        Delete =(Button) findViewById(R.id.delete);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DbHelper.getReadableDatabase();
        Intent intent = getIntent();
        String num = intent.getStringExtra("_id");
        Id =num;
        Cursor cursor = DbHelper.getData(Id);
        while (cursor.moveToNext()){
            String date = cursor.getString(1);
            String amount = cursor.getString(2);
            String subject = cursor.getString(3);
            byte [] reciept = cursor.getBlob(4);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(reciept);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Date.setText(date);
            Amount.setText(amount);
            Subject.setText(subject);
            photoView.setImageBitmap(bitmap);
        }

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditPressed();

            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onDelPressed();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Income_form.this,List_Income.class);
                startActivity(intent);
                finish();
            }
        });
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

    }
    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onEditPressed() {
         new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Data will edited")
                .setCancelText("No")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        String subject = Subject.getText().toString();
                        String amount = Amount.getText().toString();
                        String date = Date.getText().toString();
                        DbHelper.Update_Income(Id, date, amount, subject);
                        new SweetAlertDialog(Income_form.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Edit Successfully")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        Intent c = new Intent(Income_form.this, List_Income.class);
                                        c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        c.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(c);
                                        finish();

                                    }
                                })
                                .show();
                    }
                }).show();

    }



    public void onDelPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Data will Delete")
                .setCancelText("No")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                DbHelper.Delete_Income(Id);
                        new SweetAlertDialog(Income_form.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Deleted had Successfully")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        Intent c = new Intent(Income_form.this, List_Income.class);
                                        c.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        c.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(c);
                                        finish();

                                    }
                                })
                                .show();
                    }
                }).show();

            }
        }



