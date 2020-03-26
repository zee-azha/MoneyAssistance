package com.example.moneyassistance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

public class List_Income extends AppCompatActivity {
    private dbhelper Dbhelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    Cursor cursor;
 final String[] from = new String[]{"_id", "date", "amount", "subject"};
 final int[] to = new int[]{R.id.number, R.id.date, R.id.amount, R.id.subject};
    private ImageView Back;
    CardView card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__income);
        Back = (ImageView) findViewById(R.id.back);
        Dbhelper = new dbhelper(this);
        Cursor cursor = Dbhelper.DisplayIncome();
        listView = (ListView) findViewById(R.id.list_view);

        listView.setEmptyView(findViewById(R.id.empty));
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view__income, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView noTextView = (TextView) view.findViewById(R.id.number);
                String number = noTextView.getText().toString();
                Intent modify_intent = new Intent(getApplicationContext(), ViewForm.class);
                modify_intent.putExtra("_id", number);
                startActivity(modify_intent);

            }



        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_Income.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
