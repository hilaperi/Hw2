package hila.peri.hw2.activities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import hila.peri.hw2.services.MyScreenUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);
    }

    public static class MySP {
        private static MySP instance;
        private SharedPreferences prefs;

        private MySP(Context context) {
            prefs = context.getSharedPreferences(MyScreenUtils.Const.MY_SP, MODE_PRIVATE);
        }

        public static void init(Context context) {
            if (instance == null) {
                instance = new MySP(context.getApplicationContext());
            }
        }

        public static MySP getInstance() {
            return instance;
        }

        public void putString(String key, String value) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.apply();
        }

        public String getString(String key, String def) {
            return prefs.getString(key, def);
        }

        public void removeKey(String key) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.apply();
        }
    }
}
