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

import com.zduo.sos.R;
import com.zduo.sos.db.DatabaseHandler;

public class GlucoseRanges {

    private DatabaseHandler dB;
    private Context mContext;
    private String preferredRange;
    private int customMin;
    private int customMax;

    public GlucoseRanges(Context context) {
        this.mContext = context;
        dB = new DatabaseHandler(mContext);
        this.preferredRange = dB.getUser(1).getPreferred_range();
        if (preferredRange.equals("Custom range")) {
            this.customMin = dB.getUser(1).getCustom_range_min();
            this.customMax = dB.getUser(1).getCustom_range_max();
        }
    }

    public String colorFromReading(int reading) {
        // Check for Hypo/Hyperglycemia
        if (reading < 70) {
            return "purple";
        } else if (reading > 200) {
            return "red";
        } else if (reading > 70 | reading < 200) {
            // if not check with custom ranges
            switch (preferredRange) {
                case "ADA":
                    if (reading >= 70 & reading <= 180) {
                        return "green";
                    } else if (reading < 70) {
                        return "blue";
                    } else if (reading > 180) {
                        return "orange";
                    }
                case "AACE":
                    if (reading >= 110 & reading <= 140) {
                        return "green";
                    } else if (reading < 110) {
                        return "blue";
                    } else if (reading > 140) {
                        return "orange";
                    }
                case "UK NICE":
                    if (reading >= 72 & reading <= 153) {
                        return "green";
                    } else if (reading < 72) {
                        return "blue";
                    } else if (reading > 153) {
                        return "orange";
                    }
                default:
                    if (reading >= customMin & reading <= customMax) {
                        return "green";
                    } else if (reading < customMin) {
                        return "blue";
                    } else if (reading > customMax) {
                        return "orange";
                    }
            }
        }
        return "red";
    }

    public int stringToColor(String color) {
        switch (color) {
            case "green":
                return mContext.getResources().getColor(R.color.glucosio_reading_ok);
            case "red":
                return mContext.getResources().getColor(R.color.glucosio_reading_hyper);
            case "blue":
                return mContext.getResources().getColor(R.color.glucosio_reading_low);
            case "orange":
                return mContext.getResources().getColor(R.color.glucosio_reading_high);
            default:
                return mContext.getResources().getColor(R.color.glucosio_reading_hypo);
        }
    }

    ;
}
