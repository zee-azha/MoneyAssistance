package com.example.moneyassistance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;


public class Income extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    EditText nilai;
    EditText catat;
    EditText tanggal;
    TextView amount;
    TextView note;
    Button trans;
    Button take;
    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;
    private ImageView cam;
    private DatePickerDialog datePickerDialog;
    private DatePicker datePicker;
    private Button pict;
    private Calendar calendar,calendar1;
    private int year, month, day;
    private Button setdate;
    TextView Date;
  private dbhelper Dbhelper;


    private Uri selectedImageUri;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        //loading the default fragment
        loadFragment(new frame_income());
        //declare
        tanggal = (EditText) findViewById(R.id.date);
        nilai =(EditText) findViewById(R.id.setAmount);
        catat = (EditText) findViewById(R.id.setNote);
        Date = (TextView) findViewById(R.id.date);
        amount =(TextView)findViewById(R.id.setAmount);
        note = (TextView)findViewById(R.id.setNote);
        cam =(ImageView) findViewById(R.id.foto);
        Dbhelper = new dbhelper(this);
        trans=(Button)findViewById(R.id.save);
        take=(Button)findViewById(R.id.take);
        setdate=(Button)findViewById(R.id.setDate);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        //Navigation
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(0);
        menuItem.setChecked(true);
       trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tanggal.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Insert Date",Toast.LENGTH_LONG).show();
                }else if (nilai.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Insert Amount ",Toast.LENGTH_LONG).show();
                }else if (note.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Insert Note ", Toast.LENGTH_LONG).show();
                }else if (cam.getDrawable()==null){
                    Toast.makeText(getApplicationContext(), "Please Insert Reciept picture ", Toast.LENGTH_LONG).show();
                }
                else {
                    saveImageInDB(view);

                    nilai.setText(null);
                    tanggal.setText(null);
                    note.setText(null);
                    cam.setImageResource(0);
                }
                }
            public void saveImageInDB(View view){

                try {
                    Dbhelper.insertIncome(
                            tanggal.getText().toString().trim(),
                            nilai.getText().toString().trim(),
                            note.getText().toString().trim(),
                            imageToByte(cam)
                    ); Toast.makeText(getApplicationContext(),"ADD Income success",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                String amn = amount.getText().toString();
//                String strdate =Date.getText().toString();
//                String not = note.getText().toString();
//                   // Bitmap bitmap =((BitmapDrawable)cam.getDrawable()).getBitmap();
//                    imageToByte(cam);
//    //                   Dbhelper.addIncome(new Utils(strdate,amn,not,image));
//                    Dbhelper.insertIncome(strdate,amn,not,bitmap);
               }

           private byte[] imageToByte(ImageView image) {
                Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
               ByteArrayOutputStream stream =new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
               byte[] byteArray = stream.toByteArray();
               return byteArray;
           }
       });
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        //camera function
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setRequestImage();
            }
        });
    }
    private void setRequestImage() {
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Add Image")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA);
                                }
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                cam.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cam.setImageBitmap(imageBitmap);
        }
    }
//fragment function
            private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container,fragment )
                    .commit();
            return true;
        }
        return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_income:
                fragment = new frame_income();
                break;
            case R.id.action_expense:
                fragment = new frame_expense();
                Intent actexpense = new Intent(Income.this,expense.class);
                startActivity(actexpense);
                finish();
                break;
            case R.id.action_menu:
                fragment = new frame_menu();
                Intent acthome = new Intent(Income.this,MainActivity.class);
                startActivity(acthome);
                finish();
                break;
            case R.id.action_info:
                fragment = new frame_info();
                break;
        }
        return loadFragment(fragment);
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
}
