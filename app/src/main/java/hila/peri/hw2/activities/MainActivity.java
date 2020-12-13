package hila.peri.hw2.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import hila.peri.hw2.R;
import hila.peri.hw2.objects.Cards;
import hila.peri.hw2.objects.Player;
import hila.peri.hw2.services.MainViewController;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import static hila.peri.hw2.services.MyScreenUtils.Constants.BOY_CARD;
import static hila.peri.hw2.services.MyScreenUtils.Constants.COMPUTER_CARD;
import static hila.peri.hw2.services.MyScreenUtils.Constants.OLD_WOMAN;
import static hila.peri.hw2.services.MyScreenUtils.Constants.GIRL_CARD;

public class MainActivity extends ActivityBase {
    public static final String PLAYER_GENDER = "PLAYER_GENDER";
    public static final String PLAYER_NAME = "PLAYER_NAME";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";

    private final int SECOND = 1000;
    private final int NUMBER_OF_CARDS = 14;
    private Player playerA;
    private Player playerB;
    private Cards.Deck warDeck;
    private Timer carousalTimer;
    private MainViewController mainViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewController = new MainViewController(this);

        initViews();

        isDoubleBackPressToClose = true;
    }

    private void initViews() {
        initDeck();
        setPlayers();
    }

    public void initDeck() {
        warDeck = new Cards.Deck();
        for (int i = 1; i <= NUMBER_OF_CARDS; i++) {
            warDeck.addCard("card_" + i, i);
        }
        warDeck.shuffleCards();
    }

    private void setPlayers() {
        Intent intent = getIntent();
        String playerGander = intent.getStringExtra(PLAYER_GENDER);
        String playerName = intent.getStringExtra(PLAYER_NAME);
        double playerLatitude = intent.getDoubleExtra(LATITUDE, 0);
        double playerLongitude = intent.getDoubleExtra(LONGITUDE, 0);

        playerA = new Player()
                .setPlayerName(playerName)
                .setPlayerScore(0)
                .setPlayerLatitude(playerLatitude)
                .setPlayerLongitude(playerLongitude);

        playerB = new Player(COMPUTER_CARD,
                0, OLD_WOMAN,
                -0.142368,
                0.034444);

        if (playerGander.matches(GIRL_CARD)) {
            playerA.setPlayerImage(GIRL_CARD);
        } else {
            playerA.setPlayerImage(BOY_CARD);
        }

        int playerImageId = this.getResources().getIdentifier(playerA.getPlayerImage(), "drawable", this.getPackageName());
        Drawable playerImage = getDrawable(playerImageId);
        mainViewController.setPlayerImage(playerImage);
        mainViewController.setPlayerCardImage(playerImage);
    }

    private void playTurn() {
        Cards playerCardA = warDeck.getCard();
        Cards playerCardB = warDeck.getCard();
        if (playerCardA != null && playerCardB != null) {
           // setNewCardsImage(playerCardA.getImageName(), playerCardB.getImageName());

            setScore(playerCardA, playerCardB);

            setProgress();

            if (warDeck.isEmpty()) {
                displayWinner();
            }
        }


    }

    private void setNewCardsImage(String imageNameA, String imageNameB) {
//        int playerDrawableA = this.getResources().getIdentifier(imageNameA, "drawable", this.getPackageName());
//        int playerDrawableB = this.getResources().getIdentifier(imageNameB, "drawable", this.getPackageName());
//
//        mainViewController.setPlayerCardImage(getDrawable(playerDrawableA));
//        mainViewController.setComputerCardImage(getDrawable(playerDrawableB));
    }

    private void setScore(Cards playerCardA, Cards playerCardB) {
        if (playerCardA.isStronger(playerCardB)) {
            playerA.addScore();
            mainViewController.setPlayerScore(playerA.getPlayerScore() + "");
        } else {
            playerB.addScore();
            mainViewController.setComputerScore(playerB.getPlayerScore() + "");
        }
    }

    private void setProgress() {
        double sizeOfBar = 100 / (NUMBER_OF_CARDS / 2.0);
        double totalTurns = playerA.getPlayerScore() + playerB.getPlayerScore();
        int gameProgress = (int) (totalTurns * sizeOfBar);
        mainViewController.setProgressBar(gameProgress);
    }

    private void displayWinner() {
        Gson gson = new Gson();
        Intent intent = new Intent(this, WinnerPage.class);

        String playerJsonA = gson.toJson(playerA);
        String playerJsonB = gson.toJson(playerB);
        intent.putExtra(WinnerPage.PLAYER_A, playerJsonA);
        intent.putExtra(WinnerPage.PLAYER_B, playerJsonB);

        startActivity(intent);
        finish();
    }

    public void startCounting() {
        carousalTimer = new Timer();
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playTurn();
                        mainViewController.playSound();
                    }
                });
            }
        }, 0, SECOND);
    }

    private void stopCounting() {
        carousalTimer.cancel();
    }

    @Override
    protected void onStart() {
        if (carousalTimer != null) {
            startCounting();
            mainViewController.playSound();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (carousalTimer != null) {
            stopCounting();
            mainViewController.stopSound();
        }
        super.onStop();
    }

}