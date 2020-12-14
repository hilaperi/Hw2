package hila.peri.hw2.activities;
import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesSingleton.init(this);
    }

}