package nl.iamlinda.catbook.model;

public class Chapter4 {
    private int userChoice;
    private int snack;
    private boolean snackCaught = false;

    public int getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(int userChoice) {
        this.userChoice = userChoice;
        if (userChoice == 1) {
            catchSnack();
        }
        else if (userChoice == 2) {
            findSnack();
        }
    }

    public int getSnack() {
        return snack;
    }

    public void setSnack(int snack) {
        this.snack = snack;
    }

    public boolean isSnackCaught() {
        return snackCaught;
    }

    public void setSnackCaught(boolean snackCaught) {
        this.snackCaught = snackCaught;
    }

    private void findSnack() {
        double search = Math.floor(Math.random() * 10 + 1);
        if (search == 1) {
            snack = 1;  // mouse
        }
        else if (search <= 3) {
            snack = 2;  // spider
        }
        else if (search <= 6) {
            snack = 3;  // butterfly
        }
        else if (search >= 7) {
            snack = 4;  // bird
        }
    }

    private void catchSnack() {
        double catchAttempt = Math.floor(Math.random() * 10 + 1);
        if (snack == 1) {
            if (catchAttempt <= 6) {
                snackCaught = false;
            }
            else if (catchAttempt >= 7) {
                snackCaught = true;
            }
        }
        else if (snack == 2) {
            if (catchAttempt <= 3) {
                snackCaught = false;
            }
            else if (catchAttempt >= 4) {
                snackCaught = true;
            }
        }
        else if (snack == 3) {
            if (catchAttempt <= 4) {
                snackCaught = false;
            }
            else if (catchAttempt >= 5) {
                snackCaught = true;
            }
        }
        else if (snack == 4) {
            if (catchAttempt <= 7) {
                snackCaught = false;
            }
            else if (catchAttempt >= 8) {
                snackCaught = true;
            }
        }
    }
}
