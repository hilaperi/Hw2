package hila.peri.hw2.views;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
@SuppressWarnings("unchecked")

public class MyScreenUtils {

    public static void hideSystemUI(AppCompatActivity activity) {

        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        // Dim the Status and Navigation Bars
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    public static void updateBackground(String imageName, Activity activity, ImageView place) {
        int backgroundId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
        Glide.with(activity).load(backgroundId).into(place);
    }

    public static class Const {

        public static final String MY_SP = "SharedPreferences";
        public static final String TOP_TEN = "TopTen";
        public static final String OLD_WOMAN = "Savtosh";
       public static final String BACKGROUND_NAME = "background";
        public static final String GIRL_CARD = "player_girl";
        public static final String BOY_CARD = "player_boy";
        public static final String COMPUTER_CARD = "player_computer";
    }
}