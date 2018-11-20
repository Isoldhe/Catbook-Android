package nl.iamlinda.catbook.model;

import java.util.Random;

public class Chapter7 {
    private int userDamage;
    private int snugglesDamage;
    private int luckyDamage;
    private int userHealth = 100;
    private int luckyHealth = 100;
    private boolean snugglesIsFriend = false;

    private Random r = new Random();

    public boolean isSnugglesIsFriend() {
        return snugglesIsFriend;
    }

    public void setSnugglesIsFriend(boolean snugglesIsFriend) {
        this.snugglesIsFriend = snugglesIsFriend;
        userAttack();
    }

    private void userAttack() {
        int damage = r.nextInt(20) + 1;  // integer between 1 (inclusive) and 20 (inclusive)
        userDamage = damage;
        if (snugglesIsFriend) {
            snugglesAttack();
        }
        luckyAttack();
    }

    private void luckyAttack() {
        int damage = r.nextInt(20) + 1;  // integer between 1 (inclusive) and 20 (inclusive)
        luckyDamage = damage;
        health();
    }

    private void snugglesAttack() {
        int damage = r.nextInt(20) + 1;  // integer between 1 (inclusive) and 20 (inclusive)
        snugglesDamage = damage;
    }

    public void health() {
        userHealth -= luckyDamage;
        if (snugglesIsFriend) {
            luckyHealth = luckyHealth - userDamage - snugglesDamage;
        }
        else {
            luckyHealth -= userDamage;
        }
    }

    public int getUserDamage() {
        return userDamage;
    }

    public void setUserDamage(int userDamage) {
        this.userDamage = userDamage;
    }

    public int getSnugglesDamage() {
        return snugglesDamage;
    }

    public void setSnugglesDamage(int snugglesDamage) {
        this.snugglesDamage = snugglesDamage;
    }

    public int getLuckyDamage() {
        return luckyDamage;
    }

    public void setLuckyDamage(int luckyDamage) {
        this.luckyDamage = luckyDamage;
    }

    public int getUserHealth() {
        return userHealth;
    }

    public void setUserHealth(int userHealth) {
        this.userHealth = userHealth;
    }

    public int getLuckyHealth() {
        return luckyHealth;
    }

    public void setLuckyHealth(int luckyHealth) {
        this.luckyHealth = luckyHealth;
    }
}
