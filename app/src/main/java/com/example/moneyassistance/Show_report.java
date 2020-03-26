package com.example.moneyassistance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Show_report extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    final String[] from = new String[]{"_id", "strftime('%m-%Y',date)"};
    final int[] to = new int[]{R.id.number, R.id.date};
    private dbhelper Dbhelper;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);
        loadFragment(new frame_info());
        Dbhelper = new dbhelper(this);
        Cursor cursor = Dbhelper.Report();
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        adapter = new SimpleCursorAdapter(this, R.layout.activity_monthly, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(3);
        menuItem.setChecked(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView noTextView = (TextView) view.findViewById(R.id.date);
                String bulan = noTextView.getText().toString();
                Intent modify_intent = new Intent(getApplicationContext(), TableReport.class);
                modify_intent.putExtra("bulan", bulan);
                startActivity(modify_intent);
            }

        });
    }
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
                Intent actincome = new Intent(Show_report.this, Income.class);
                startActivity(actincome);
                finish();
                break;

            case R.id.action_expense:

                fragment = new frame_expense();
                Intent actexpense = new Intent(Show_report.this,expense.class);
                startActivity(actexpense);
                finish();
                break;

            case R.id.action_menu:
                fragment = new frame_menu();
                Intent actinhome = new Intent(Show_report.this, MainActivity.class);
                startActivity(actinhome);
                finish();
                break;

            case R.id.action_info:
                fragment = new frame_info();
                break;
        }

        return loadFragment(fragment);
    }
}
