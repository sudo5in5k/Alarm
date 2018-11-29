package com.example.sho.alermtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * TODO クラス説明
 * <p>
 * Created by sho on 2017/08/30.
 */

public class TimePick2 extends Fragment implements DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener {

    public TimePick2() {
    }

    public static TimePick2 newInstance(int position) {
        TimePick2 dialog = new TimePick2();
        Bundle args = new Bundle();
        args.putInt("position", position);
        dialog.setArguments(args);
        return dialog;
    }

    int pickYear;
    int pickMonth;
    int pickDay;
    int pickHour;
    int pickMinute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_pick, container, false);

        final DatePicker datePicker = v.findViewById(R.id.datepick_parts);
        TimePicker timePicker = v.findViewById(R.id.timepick_parts);

        final Switch visibleSwitch = v.findViewById(R.id.visible_switch);
        visibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    datePicker.setVisibility(View.GONE);
                } else {
                    datePicker.setVisibility(View.VISIBLE);
                }
            }
        });

        pickerInit(datePicker, timePicker);

        final Button alarmSetButton = v.findViewById(R.id.set_alarm_button);
        alarmSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("最終決定時間 ", String.valueOf(pickHour) + ":" + String.valueOf(pickMinute));
                setAlarmTime(pickHour, pickMinute);
            }
        });
        return v;
    }

    public void pickerInit(DatePicker dp, TimePicker tp) {
        final Calendar c = Calendar.getInstance();

        pickYear = c.get(Calendar.YEAR);
        Log.d("TAG", String.valueOf(pickYear));
        pickMonth = c.get(Calendar.MONTH);
        pickDay = c.get(Calendar.DAY_OF_MONTH);
        pickHour = c.get(Calendar.HOUR_OF_DAY);
        pickMinute = c.get(Calendar.MINUTE);

        dp.init(pickYear, pickMonth, pickDay, this);
        tp.setIs24HourView(true);
        tp.setHour(pickHour);
        tp.setMinute(pickMinute);
        tp.setOnTimeChangedListener(this);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        pickYear = year;
        pickMonth = monthOfYear;
        pickDay = dayOfMonth;
        Log.d(BroadcastAlarm.TAG, "Year" + year + "Month" + monthOfYear + "Day" + dayOfMonth);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        pickHour = hourOfDay;
        pickMinute = minute;
        Log.d(BroadcastAlarm.TAG, "Hour & Time Change ..." + "Hour" + hourOfDay + "Minute" + minute);
    }

    /*
     * 起動したい時刻(hour,minute)を指定
	 * 指定した時刻で毎日起動する
	 */
    public void setAlarmTime(int hour, int minute) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");

        // 設定したい時間
        Calendar calTarget = Calendar.getInstance();
        calTarget.setTimeZone(tz);
        calTarget.set(Calendar.HOUR_OF_DAY, hour);
        calTarget.set(Calendar.MINUTE, minute);
        calTarget.set(Calendar.SECOND, 0);
        long targetMS = calTarget.getTimeInMillis();

        Calendar current = Calendar.getInstance();
        current.setTimeZone(tz);
        long currentMS = current.getTimeInMillis();

        if (targetMS >= currentMS) {
            settingAlarm(BroadcastAlarm.class, targetMS);
        } else {
            calTarget.add(Calendar.DAY_OF_MONTH, 1);
            targetMS = calTarget.getTimeInMillis();
            settingAlarm(BroadcastAlarm.class, targetMS);
        }
    }

    public void settingAlarm(Class cls, long durationTime) {
        Log.d(BroadcastAlarm.TAG, "startAlarm");
        Intent intent = new Intent(getActivity(), cls);
        PendingIntent action = PendingIntent.getBroadcast(getActivity(), 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm =
                (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, durationTime, AlarmManager.INTERVAL_DAY,
                action);
    }
}


