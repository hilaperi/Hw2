package hila.peri.hw2.logic;

public class Player {

    private int playerScore;
    private double playerLongitude;
    private double playerLatitude;
    private String imagePlayer;
    private String namePlayer;
    public Player(){}
    public Player(String imagePlayer, int playerScore, String namePlayer, double playerLongitude, double playerLatitude) {
        this.imagePlayer = imagePlayer;
        this.playerScore = playerScore;
        this.namePlayer = namePlayer;
        this.playerLongitude = playerLongitude;
        this.playerLatitude = playerLatitude;
    }

    public String getImagePlayer() {
        return imagePlayer;
    }

    public void setImagePlayer(String imagePlayer) {
        this.imagePlayer = imagePlayer;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public Player setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
        return this;

    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public Player setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
        return this;
    }

    public double getPlayerLongitude() {
        return playerLongitude;
    }

    public Player setPlayerLongitude(double playerLongitude) {
        this.playerLongitude = playerLongitude;
        return this;

    }

    public double getPlayerLatitude() {
        return playerLatitude;
    }

    public Player setPlayerLatitude(double playerLatitude) {
        this.playerLatitude = playerLatitude;
        return this;

    }

    public void addScore() {
        playerScore++;
    }
}
