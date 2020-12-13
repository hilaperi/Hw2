package hila.peri.hw2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import hila.peri.hw2.services.MyScreenUtils;
import hila.peri.hw2.services.MySP;
import hila.peri.hw2.R;
import hila.peri.hw2.objects.Player;
import hila.peri.hw2.objects.Record;
import hila.peri.hw2.objects.TopTenRecords;
import hila.peri.hw2.services.Sound;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import static hila.peri.hw2.services.MyScreenUtils.Constants.TOP_TEN;

public class WinnerPage extends ActivityBase {
    public static final String PLAYER_A = "PLAYER_A";
    public static final String PLAYER_B = "PLAYER_B";
    private TextView win_LBL_wonName;
    private ImageView win_IMG_won;
    private Button win_BTN_Restart;
    private Sound winSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_page);

        findViews();
        initViews();

        displayWinner();
        winSound.playSound();
    }

    private void findViews() {
        win_LBL_wonName = findViewById(R.id.win_LBL_wonName);
        win_IMG_won = findViewById(R.id.win_IMG_won);
        win_BTN_Restart = findViewById(R.id.win_BTN_Restart);

        winSound = new Sound();
        winSound.setSound(this, R.raw.party);

        ImageView win_IMG_background = findViewById(R.id.win_IMG_background);
        MyScreenUtils.updateBackground(MyScreenUtils.Constants.BACKGROUND_NAME, this, win_IMG_background);
    }

    private void initViews() {
        win_BTN_Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
    }

    private void startNewGame() {
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
        finish();
    }

    private void displayWinner() {
        int imageId;
        String playerImage;
        String playerName;

        Intent intent = getIntent();
        Gson gson = new Gson();

        String playerJsonA = intent.getStringExtra(PLAYER_A);
        String playerJsonB = intent.getStringExtra(PLAYER_B);
        Player playerA = gson.fromJson(playerJsonA, Player.class);
        Player playerB = gson.fromJson(playerJsonB, Player.class);

        if (playerA.getPlayerScore() > playerB.getPlayerScore()) {
            playerImage = playerA.getPlayerImage();
            playerName = playerA.getPlayerName();
            saveScore(playerA);
        } else {
            playerImage = playerB.getPlayerImage();
            playerName = playerB.getPlayerName();
            saveScore(playerB);
        }

        imageId = this.getResources().getIdentifier(playerImage, "drawable", this.getPackageName());
        win_IMG_won.setImageDrawable(getDrawable(imageId));
        win_LBL_wonName.setText(playerName);
    }

    private void saveScore(Player player) {
        TopTenRecords topTenRecords;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy\nHH:mm:ss");
        String date = format.format(System.currentTimeMillis());

        Gson gson = new Gson();

        String jsonFromMemory = MySP.getInstance().getString(TOP_TEN, "");
        if (jsonFromMemory.equals("")) {
            topTenRecords = new TopTenRecords();
        } else {
            topTenRecords = gson.fromJson(jsonFromMemory, TopTenRecords.class);
        }

        Record record = new Record(player.getPlayerName(),
                player.getPlayerScore(),
                date,
                player.getPlayerLatitude(),
                player.getPlayerLongitude());

        boolean isAdd = topTenRecords.addRecord(record);

        if (isAdd) {
            String json = gson.toJson(topTenRecords);
            MySP.getInstance().putString(TOP_TEN, json);
        }
    }
}