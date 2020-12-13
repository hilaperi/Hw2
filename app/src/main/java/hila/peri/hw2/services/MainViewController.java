package hila.peri.hw2.services;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import hila.peri.hw2.R;
import hila.peri.hw2.activities.MainActivity;

public class MainViewController {

    private final MainActivity activity;
    private TextView main_LBL_scoreLeft, main_LBL_scoreRight;
    private ImageView main_IMG_backPlayerLeft, main_IMG_backPlayerRight;
    private ImageView main_IMG_playerLeft;
    private Sound tickingSound;
    private ProgressBar main_PGR_progressBar;
    private ImageButton main_BTN_play;

    public MainViewController(MainActivity activity) {
        this.activity = activity;

        findViews();
        initViews();
    }

    private void findViews() {
        main_LBL_scoreLeft = activity.findViewById(R.id.main_LBL_scoreLeft);
        main_LBL_scoreRight = activity.findViewById(R.id.main_LBL_scoreRight);
        main_IMG_backPlayerLeft = activity.findViewById(R.id.main_IMG_backPlayerLeft);
        main_IMG_backPlayerRight = activity.findViewById(R.id.main_IMG_backPlayerRight);
        main_IMG_playerLeft = activity.findViewById(R.id.main_IMG_playerLeft);
        tickingSound = new Sound();
        main_PGR_progressBar = activity.findViewById(R.id.main_PGR_progressBar);
        main_BTN_play = activity.findViewById(R.id.main_BTN_play);

        ImageView main_IMG_background = activity.findViewById(R.id.main_IMG_background);
        MyScreenUtils.updateBackground(MyScreenUtils.Constants.BACKGROUND_NAME, activity, main_IMG_background);
    }

    private void initViews() {
        main_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_BTN_play.setEnabled(false);
                activity.startCounting();
            }
        });
    }


    public void setPlayerImage(Drawable playerImage) {
        main_IMG_playerLeft.setImageDrawable(playerImage);
    }

    public void setPlayerCardImage(Drawable cardImage) {
        main_IMG_backPlayerLeft.setImageDrawable(cardImage);
    }

    public void setComputerCardImage(Drawable cardImage) {
        main_IMG_backPlayerRight.setImageDrawable(cardImage);
    }

    public void setPlayerScore(String score) {
        main_LBL_scoreLeft.setText(score);
    }

    public void setComputerScore(String score) {
        main_LBL_scoreRight.setText(score);
    }

    public void setProgressBar(int progress) {
        main_PGR_progressBar.setProgress(progress);
    }

    public void playSound() {
        if (!tickingSound.isPlaying()) {
            tickingSound.setSound(activity, R.raw.thegame);
            tickingSound.playSound();
        }
    }

    public void stopSound() {
        tickingSound.stopSound();
    }
}
