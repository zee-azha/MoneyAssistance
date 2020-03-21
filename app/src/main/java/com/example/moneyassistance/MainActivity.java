package com.example.moneyassistance;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //loading the default fragment

        loadFragment(new frame_menu());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);
        Menu menu = navigation.getMenu();
        MenuItem menuItem =menu.getItem(2);
        menuItem.setChecked(true);
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
