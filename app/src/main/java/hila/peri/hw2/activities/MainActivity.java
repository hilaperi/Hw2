package hila.peri.hw2.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import hila.peri.hw2.R;
import hila.peri.hw2.logic.Cards;
import hila.peri.hw2.logic.Player;
import hila.peri.hw2.views.MainViewController;
import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;
import static hila.peri.hw2.views.MyScreenUtils.Const.BOY_CARD;
import static hila.peri.hw2.views.MyScreenUtils.Const.COMPUTER_CARD;
import static hila.peri.hw2.views.MyScreenUtils.Const.OLD_WOMAN;
import static hila.peri.hw2.views.MyScreenUtils.Const.GIRL_CARD;

public class MainActivity extends ActivityBase {

    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String PLAYER_GENDER = "PLAYER_GENDER";
    public static final String PLAYER_NAME = "PLAYER_NAME";

    private final int SECOND = 1000;
    private final int NUMBER_OF_CARDS = 14;
    private Player playerLeft;
    private Player playerRight;
    private Cards.Deck warDeck;
    private Timer cTimer;
    private MainViewController mainViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewController = new MainViewController(this);
        initViews();
        isDoubleBackPressToClose = true;
//        Fragment_Map fragment=new Fragment_Map();
//        getSupportFragmentManager().beginTransaction().replace(R.id.topTen_MAP_view,fragment).commit();
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

      
        playerRight = new Player(COMPUTER_CARD,
                0, OLD_WOMAN,
                -0.142368,
                0.034444);

        playerLeft = new Player()
                .setNamePlayer(playerName)
                .setPlayerScore(0)
                .setPlayerLatitude(playerLatitude)
                .setPlayerLongitude(playerLongitude);


        if (playerGander.matches(GIRL_CARD)) {
            playerLeft.setImagePlayer(GIRL_CARD);
        } else {
            playerLeft.setImagePlayer(BOY_CARD);
        }

        int playerImageId = this.getResources().getIdentifier(playerLeft.getImagePlayer(), "drawable", this.getPackageName());
        Drawable playerImage = getDrawable(playerImageId);
        mainViewController.setPlayerImage(playerImage);
        mainViewController.setPlayerCardImage(playerImage);
    }

    private void playTurn() {
        Cards playerCardLeft = warDeck.getCard();
        Cards playerCardRight = warDeck.getCard();
        if (playerCardLeft != null && playerCardRight != null) {
           setImageCards(playerCardLeft.getImageName(), playerCardRight.getImageName());
            setScore(playerCardLeft, playerCardRight);

            setProgress();

            if (warDeck.isEmpty()) {
                displayWinner();
            }
        }


    }

    private void setImageCards(String imageNameA, String imageNameB) {
        int playerDraLeft = this.getResources().getIdentifier(imageNameA, "drawable", this.getPackageName());
        int playerDraRight = this.getResources().getIdentifier(imageNameB, "drawable", this.getPackageName());

        mainViewController.setPlayerCardImage(getDrawable(playerDraLeft));
        mainViewController.setComputerCardImage(getDrawable(playerDraRight));
    }

    private void setScore(Cards playerCardA, Cards playerCardB) {
        if (playerCardA.isStronger(playerCardB)) {
            playerLeft.addScore();
            mainViewController.setPlayerScore(playerLeft.getPlayerScore() + "");
        } else {
            playerRight.addScore();
            mainViewController.setComputerScore(playerRight.getPlayerScore() + "");
        }
    }

    private void setProgress() {
        //double numOfTurn = 100 / (NUMBER_OF_CARDS / 2.0);
        double numOfTurn = NUMBER_OF_CARDS;

        double totalTurns = playerLeft.getPlayerScore() + playerRight.getPlayerScore();
        int gameProgress = (int) (totalTurns * numOfTurn);
        mainViewController.setProgressBar(gameProgress);
    }

    private void displayWinner() {
        Gson gson = new Gson();
        Intent intent = new Intent(this, WinPage.class);

        String playerJsonA = gson.toJson(playerLeft);
        String playerJsonB = gson.toJson(playerRight);
        intent.putExtra(WinPage.PLAYER_A, playerJsonA);
        intent.putExtra(WinPage.PLAYER_B, playerJsonB);

        startActivity(intent);
        finish();
    }

    public void startCounting() {
        cTimer = new Timer();
        cTimer.scheduleAtFixedRate(new TimerTask() {
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
        cTimer.cancel();
    }

    @Override
    protected void onStart() {
        if (cTimer != null) {
            startCounting();
            mainViewController.playSound();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (cTimer != null) {
            stopCounting();
            mainViewController.stopSound();
        }
        super.onStop();
    }



}