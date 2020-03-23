package com.example.moneyassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Expense_form extends AppCompatActivity {
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
        setContentView(R.layout.activity_expense_form);
        DbHelper = new dbhelper(this);
        Date = (EditText) findViewById(R.id.date);
        Amount = (EditText) findViewById(R.id.setAmount);
        Subject = (EditText) findViewById(R.id.setNote);
        Reciept = (ImageView) findViewById(R.id.foto);
        Back = (ImageView) findViewById(R.id.back);
        setdate =(Button) findViewById(R.id.setDate);
        Edit =(Button) findViewById(R.id.edit);
        Delete =(Button) findViewById(R.id.delete);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        DbHelper.getReadableDatabase();
        Intent intent = getIntent();
        String num = intent.getStringExtra("_id");
        Id =num;
        Cursor cursor = DbHelper.getDataex(Id);
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
            Reciept.setImageBitmap(bitmap);
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
                Intent intent = new Intent(Expense_form.this,List_Expense.class);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to change expense data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = Subject.getText().toString();
                String amount = Amount.getText().toString();
                String date = Date.getText().toString();
                DbHelper.Update_Expense(Id,date,amount,subject);
                Toast.makeText(getApplicationContext(), "The Expense Data had changed", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Expense_form.this,List_Expense.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void onDelPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to Delete expense data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbHelper.Delete_Expense(Id);
                Intent intent = new Intent(Expense_form.this,List_Expense.class);
                Toast.makeText(getApplicationContext(), "The Expense Data had Delete", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
