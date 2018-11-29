package com.example.sho.alermtest;

import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentmanager = getSupportFragmentManager();//起動
        FragmentTransaction fragmentTransaction = fragmentmanager.beginTransaction();

        if(fragmentTransaction == null){
            fragmentTransaction.add(R.id.container, RootFragment.newInstance(0));
        }
        fragmentTransaction.replace(R.id.container, RootFragment.newInstance(0));

        fragmentTransaction.commit();
    }
}
