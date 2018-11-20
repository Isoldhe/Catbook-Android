package nl.iamlinda.catbook.model;

import java.util.Random;

public class Chapter6 {
    // Hide & Seek variables
    private boolean play;
    private int hidingSpotSnuggles;
    // Investigation variables
    private int investigation = 0;

    // Hide & Seek methods
    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
        snugglesHide();
    }

    private void snugglesHide() {
        Random r = new Random();
        hidingSpotSnuggles = r.nextInt(4) + 1;  // integer between 1 (inclusive) and 4 (inclusive)
    }

    public int getHidingSpotSnuggles() {
        return hidingSpotSnuggles;
    }

    // Investigation methods
    public int getInvestigation() {
        return investigation;
    }

    public void setInvestigation(int investigation) {
        this.investigation = investigation;
    }
}
