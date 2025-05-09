package model;
import java.util.ArrayList;

import util.Observer;
import util.Subject;

public class GameTimer implements Subject {
    private static final int DEFAULT_TIME_LIMIT_IN_SECONDS = 60;
    private int timeRemaining;
    private boolean isRunning;
    private long lastUpdateTime;
    private ArrayList<Observer> observers = new ArrayList<>();

    public GameTimer() {
        this(DEFAULT_TIME_LIMIT_IN_SECONDS);
    }

    public GameTimer(int initialTimeInSeconds) {
        timeRemaining = initialTimeInSeconds;
        isRunning = false;
        lastUpdateTime = 0;
    }

    public void update() {
        if (!isRunning)
            return;

        long currentTime = System.currentTimeMillis();

        if (lastUpdateTime == 0) {
            lastUpdateTime = currentTime;
            return;
        }

        long elapsed = currentTime - lastUpdateTime;

        if (elapsed >= 1000) {
            timeRemaining--;
            lastUpdateTime = currentTime;
            notifyObservers();

            if (timeRemaining <= 0) {
                isRunning = false;
            }
        }
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            lastUpdateTime = System.currentTimeMillis();
            notifyObservers();
        }
    }

    public void stop() {
        isRunning = false;
        notifyObservers();
    }

    public void reset() {
        stop();
        timeRemaining = DEFAULT_TIME_LIMIT_IN_SECONDS;
        notifyObservers();
    }

    public void reset(int newTimeInSeconds) {
        stop();
        timeRemaining = newTimeInSeconds;
        notifyObservers();
    }

    public int getTimeRemainingInSeconds() {
        return timeRemaining;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getFormattedTime() {
        final int seconds = 60;
        int minutes = timeRemaining / seconds;
        int actualSeconds = timeRemaining % seconds;
        return String.format("%02d:%02d", minutes, actualSeconds);
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
