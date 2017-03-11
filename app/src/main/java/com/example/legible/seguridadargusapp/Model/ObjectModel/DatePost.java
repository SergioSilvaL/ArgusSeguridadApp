package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/10/17.
 */


import android.util.Log;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sergio on 1/24/2017.
 */

public class DatePost{
    Calendar c = Calendar.getInstance();





    public String getDatePost(){
        c.setTime(new Date());

        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int numberOfMonth = c.get(Calendar.MONTH);
        Log.v("mes",numberOfMonth+"");
        String month ="";
        int numberOfYear = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (numberOfMonth){
            case 0 :month = "enero";
                break;
            case 1 :month = "febrero";
                break;
            case 2 :month = "marzo";
                break;
            case 3 :month = "abril";
                break;
            case 4 :month = "mayo";
                break;
            case 5 :month = "junio";
                break;
            case 6 :month = "julio";
                break;
            case 7 :month = "agosto";
                break;
            case 8 :month = "septiembre";
                break;
            case 9 :month = "octubre";
                break;
            case 10 :month = "noviembre";
                break;
            case 11 :month = "diciembre";
                break;

            default:
                break;
        }

        String Date = dayOfMonth + " de "+ month+" del "+numberOfYear+" a las "+hour+":"+minute;

        return Date;

    }
}

