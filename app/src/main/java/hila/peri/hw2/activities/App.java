package hila.peri.hw2.activities;

import android.app.Application;
import hila.peri.hw2.utils.MyScreenUtils;

import hila.peri.hw2.services.MySP;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySP.init(this);
    }
}
