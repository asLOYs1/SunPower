package com.tutoriasdidier.sunpower;

import android.content.Context;
import android.content.SharedPreferences;
import java.security.Key;

public class UserManager {
    // creo arcivo pref
    private  static final String PREF_NAME ="User_prefs";
    // claves para los datos
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE ="phone";
    // variables a instanciar
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //constructor
    public UserManager(Context context){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,context.MODE_PRIVATE);
        //llamar un editor
        editor= sharedPreferences.edit();

    }
    //user register function
    public void RegisterUser(String name, String email, String password,String phone){
        //key para guardar datos
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_PHONE,phone);
        editor.apply();
    }
    //method to validate information
    public boolean LoginUser(String email, String password) {
        String registeredEmail = sharedPreferences.getString(KEY_EMAIL, null);
        String registeredPassword = sharedPreferences.getString(KEY_PASSWORD, null);
        return email.equals(registeredEmail) && password.equals(registeredPassword);
    }
}
