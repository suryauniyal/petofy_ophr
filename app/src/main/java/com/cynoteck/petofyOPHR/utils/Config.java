package com.cynoteck.petofyOPHR.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Config {

    public  static  int count =1;
    public static String token="";
    public static String user_id="";
    public static String user_Veterian_name="";
    public static String user_Veterian_phone="";
    public static String user_Veterian_emial="";
    public static String user_Veterian_address="";
    public static String user_Veterian_study="";
    public static String user_Veterian_id="";
    public static String user_Veterian_online="";
    public static String user_Veterian_url="";
    public static String user_Veterian_details="";
    public static String two_fact_auth_status="";
    public static String pet_type_for_immue="";
    public static String user_type="";
    public static String user_verterian_reg_no="";

    public static String type="";
    public static String backCall="";
    public static String IpAddress="";


    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
   public static String currentDate()
   {
       String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
       return date;
   }

   public static String changeDateFormat(String currentDate){
       SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
       DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
       Date date = null;

       try {
           date = formatter.parse(currentDate);
       } catch (ParseException e) {
       }

       String newDate = df.format(date);
        return newDate;
   }

    public static String changeTimeFormat(String currentTime){
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm a");
            Date date = inFormat.parse(currentTime);
            SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm a");
            String goal = outFormat.format(date);
            return goal;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String changeTimePicker(String currentTime){
        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("HH:mm");
            Date date = inFormat.parse(currentTime);
            SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm a");
            String goal = outFormat.format(date);
            return goal;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}