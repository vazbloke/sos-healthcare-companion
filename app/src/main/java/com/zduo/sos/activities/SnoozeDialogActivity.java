/*
 * SOS
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.zduo.sos.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;

import com.zduo.sos.R;
import com.zduo.sos.receivers.SnoozeReceiver;
import com.zduo.sos.utils.AlarmUtil;
import com.zduo.sos.utils.NotificationUtil;

import java.util.Calendar;

public class SnoozeDialogActivity extends AppCompatActivity {

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int reminderId = getIntent().getIntExtra("NOTIFICATION_ID", 0);

        View view = getLayoutInflater().inflate(R.layout.number_picker, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Dialog);
        builder.setTitle(R.string.snooze_length);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        hourPicker = (NumberPicker) view.findViewById(R.id.picker1);
        minutePicker = (NumberPicker) view.findViewById(R.id.picker2);

        setUpHourPicker();
        setUpMinutePicker();

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (hourPicker.getValue() != 0 || minutePicker.getValue() != 0) {
                    NotificationUtil.cancelNotification(getApplicationContext(), reminderId);

                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE, minutePicker.getValue());
                    calendar.add(Calendar.HOUR, hourPicker.getValue());
                    Intent alarmIntent = new Intent(getApplicationContext(), SnoozeReceiver.class);
                    AlarmUtil.setAlarm(getApplicationContext(), alarmIntent, reminderId, calendar);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("snoozeHours", hourPicker.getValue());
                    editor.putInt("snoozeMinutes", minutePicker.getValue());
                    editor.apply();
                }
                finish();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });

        builder.setView(view).create().show();
    }

    public void setUpHourPicker() {
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(24);
        hourPicker.setValue(sharedPreferences.getInt("snoozeHours", getResources().getInteger(R.integer.default_snooze_hours)));

        String[] hourValues = new String[25];
        for (int i = 0; i < hourValues.length; i++) {
            hourValues[i] = String.format(getResources().getQuantityString(R.plurals.time_hour, i), i);
        }
        hourPicker.setDisplayedValues(hourValues);
    }

    public void setUpMinutePicker() {
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(60);
        minutePicker.setValue(sharedPreferences.getInt("snoozeMinutes", getResources().getInteger(R.integer.default_snooze_minutes)));

        String[] minuteValues = new String[61];
        for (int i = 0; i < minuteValues.length; i++) {
            minuteValues[i] = String.format(getResources().getQuantityString(R.plurals.time_minute, i), i);
        }
        minutePicker.setDisplayedValues(minuteValues);
    }
}
