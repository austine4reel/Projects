package com.example.austi.thingsee;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iakai9900 on 11/03/2018.
 */
// to create a XML file that stores the users data
public class User {
    private static final String login = "login_details";
    private    static List<Double> lat = new ArrayList<Double>();
    private    static List<Double> lng = new ArrayList<Double>();

    Context context; // global data
    SharedPreferences sharedPreferences;



    public User(Context context){
        this.context = context;
        sharedPreferences=context.getSharedPreferences(login, Context.MODE_PRIVATE);
    }

    private String nam;

    public String getNam() {

        nam= sharedPreferences.getString("nam", "");
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
        sharedPreferences.edit().putString("nam", nam).commit();
    }

    public String getPas() {

        pas= sharedPreferences.getString("pas", "");
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
        sharedPreferences.edit().putString("pas", pas).commit();
    }

    private String pas;

    public void removeUser(){

        sharedPreferences.edit().clear().commit();
    }

    public static double getlat(int i){
        return lat.get(i);

    }
    public static double getlng(int i){
        return lng.get(i);

    }
    public static void addlat(double i){
       lat.add(i);

    }
    public static void addlng(double i){
        lng.add(i);

    }

    public static int getsize(){
        return lat.size();
    }


}
