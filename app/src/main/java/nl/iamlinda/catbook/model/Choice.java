package nl.iamlinda.catbook.model;

public class Choice {
    // Choice has a text on the button with a id (int textId)
    private int buttonTextId;  // text on button
    private int choiceId;      // this can be any int (e.g. 25/ 50/ etc points or choice nr 1, 2, etc.)

    // Choices don't trigger next pages, a separate nextChapter button does this
    // Choices send data to other methods that calculate stuff and based on that the TextView (story text) changes

    public Choice(int buttonTextId, int choiceId) {
        this.buttonTextId = buttonTextId;
        this.choiceId = choiceId;
    }

    public int getButtonTextId() {
        return buttonTextId;
    }

    public void setButtonTextId(int buttonTextId) {
        this.buttonTextId = buttonTextId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }
}
