package nl.iamlinda.catbook.model;

public class Page {
    private int imageId;
    private int textId;
    private Choice choice1;
    private Choice choice2;
    private Choice choice3;
    private Choice choice4;
    private boolean isFinalPage = false;

    // Last pages
    public Page(int imageId, int textId) {
        this.imageId = imageId;
        this.textId = textId;
    }

    // Page with one choice
    public Page(int imageId, int textId, Choice choice1) {
        this.imageId = imageId;
        this.textId = textId;
        this.choice1 = choice1;
    }

    // Page with two choices
    public Page(int imageId, int textId, Choice choice1, Choice choice2) {
        this.imageId = imageId;
        this.textId = textId;
        this.choice1 = choice1;
        this.choice2 = choice2;
    }

    // Page with three choices
    public Page(int imageId, int textId, Choice choice1, Choice choice2, Choice choice3) {
        this.imageId = imageId;
        this.textId = textId;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
    }

    // Page with four choices
    public Page(int imageId, int textId, Choice choice1, Choice choice2, Choice choice3, Choice choice4) {
        this.imageId = imageId;
        this.textId = textId;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
    }

    public boolean isFinalPage() {
        return isFinalPage;
    }

    public void setFinalPage(boolean finalPage) {
        isFinalPage = finalPage;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public Choice getChoice1() {
        return choice1;
    }

    public void setChoice1(Choice choice1) {
        this.choice1 = choice1;
    }

    public Choice getChoice2() {
        return choice2;
    }

    public void setChoice2(Choice choice2) {
        this.choice2 = choice2;
    }

    public Choice getChoice3() {
        return choice3;
    }

    public void setChoice3(Choice choice3) {
        this.choice3 = choice3;
    }

    public Choice getChoice4() {
        return choice4;
    }

    public void setChoice4(Choice choice4) {
        this.choice4 = choice4;
    }
}
