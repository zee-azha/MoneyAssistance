package com.example.moneyassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.zip.Inflater;

public class TableReport extends AppCompatActivity {
    private String stfr;
    TextView income,expense,save,Bulan;
    dbhelper DbHelper;
    private String Income,Expense;
    Integer in,ex,sum;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_report);
        final Intent intent = getIntent();
        String bulan = intent.getStringExtra("bulan");
        stfr = bulan;

        DbHelper = new dbhelper(this);
        DbHelper.open();
        TextView Bulan =(TextView) findViewById(R.id.bulan);
        Button back = (Button) findViewById(R.id.back);
        income =(TextView) findViewById(R.id.income);
        expense =(TextView) findViewById(R.id.expense);
        save =(TextView) findViewById(R.id.save);
        Bulan.setText(bulan);
        Cursor cursor = DbHelper.ReportIncome(stfr);

        while (cursor.moveToNext()) {
            if (cursor.isNull(1)){
                Income="0";
                income.setText(Income);

            }else{

                Income = cursor.getString(1);
                income.setText(Income);
            }

        }
            Cursor cursor1 = DbHelper.ReportExpense(stfr);

            while (cursor1.moveToNext()){
                if (cursor1.isNull(1)){
                    Expense="0";
                    expense.setText(Expense);

                }else {

                    Expense = cursor1.getString(1);
                    expense.setText(Expense);
                }
            }
        in=Integer.parseInt(Income);
        ex=Integer.parseInt(Expense);
            sum= in-ex;
            save.setText(Integer.toString(sum));

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent kembali = new Intent(TableReport.this, Show_report.class);
        startActivity(kembali);
        finish();
    }
});
        }








}
