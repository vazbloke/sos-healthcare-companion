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

package com.zduo.sos.tools;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatDateTime {

    private Context context;

    public FormatDateTime(Context mContext) {
        this.context = mContext;
    }

    public String convertDateTime(String date) {
        //TODO use joda.time
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat finalDataFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat finalTimeFormat;

        if (android.text.format.DateFormat.is24HourFormat(context)) {
            finalTimeFormat = new SimpleDateFormat("HH:mm");
        } else {
            finalTimeFormat = new SimpleDateFormat("hh:mm a");
        }

        Date parsed = null;
        try {
            parsed = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String finalData = finalDataFormat.format(parsed);
        String finalTime = finalTimeFormat.format(parsed);
        return finalData + " " + finalTime;
    }

    public String formatDate(Date date) {
        DateFormat finalDataFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat finalTimeFormat;

        if (android.text.format.DateFormat.is24HourFormat(context)) {
            finalTimeFormat = new SimpleDateFormat("HH:mm");
        } else {
            finalTimeFormat = new SimpleDateFormat("hh:mm a");
        }

        String finalData = finalDataFormat.format(date);
        String finalTime = finalTimeFormat.format(date);
        return finalData + " " + finalTime;
    }

    public String convertDateToMonthOverview(String date) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat finalDataFormat = new SimpleDateFormat("MMMM");

        Date parsed = null;
        try {
            parsed = inputFormat.parse(date);
            // Because database's average is the end of the month
            // we need to remove 1 month from final date
            Calendar cal = Calendar.getInstance();
            cal.setTime(parsed);
            cal.add(Calendar.MONTH, -1);
            parsed = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String finalData = finalDataFormat.format(parsed);
        return finalData + " ";
    }


    public String convertDate(String datetime) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat finalDataFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        Date parsed = null;
        try {
            parsed = inputFormat.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String finalData = finalDataFormat.format(parsed);
        return finalData + "";
    }

    public String convertRawDate(String datetime) {
        DateFormat inputFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        DateFormat finalDataFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        Date parsed = null;
        try {
            parsed = inputFormat.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String finalData = finalDataFormat.format(parsed);
        return finalData + "";
    }

    public String convertRawTime(String datetime) {
        DateFormat inputFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        DateFormat finalTimeFormat;

        if (android.text.format.DateFormat.is24HourFormat(context)) {
            finalTimeFormat = new SimpleDateFormat("HH:mm");
        } else {
            finalTimeFormat = new SimpleDateFormat("hh:mm a");
        }

        Date parsed = null;
        try {
            parsed = inputFormat.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String finalTime = finalTimeFormat.format(parsed);
        return finalTime + "";
    }

    public String getCurrentTime() {
        Calendar cal = Calendar.getInstance();

        DateFormat finalTimeFormat;

        finalTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);


        String finalTime = finalTimeFormat.format(cal.getTime());
        return finalTime + "";
    }

    public String getCurrentDate() {
        Calendar cal = Calendar.getInstance();

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        String finalTime = dateFormat.format(cal.getTime());
        return finalTime + "";
    }

    public String getTime(Calendar cal) {
        DateFormat finalTimeFormat;

        finalTimeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);


        String finalTime = finalTimeFormat.format(cal.getTime());
        return finalTime + "";
    }

    public String getDate(Calendar cal) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        String finalTime = dateFormat.format(cal.getTime());
        return finalTime + "";
    }
}
