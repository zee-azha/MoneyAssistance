package com.example.moneyassistance;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Button income;
    private Button expense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        income = (Button) findViewById(R.id.income);
        expense = (Button) findViewById(R.id.expense);

        //loading the default fragment
        loadFragment(new frame_menu());
        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(2);
        menuItem.setChecked(true);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this, List_Income.class);
                startActivity(pindah);
                finish();
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this, List_Expense.class);
                startActivity(pindah);
                finish();
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
                Intent actincome = new Intent(MainActivity.this, Income.class);
                startActivity(actincome);
                finish();
                break;

            case R.id.action_expense:

                fragment = new frame_expense();
                Intent actexpense = new Intent(MainActivity.this,expense.class);
                startActivity(actexpense);
                finish();
                break;

            case R.id.action_menu:
                fragment = new frame_menu();
                break;

            case R.id.action_info:
                fragment = new frame_info();
                break;
        }

        return loadFragment(fragment);
    }



}
