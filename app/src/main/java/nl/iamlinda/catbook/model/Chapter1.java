package nl.iamlinda.catbook.model;

public class Chapter1 {
    private int userChoice;
    private int totalPoints = 0;

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setUserChoice(int userChoice) {
        this.userChoice = userChoice;
        add(userChoice);
    }

    private void add(int points) {
        totalPoints += points;
    }
}
