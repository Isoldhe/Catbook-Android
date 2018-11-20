package nl.iamlinda.catbook.model;

import java.util.ArrayList;
import java.util.Objects;

public class Chapter3 {
    private String guess;
    private int guessing = 0;
    private int guessedAll = 0;

    private String[] stuffPuddles = {"rock", "leaf", "twig", "mouse", "ring", "newspaper", "box", "ball", "bug", "toy"};
    private ArrayList<String> guessedItems = new ArrayList<>();

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
        findStuff(guess);
    }

    public int getGuessing() {
        return guessing;
    }

    public void setGuessing(int guessing) {
        this.guessing = guessing;
    }

    private void findStuff(String userGuess) {
        guessing = 0;
        for (String stuff : stuffPuddles) {
            if (Objects.equals(userGuess, stuff)) {
                guessing = 1;
            }
        }
        for (String guessedItem : guessedItems) {
            if (Objects.equals(userGuess, guessedItem)) {
                guessing = 2;
            }
        }
        guessedItems.add(userGuess);
        setGuessing(guessing);
    }

    public ArrayList<String> getGuessedItems() {
        return guessedItems;
    }

    public void setGuessedItems(ArrayList<String> guessedItems) {
        this.guessedItems = guessedItems;
    }

    public int getGuessedAll() {
        return guessedAll;
    }

    public void setGuessedAll(int guessedAll) {
        this.guessedAll = guessedAll;
    }
}
