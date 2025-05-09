package model;

import java.util.ArrayList;

import util.ScoreListener;

public class ScoreBoard {
    private int player1Score, player2Score = 0;
    private ArrayList<ScoreListener> listeners = new ArrayList<>();

    public void incrementPlayer1Score(int points) {
        player1Score += points;
        notifyListeners();
    }

    public void incrementPlayer2Score(int points) {
        player2Score += points;
        notifyListeners();
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void resetScores() {
        player1Score = 0;
        player2Score = 0;
    }

    public void addScoreListener(ScoreListener l) {
        listeners.add(l);
    }

    public void removeScoreListener(ScoreListener l) {
        listeners.remove(l);
    }

    public void notifyListeners() {
        for (ScoreListener l : listeners) {
            l.scoreChanged(player1Score, player2Score);
        }
    }

}
