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


    public String getDateKey(){

        c.setTime(new Date());

        String dayOfMonth = getDayFormat(c.get(Calendar.DAY_OF_MONTH));
        String numberOfMonth = getMonthFormat(c.get(Calendar.MONTH));
        int numberOfYear = c.get(Calendar.YEAR);
        //Todo fix correct date format
        String dateKey = numberOfYear+""+numberOfMonth+""+dayOfMonth;
        return dateKey;

    }

    // Todo Refactor get24HourFormatCode

    public String get24HourFormat(){

        String hour = getTimeFormat(c.get(Calendar.HOUR));
        String minute = getTimeFormat(c.get(Calendar.MINUTE));
        String time = hour + ":" +minute;
        return time;
    }

    public String getTimeCompletetKey(){

        String ano = "" + c.get(Calendar.YEAR);
        String mes = "" + c.get(Calendar.MONTH);
        String dia = "" + c.get(Calendar.DAY_OF_MONTH);
        String hour = getTimeFormat(c.get(Calendar.HOUR));
        String minute = getTimeFormat(c.get(Calendar.MINUTE));
        String seconds = "" + c.get(Calendar.SECOND);
        return String.format("%s%s%s%s%s%s", ano, mes, dia, hour, minute, seconds);
    }


    public String getDatePost(){
        c.setTime(new Date());

        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int numberOfMonth = c.get(Calendar.MONTH);
        String month ="";
        int numberOfYear = c.get(Calendar.YEAR);
        String hour = getTimeFormat(c.get(Calendar.HOUR_OF_DAY));
        String minute = getTimeFormat(c.get(Calendar.MINUTE));

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

    public String getDate (){

        c.setTime(new Date());

        //int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        String dayOfMonth = getDayFormat(c.get(Calendar.DAY_OF_MONTH));
        String numberOfMonth = getMonthFormat(c.get(Calendar.MONTH));
        int numberOfYear = c.get(Calendar.YEAR);

        String date = "" + numberOfYear + numberOfMonth + dayOfMonth;

        return date;

    }


    public String getMonthFormat(int numberOfMonth) {

        numberOfMonth ++;
        String month = ((Integer) numberOfMonth).toString();
        month = "0" + month;
        month = month.substring(month.length() - 2);

        return month;
    }

    public String getDayFormat (int numberOfDay){

        String day = ((Integer) numberOfDay).toString();
        day = "0" + day;
        day = day.substring(day.length()-2);
        return day;

    }

    public String getTimeFormat (int numberOfHour){
        String n = ((Integer) numberOfHour).toString();
        n = "0" + n;
        n = n.substring(n.length()-2);
        return n;
    }
}

