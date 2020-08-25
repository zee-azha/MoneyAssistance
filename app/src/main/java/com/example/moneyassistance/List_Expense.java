package com.example.moneyassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class List_Expense extends AppCompatActivity {
    private dbhelper Dbhelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    final String[] from = new String[]{"_id", "date", "amount", "subject"};
    final int[] to = new int[]{R.id.number, R.id.date, R.id.amount, R.id.subject};
    private ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__expense);
        Intent intent = getIntent();
        Dbhelper = new dbhelper(this);
        Cursor cursor = Dbhelper.DisplayExpense();
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        Back = (ImageView) findViewById(R.id.back);
        adapter = new SimpleCursorAdapter(this, R.layout.activity_view__expense, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView noTextView = (TextView) view.findViewById(R.id.number);
                String number = noTextView.getText().toString();
                Intent modify_intent = new Intent(getApplicationContext(), Expense_form.class);
                modify_intent.putExtra("_id", number);
                startActivity(modify_intent);
            }

        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_Expense.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void onBackPressed() {

    }
}
