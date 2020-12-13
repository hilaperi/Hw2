package hila.peri.hw2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import hila.peri.hw2.utils.MyScreenUtils;
import hila.peri.hw2.services.MySP;
import hila.peri.hw2.R;
import hila.peri.hw2.objects.Card;
import hila.peri.hw2.objects.Deck;
import hila.peri.hw2.objects.Player;
import hila.peri.hw2.services.MainViewController;
import hila.peri.hw2.fragments.FragmentList;
import hila.peri.hw2.fragments.FragmentMap;
import hila.peri.hw2.interfaces.RecordCallBack;
import hila.peri.hw2.objects.Record;
import hila.peri.hw2.objects.TopTenRecords;
import hila.peri.hw2.services.RecordItemAdapter;
import hila.peri.hw2.utils.Constants;
import hila.peri.hw2.utils.MyScreenUtils;
import hila.peri.hw2.services.MySP;
import hila.peri.hw2.services.Sound;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import static hila.peri.hw2.utils.Constants.TOP_TEN;

public class WinnerPage extends ActivityBase {
    public static final String PLAYER_A = "PLAYER_A";
    public static final String PLAYER_B = "PLAYER_B";
    private TextView winner_LBL_name;
    private ImageView winner_IMG_winner;
    private Button winner_BTN_new_game;
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
        winner_LBL_name = findViewById(R.id.winner_LBL_name);
        winner_IMG_winner = findViewById(R.id.winner_IMG_winner);
        winner_BTN_new_game = findViewById(R.id.winner_BTN_new_game);

        winSound = new Sound();
        winSound.setSound(this, R.raw.win_sound);

        ImageView winner_IMG_background = findViewById(R.id.winner_IMG_background);
        MyScreenUtils.updateBackground(Constants.BACKGROUND_NAME, this, winner_IMG_background);
    }

    private void initViews() {
        winner_BTN_new_game.setOnClickListener(new View.OnClickListener() {
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
        winner_IMG_winner.setImageDrawable(getDrawable(imageId));
        winner_LBL_name.setText(playerName);
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