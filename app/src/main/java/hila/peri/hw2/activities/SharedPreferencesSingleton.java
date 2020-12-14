package hila.peri.hw2.activities;

import android.content.Context;
import android.content.SharedPreferences;

import hila.peri.hw2.views.MyScreenUtils;


public class SharedPreferencesSingleton {
    private static SharedPreferencesSingleton instance;
    private SharedPreferences prefs;

    private SharedPreferencesSingleton(Context context) {
        prefs = context.getSharedPreferences(MyScreenUtils.Const.MY_SP, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesSingleton(context.getApplicationContext());
        }
    }

    public static SharedPreferencesSingleton getInstance() {
        return instance;
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }


    }

