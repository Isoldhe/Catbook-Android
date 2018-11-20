package nl.iamlinda.catbook.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

import nl.iamlinda.catbook.R;
import nl.iamlinda.catbook.model.Bookmarker;
import nl.iamlinda.catbook.model.Chapter1;
import nl.iamlinda.catbook.model.Chapter3;
import nl.iamlinda.catbook.model.Chapter4;
import nl.iamlinda.catbook.model.Chapter6;
import nl.iamlinda.catbook.model.Chapter7;
import nl.iamlinda.catbook.model.NextChapter;
import nl.iamlinda.catbook.model.Page;
import nl.iamlinda.catbook.model.Story;

public class StoryActivity extends AppCompatActivity {
    private Story story;
    private Bookmarker bookmarker;
    private NextChapter nextChapter;
    private ImageView storyImageView;
    private TextView storyTextView;   // This changes all the time when buttons are clicked
    private Button choice1Button;
    private Button choice2Button;
    private Button choice3Button;
    private Button choice4Button;
    private Button nextChapterButton;
    private EditText guessField;
    private Button enterGuessButton;
    private boolean button1present = false;
    private boolean button2present = false;
    private boolean button3present = false;
    private boolean button4present = false;
    private boolean isFinalPage = false;
    private Stack<Integer> pageStack = new Stack<>();
    private ScrollView scroll;

    // booleans to keep track of the storyline
    private boolean snugglesIsFriend = false;
    private boolean leiaIsHappy = false;
    private boolean eatTheFood = false;

    private String name;

    // Changing the storyTextView and buttons as the story unfolds with separate Chapter classes
    private Chapter1 chapter1 = new Chapter1();
    private Chapter3 chapter3 = new Chapter3();
    private Chapter4 chapter4 = new Chapter4();
    private Chapter6 chapter6 = new Chapter6();
    private Chapter7 chapter7 = new Chapter7();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = findViewById(R.id.storyImageView);
        storyTextView = findViewById(R.id.storyTextView);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);
        choice3Button = findViewById(R.id.choice3Button);
        choice4Button = findViewById(R.id.choice4Button);
        guessField = findViewById(R.id.guessEditText);
        enterGuessButton = findViewById(R.id.enterGuessButton);
        nextChapterButton = findViewById(R.id.nextChapterButton);
        scroll = findViewById(R.id.scroll);

        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.key_name));
        if (name == null || name.isEmpty()) {
            name = "Friend";
        }

        story = new Story();
        bookmarker = new Bookmarker();
        nextChapter = new NextChapter();
        button1present = true;
        button2present = true;
        button3present = true;
        button4present = true;
        loadPage(0);
    }

    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);

        // on page 4 (guessing game) the choice buttons need to reset for the next page (5 || 6)
        // so here they are set back to present false or true and choice3Button is set to GONE, because it left an empty space when set to Inivisible
        if (pageNumber == 5 || pageNumber == 6) {
            choice3Button.setVisibility(View.GONE);
            button1present = true;
            button2present = true;
            button3present = false;
            button4present = false;
        }
        if (pageNumber == 8 || pageNumber == 9 || pageNumber == 10 || pageNumber == 11) {
            choice4Button.setVisibility(View.VISIBLE);
        }

        // make buttons black and clickable again if they are greyed out in previous page
        choice1Button.setTextColor(Color.rgb(0, 0, 0));  // black
        choice2Button.setTextColor(Color.rgb(0, 0, 0));
        choice3Button.setTextColor(Color.rgb(0, 0, 0));
        choice4Button.setTextColor(Color.rgb(0, 0, 0));
        choice1Button.setClickable(true);
        choice2Button.setClickable(true);
        choice3Button.setClickable(true);
        choice4Button.setClickable(true);

        final Page page = story.getPage(pageNumber);

        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);

        String pageText = getString(page.getTextId());
        // Add name if placeholder included. Won't add if not.
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);

        // set scrollview back to top
        scroll.scrollTo(0, 0);
        scroll.smoothScrollTo(0,0);
        scroll.smoothScrollBy(0, 0);
        bookmarker.setMarker(0);
        loadButtons(page);
    }

    private void loadButtons(final Page page) {
        guessField.setVisibility(View.GONE);
        enterGuessButton.setVisibility(View.GONE);
        nextChapterButton.setVisibility(View.GONE);

        // Preventing NPE if buttons are not present in certain page with a boolean check
        if (button1present) {
            choice1Button.setVisibility(View.VISIBLE);
            choice1Button.setText(page.getChoice1().getButtonTextId());
            choice1Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int choice = page.getChoice1().getChoiceId();
                    userAction(choice);
                }
            });
            // For Chapter 4: to catch a snack (pages 5, 6, 7) only button 2 needs to be visible at page load
            int currentPage = pageStack.peek();
            if (currentPage == 5 || currentPage == 6 || currentPage == 7) {
                choice1Button.setVisibility(View.GONE);
            }
        }
        if (button2present) {
            choice2Button.setVisibility(View.VISIBLE);
            choice2Button.setText(page.getChoice2().getButtonTextId());
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int choice = page.getChoice2().getChoiceId();
                    userAction(choice);
                }
            });
        }

        if (button3present) {
            choice3Button.setVisibility(View.VISIBLE);
            choice3Button.setText(page.getChoice3().getButtonTextId());
            choice3Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int choice = page.getChoice3().getChoiceId();
                    userAction(choice);
                }
            });
        }
        if (button4present) {
            choice4Button.setVisibility(View.VISIBLE);
            choice4Button.setText(page.getChoice4().getButtonTextId());
            choice4Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int choice = page.getChoice4().getChoiceId();
                    userAction(choice);
                }
            });
        }

        int currentPage = nextChapter.getNextPage();
        if (currentPage == 4) {
            playGuessingGame();
        }
    }

    private void removeChoiceButtons(int remove) {
        // if all buttons should be gone, except button 1, remove = 0
        // if button 4 should be gone and others invisible, remove = 1 (for page 4)
        if (remove == 0) {
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setVisibility(View.GONE);
            choice3Button.setVisibility(View.GONE);
            choice4Button.setVisibility(View.GONE);
        }
        else if (remove == 1) {
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setVisibility(View.INVISIBLE);
            choice3Button.setVisibility(View.INVISIBLE);
            choice4Button.setVisibility(View.GONE);
        }
    }

    private void loadNextChapterButton() {
        nextChapterButton.setVisibility(View.VISIBLE);
        if (isFinalPage) {
            nextChapterButton.setText(R.string.page16_play_again);
        }
        else {
            nextChapterButton.setText(R.string.next_chapter);
        }
        nextChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage;
                if (isFinalPage) {
                    nextPage = 0;
                    choice1Button.setVisibility(View.VISIBLE);
                    choice2Button.setVisibility(View.VISIBLE);
                    choice3Button.setVisibility(View.VISIBLE);
                    choice4Button.setVisibility(View.VISIBLE);
                    button1present = true;
                    button2present = true;
                    button3present = true;
                    button4present = true;
                    isFinalPage = false;
                    // resetting all values in chapters and StoryActivity
                    resetChapters();
                    snugglesIsFriend = false;
                    leiaIsHappy = false;
                    eatTheFood = false;
                }
                else {
                    nextPage = nextChapter.getNextPage();
                }
                loadPage(nextPage);
            }
        });
    }

    // Resetting all values in separate Chapter classes
    private void resetChapters() {
        chapter1.setTotalPoints(0);
        ArrayList<String> guessedItems = new ArrayList<>(); // empty guessed items list
        chapter3.setGuessedItems(guessedItems);
        chapter3.setGuessing(0);
        chapter3.setGuessedAll(0);
        chapter4.setSnack(0);
        chapter4.setSnackCaught(false);
        chapter6.setInvestigation(0);
        chapter7.setUserHealth(100);
        chapter7.setLuckyHealth(100);
        chapter7.setUserDamage(0);
        chapter7.setLuckyDamage(0);
        chapter7.setSnugglesDamage(0);
    }

    // Methods to set the right amount of buttons present
    private void noButtons() {
        button1present = false;
        button2present = false;
        button3present = false;
        button4present = false;
    }
    private void oneButton() {
        button1present = true;
        button2present = false;
        button3present = false;
        button4present = false;
    }
    private void twoButtons() {
        button1present = true;
        button2present = true;
        button3present = false;
        button4present = false;
    }
    private void threeButtons() {
        button1present = true;
        button2present = true;
        button3present = true;
        button4present = false;
    }
    private void fourButtons() {
        button1present = true;
        button2present = true;
        button3present = true;
        button4present = true;
    }


    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()) {
            super.onBackPressed();
        } else {
            if (isFinalPage) {
                isFinalPage = false;
            }
            resetChapters(); // resetting the data made in previous pages

            // setting the right amount of buttons present
            int pageNumber = pageStack.peek();
            switch (pageNumber) {
                case 0: fourButtons(); break;
                case 1: twoButtons(); break;
                case 2: twoButtons(); break;
                case 3: twoButtons(); break;
                case 4:
                    noButtons();
                    choice1Button.setVisibility(View.GONE);
                    choice2Button.setVisibility(View.GONE);
                    break;
                case 5:
                case 6:
                    choice4Button.setVisibility(View.GONE);
                    twoButtons();
                    break;
                case 8: fourButtons(); break;
                case 9: fourButtons(); break;
                case 10: fourButtons(); break;
                case 11: fourButtons(); break;
                case 12: twoButtons(); break;
                case 13: oneButton(); break;
                case 14: fourButtons(); break;
                case 15: threeButtons(); break;
                case 16: oneButton(); break;
                case 17: oneButton(); break;
            }
            loadPage(pageStack.pop());
        }
    }

    private void playGuessingGame() {
        removeChoiceButtons(1);
        loadNextChapterButton();
        enterGuessButton.setVisibility(View.VISIBLE);
        guessField.setVisibility(View.VISIBLE);
        nextChapter.setNextPage(6);
        enterGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = guessField.getText().toString().toLowerCase();
                chapter3.setGuess(guess);
                int checkGuess = chapter3.getGuessing();

                if (checkGuess == 1) {
                    Resources r = getResources();
                    int setGuessedAll = chapter3.getGuessedAll();
                    chapter3.setGuessedAll(setGuessedAll += 1);
                    int guessedAll = chapter3.getGuessedAll();
                    if (guessedAll == 10) {
                        String text = r.getString(R.string.page4_4, guess);
                        storyTextView.setText(text);
                        guessField.setVisibility(View.GONE);
                        enterGuessButton.setVisibility(View.GONE);
                    }
                    else {
                        String text = r.getString(R.string.page4_1, guess);
                        storyTextView.setText(text);
                    }
                }
                else if (checkGuess == 0) {
                    storyTextView.setText(R.string.page4_2);
                }
                else if (checkGuess == 2) {
                    storyTextView.setText(R.string.page4_3);
                }
                guessField.setText("");  // Empty EditText
                View v = getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    private void userAction(int choice){
        int marker = bookmarker.getMarker();
        // Checking the currentPage by peeking at the top of the pageStack
        int currentPage = pageStack.peek();
        switch(currentPage) {
            case 0:
                chapter1.setUserChoice(choice);
                int nextParagraph = chapter1.getTotalPoints();
                if (nextParagraph == 25) {
                    storyTextView.setText(R.string.page0_25points);
                }
                else if (nextParagraph == 50) {
                    storyTextView.setText(R.string.page0_50points);
                }
                else if (nextParagraph == 75) {
                    storyTextView.setText(R.string.page0_75points);
                }
                else if (nextParagraph == 100) {
                    // removing choice buttons, adding next chapter button with corresponding nextPage
                    storyTextView.setText(R.string.page0_100points);
                    removeChoiceButtons(0);
                    chapter1.setTotalPoints(0); // resetting it for chapter 5 (page 7)
                    nextChapter.setNextPage(1);
                    loadNextChapterButton();
                    button3present = false;
                    button4present = false;
                }
                else if (nextParagraph > 100) {
                    storyTextView.setText(R.string.page0_over_100points);
                    removeChoiceButtons(0);
                    chapter1.setTotalPoints(0); // resetting it for chapter 5 (page 7)
                    nextChapter.setNextPage(2);
                    loadNextChapterButton();
                    button3present = false;
                    button4present = false;
                }
                break;
            case 1:
            case 2: // case 1 or 2
                if (marker == 0 && choice == 1) {
                    String pageText = getString(R.string.page1_1);
                    pageText = String.format(pageText, name);
                    storyTextView.setText(pageText);
                    choice1Button.setText(R.string.page1_choice1_1);
                    choice2Button.setText(R.string.page1_choice2_1);
                    bookmarker.setMarker(1);
                }
                else if (marker == 0 && choice == 2) {
                    storyTextView.setText(R.string.page1_2);
                    choice1Button.setText(R.string.page1_choice1_2);
                    choice2Button.setText(R.string.page1_choice2_2);
                    bookmarker.setMarker(2);
                }
                else if (marker == 1 && choice == 1) {
                    storyTextView.setText(R.string.page1_3);
                    removeChoiceButtons(0);
                    nextChapter.setNextPage(3);
                    loadNextChapterButton();
                    snugglesIsFriend = true;
                }
                else if (marker == 1 && choice == 2) {
                    storyTextView.setText(R.string.page1_4);
                    removeChoiceButtons(0);
                    nextChapter.setNextPage(4);
                    button1present = false;
                    button2present = false;
                    button3present = false;
                    button4present = false;
                    loadNextChapterButton();
                    snugglesIsFriend = true;
                }
                else if (marker == 2 && choice == 1) {
                    String pageText = getString(R.string.page1_5);
                    pageText = String.format(pageText, name);
                    storyTextView.setText(pageText);
                    choice1Button.setText(R.string.page1_choice1_1);
                    choice2Button.setText(R.string.page1_choice2_1);
                    bookmarker.setMarker(1);
                }
                else if (marker == 2 && choice == 2) {
                    storyTextView.setText(R.string.page1_6);
                    removeChoiceButtons(0);
                    nextChapter.setNextPage(4);
                    button1present = false;
                    button2present = false;
                    button3present = false;
                    button4present = false;
                    loadNextChapterButton();
                }
                break;
            case 3:
                if (marker == 0 && choice == 1) {
                    // user showed Snuggles the kingdom and goes with him to his house
                    storyTextView.setText(R.string.page3_1);
                    choice1Button.setText(R.string.page3_choice1_1);
                    choice2Button.setText(R.string.page3_choice2_1);
                    bookmarker.setMarker(1);
                }
                else if (marker == 0 && choice == 2) {
                    // user showed Snuggles the kingdom, but doesn't go to his house, instead continues on to Puddles (page 4)
                    storyTextView.setText(R.string.page3_2);
                    removeChoiceButtons(0);
                    nextChapter.setNextPage(4);
                    button1present = false;
                    button2present = false;
                    button3present = false;
                    button4present = false;
                    loadNextChapterButton();
                }
                else if (marker == 1 && choice == 1) {
                    // user ate the food in Snuggles' house
                    storyTextView.setText(R.string.page3_3);
                    removeChoiceButtons(0);
                    nextChapter.setNextPage(5);
                    loadNextChapterButton();
                    eatTheFood = true;
                }
                else if (marker == 1 && choice == 2) {
                    // user didn't eat the food in Snuggles' house and makes friends with female human
                    storyTextView.setText(R.string.page3_4);
                    removeChoiceButtons(0);
                    nextChapter.setNextPage(5);
                    loadNextChapterButton();
                }
                break;
            case 5:  // user comes from Snuggles' house
            case 6:  // user just came from Puddles
                if (marker == 0 && choice == 2) {   // Find a snack first time
                    chapter4.setUserChoice(choice); // 2
                    int snack = chapter4.getSnack();
                    if (snack == 1) {
                        storyTextView.setText(R.string.page5_find1_mouse);  // mouse
                    }
                    else if (snack == 2) {
                        storyTextView.setText(R.string.page5_find1_spider);  // spider
                    }
                    else if (snack == 3) {
                        storyTextView.setText(R.string.page5_find1_butterfly);  // butterfly
                    }
                    else if (snack == 4) {
                        storyTextView.setText(R.string.page5_find1_bird);  // bird
                    }
                    choice1Button.setVisibility(View.VISIBLE);
                    bookmarker.setMarker(1);
                }
                else if (marker == 1 && choice == 2) {  // Find a snack second time
                    chapter4.setUserChoice(choice); // 2
                    int snack = chapter4.getSnack();
                    if (snack == 1) {
                        storyTextView.setText(R.string.page5_find2_mouse);  // mouse
                    }
                    else if (snack == 2) {
                        storyTextView.setText(R.string.page5_find2_spider);  // spider
                    }
                    else if (snack == 3) {
                        storyTextView.setText(R.string.page5_find2_butterfly);  // butterfly
                    }
                    else if (snack == 4) {
                        storyTextView.setText(R.string.page5_find2_bird);  // bird
                    }
                    choice2Button.setVisibility(View.GONE);
                }
                else if (marker == 1 && choice == 1) {  // Catch a snack first time
                    chapter4.setUserChoice(choice); // 1
                    boolean snackCaught = chapter4.isSnackCaught();
                    int snack = chapter4.getSnack();
                    if (snackCaught) {
                        if (snack == 1) {
                            storyTextView.setText(R.string.page5_catch2_mouse);  // mouse
                        }
                        else if (snack == 2) {
                            storyTextView.setText(R.string.page5_catch2_spider);  // spider
                        }
                        else if (snack == 3) {
                            storyTextView.setText(R.string.page5_catch2_butterfly);  // butterfly
                        }
                        else if (snack == 4) {
                            storyTextView.setText(R.string.page5_catch2_bird);  // bird
                        }
                        choice2Button.setVisibility(View.GONE);
                        choice1Button.setText(R.string.take_snack_to_gf);
                        bookmarker.setMarker(4);
                    }
                    else {
                        if (snack == 1) {
                            storyTextView.setText(R.string.page5_catch1_mouse);  // mouse
                        }
                        else if (snack == 2) {
                            storyTextView.setText(R.string.page5_catch1_spider);  // spider
                        }
                        else if (snack == 3) {
                            storyTextView.setText(R.string.page5_catch1_butterfly);  // butterfly
                        }
                        else if (snack == 4) {
                            storyTextView.setText(R.string.page5_catch1_bird);  // bird
                        }
                        choice2Button.setVisibility(View.GONE);
                        bookmarker.setMarker(3);
                    }
                }
                else if (marker == 3 && choice == 1) {  // catch a snack second time
                    chapter4.setUserChoice(choice); // 1
                    boolean snackCaught = chapter4.isSnackCaught();
                    int snack = chapter4.getSnack();
                    if (snackCaught) {
                        if (snack == 1) {
                            storyTextView.setText(R.string.page5_catch2_mouse);  // mouse
                        }
                        else if (snack == 2) {
                            storyTextView.setText(R.string.page5_catch2_spider);  // spider
                        }
                        else if (snack == 3) {
                            storyTextView.setText(R.string.page5_catch2_butterfly);  // butterfly
                        }
                        else if (snack == 4) {
                            storyTextView.setText(R.string.page5_catch2_bird);  // bird
                        }
                        choice2Button.setVisibility(View.GONE);
                        choice1Button.setText(R.string.take_snack_to_gf);
                        bookmarker.setMarker(4);
                    }
                    else {
                        // didn't catch the snack. Go to GF empty handed
                        if (snack == 1) {
                            storyTextView.setText(R.string.page5_catch3_mouse);  // mouse
                        }
                        else if (snack == 2) {
                            storyTextView.setText(R.string.page5_catch3_spider);  // spider
                        }
                        else if (snack == 3) {
                            storyTextView.setText(R.string.page5_catch3_butterfly);  // butterfly
                        }
                        else if (snack == 4) {
                            storyTextView.setText(R.string.page5_catch3_bird);  // bird
                        }
                        choice2Button.setVisibility(View.GONE);
                        choice1Button.setText(R.string.take_snack_to_gf);
                        bookmarker.setMarker(4);
                    }
                }
                else if (marker == 4 && choice == 1) {
                    boolean snackCaught = chapter4.isSnackCaught();
                    int snack = chapter4.getSnack();
                    if (snackCaught) {
                        if (snack == 1) {
                            storyTextView.setText(R.string.page5_mouse);  // mouse
                            leiaIsHappy = true;
                        }
                        else if (snack == 2) {
                            storyTextView.setText(R.string.page5_spider);  // spider
                            leiaIsHappy = false;
                        }
                        else if (snack == 3) {
                            storyTextView.setText(R.string.page5_butterfly);  // butterfly
                            leiaIsHappy = true;
                        }
                        else if (snack == 4) {
                            storyTextView.setText(R.string.page5_bird);  // bird
                            leiaIsHappy = true;
                        }
                    }
                    else {
                        storyTextView.setText(R.string.page5_nothing);  // nothing
                        leiaIsHappy = false;
                    }

                    removeChoiceButtons(0);
                    if (snugglesIsFriend && leiaIsHappy) {
                        // Skipping page 7, because that one is broken (didn't load choice button 1)
                        nextChapter.setNextPage(11); // snuggles is friend & leia is happy
                    }
                    else if (snugglesIsFriend) {
                        nextChapter.setNextPage(8); // snuggles is friend & leia is angry
                    }
                    else if (leiaIsHappy) {
                        nextChapter.setNextPage(9); // snuggles is enemy & leia is happy
                    }
                    else {
                        nextChapter.setNextPage(10); // snuggles is enemy & leia is angry
                    }
                    button1present = true;
                    button2present = true;
                    button3present = true;
                    button4present = true;
                    loadNextChapterButton();
                }
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                chapter1.setUserChoice(choice);
                int humanResponse = chapter1.getTotalPoints();
                if (marker == 0) {
                    if (humanResponse == 25) {
                        storyTextView.setText(R.string.page7_25points);
                    }
                    else if (humanResponse == 50) {
                        storyTextView.setText(R.string.page7_50points);
                    }
                    else if (humanResponse == 75) {
                        storyTextView.setText(R.string.page7_75points);
                    }
                    else if (humanResponse == 100) {
                        storyTextView.setText(R.string.page7_100points);
                        choice1Button.setText(R.string.page7_choice1_1);
                        choice2Button.setText(R.string.page7_choice2_1);
                        choice3Button.setText(R.string.page7_choice3_1);
                        choice4Button.setVisibility(View.GONE);
                        bookmarker.setMarker(1);
                    }
                    else if (humanResponse > 100) {
                        storyTextView.setText(R.string.page7_over_100points);
                        choice1Button.setText(R.string.page7_choice1_1);
                        choice2Button.setText(R.string.page7_choice2_1);
                        choice3Button.setText(R.string.page7_choice3_1);
                        choice4Button.setVisibility(View.GONE);
                        bookmarker.setMarker(1);
                    }
                }

                if (marker == 1) {
                    if (choice == 50) {
                        storyTextView.setText(R.string.page7_1);
                    }
                    else if (choice == 25) {
                        storyTextView.setText(R.string.page7_2);
                    }
                    else if (choice == 75) {
                        storyTextView.setText(R.string.page7_3);
                    }
                    removeChoiceButtons(0);
                    if (snugglesIsFriend && eatTheFood) {
                        nextChapter.setNextPage(12);  // frog part
                        button3present = false;
                        button4present = false;
                    }
                    else if (snugglesIsFriend) {
                        nextChapter.setNextPage(13);  // no frog, intro to hide and seek (on page 14)
                        button2present = false;
                        button3present = false;
                        button4present = false;
                    }
                    else {
                        button3present = true;
                        button4present = false;
                        nextChapter.setNextPage(15);  // the investigation
                    }
                    loadNextChapterButton();
                }
                break;
            case 12:
                if (marker == 0) {
                    if (choice == 1) {
                        storyTextView.setText(R.string.page12_1);
                    }
                    else if (choice == 2) {
                        storyTextView.setText(R.string.page12_2);
                    }
                    choice1Button.setVisibility(View.GONE);
                    nextChapter.setNextPage(14); // to hide and seek
                    choice2Button.setText(R.string.page12_choice1_1);
                    choice2Button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            button1present = true;
                            button2present = true;
                            button3present = true;
                            button4present = true;
                            chapter6.setPlay(true); // choosing snuggles' hiding spot for page 14
                            int nextPage = nextChapter.getNextPage();
                            loadPage(nextPage);
                        }
                    });
                }
                break;
            case 13:
                if (choice == 1) {
                    nextChapter.setNextPage(14);  // to hide and seek
                    button1present = true;
                    button2present = true;
                    button3present = true;
                    button4present = true;
                    chapter6.setPlay(true); // choosing snuggles' hiding spot for page 14
                    int nextPage = nextChapter.getNextPage();
                    loadPage(nextPage);
                }
                break;
            case 14:
                int hidingSpotSnuggles = chapter6.getHidingSpotSnuggles();
                if (marker == 0) {
                    if (hidingSpotSnuggles == choice) {
                        storyTextView.setText(R.string.page14_5);
                        bookmarker.setMarker(1);
                        // enable all buttons
                        choice1Button.setClickable(true);
                        choice2Button.setClickable(true);
                        choice3Button.setClickable(true);
                        choice4Button.setClickable(true);
                        choice1Button.setTextColor(Color.rgb(0, 0, 0));  // black
                        choice2Button.setTextColor(Color.rgb(0, 0, 0));
                        choice3Button.setTextColor(Color.rgb(0, 0, 0));
                        choice4Button.setTextColor(Color.rgb(0, 0, 0));
                    }
                    else {
                        if (choice == 1) {
                            storyTextView.setText(R.string.page14_1);
                            choice1Button.setClickable(false); // disable button
                            choice1Button.setTextColor(Color.rgb(206, 206, 206));  // grey
                        }
                        else if (choice == 2) {
                            storyTextView.setText(R.string.page14_2);
                            choice2Button.setClickable(false);
                            choice2Button.setTextColor(Color.rgb(206, 206, 206));
                        }
                        else if (choice == 3) {
                            storyTextView.setText(R.string.page14_3);
                            choice3Button.setClickable(false);
                            choice3Button.setTextColor(Color.rgb(206, 206, 206));
                        }
                        else if (choice == 4) {
                            storyTextView.setText(R.string.page14_4);
                            choice4Button.setClickable(false);
                            choice4Button.setTextColor(Color.rgb(206, 206, 206));
                        }
                    }
                }

                if (marker == 1) {
                    String buttonText = "";
                    switch(choice) {
                        case 1:
                            buttonText = choice1Button.getText().toString().toLowerCase();
                            break;
                        case 2:
                            buttonText = choice2Button.getText().toString().toLowerCase();
                            break;
                        case 3:
                            buttonText = choice3Button.getText().toString().toLowerCase();
                            break;
                        case 4:
                            buttonText = choice4Button.getText().toString().toLowerCase();
                            break;
                    }
                    Resources r = getResources();
                    String text = r.getString(R.string.page14_6, buttonText);
                    storyTextView.setText(text);
                    removeChoiceButtons(0);
                    button1present = true;
                    button2present = false;
                    button3present = false;
                    button4present = false;
                    nextChapter.setNextPage(16);  // to saving Leia (with Snuggles)
                    loadNextChapterButton();
                }
                break;
            case 15:
                int investigation = chapter6.getInvestigation();
                Resources res = getResources();
                String lastSentence = res.getString(R.string.page15_4);
                if (choice == 1) {
                    storyImageView.setImageDrawable(getResources().getDrawable(R.drawable.page15_1));
                    if (investigation == 2) {
                        String puddlesText = res.getString(R.string.page15_1);
                        String text = puddlesText + lastSentence;
                        storyTextView.setText(text);
                    }
                    else {
                        storyTextView.setText(R.string.page15_1);
                    }
                    choice1Button.setClickable(false); // disable button
                    choice1Button.setTextColor(Color.rgb(206, 206, 206));  // grey
                }
                else if (choice == 2) {
                    storyImageView.setImageDrawable(getResources().getDrawable(R.drawable.page15_2));
                    if (investigation == 2) {
                        String jonText = res.getString(R.string.page15_2);
                        String text = jonText + lastSentence;
                        storyTextView.setText(text);
                    }
                    else {
                        storyTextView.setText(R.string.page15_2);
                    }
                    choice2Button.setClickable(false);
                    choice2Button.setTextColor(Color.rgb(206, 206, 206));
                }
                else if (choice == 3) {
                    storyImageView.setImageDrawable(getResources().getDrawable(R.drawable.page15_3));
                    if (investigation == 2) {
                        String rufusText = res.getString(R.string.page15_3);
                        String text = rufusText + lastSentence;
                        storyTextView.setText(text);
                    }
                    else {
                        storyTextView.setText(R.string.page15_3);
                    }
                    choice3Button.setClickable(false);
                    choice3Button.setTextColor(Color.rgb(206, 206, 206));
                }

                if (investigation == 2) {
                    removeChoiceButtons(0);
                    button1present = true;
                    button2present = false;
                    button3present = false;
                    button4present = false;
                    nextChapter.setNextPage(17);  // to saving Leia (without Snuggles)
                    loadNextChapterButton();
                }
                chapter6.setInvestigation(investigation + 1);
                break;
            case 16:
                chapter7.setSnugglesIsFriend(true);
                int userDamage = chapter7.getUserDamage();
                int snugglesDamage = chapter7.getSnugglesDamage();
                int luckyDamage = chapter7.getLuckyDamage();
                int userHealth = chapter7.getUserHealth();
                int luckyHealth = chapter7.getLuckyHealth();
                Resources r = getResources();
                String link = r.getString(R.string.link);
                String text = r.getString(R.string.page16_1, userDamage, snugglesDamage, luckyHealth, luckyDamage, userHealth);
                if (luckyHealth > 0 && userHealth > 0) {
                    storyTextView.setText(text);
                }
                else if (luckyHealth > 0 && userHealth <= 0) {  // Lucky wins
                    if (leiaIsHappy) {
                        String endText = r.getString(R.string.page16_4) + link;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    else {
                        String endText = r.getString(R.string.page16_5) + link;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    // change button to go to the beginning
                }
                else if (luckyHealth <= 0 && userHealth > 0) {  // user wins
                    if (leiaIsHappy) {
                        String endText = r.getString(R.string.page16_2) + link;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    else {
                        String endText = r.getString(R.string.page16_3) + link;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                }
                else if (luckyHealth <= 0 && userHealth <= 0) {  // it's a tie
                    if (leiaIsHappy) {
                        String endText = r.getString(R.string.page16_6) + link;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    else {
                        String endText = r.getString(R.string.page16_7) + link;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                }
                isFinalPage = true;
                break;
            case 17:
                chapter7.setSnugglesIsFriend(false);
                int userDamagePoints = chapter7.getUserDamage();
                int luckyDamagePoints = chapter7.getLuckyDamage();
                int userHealthPoints = chapter7.getUserHealth();
                int luckyHealthPoints = chapter7.getLuckyHealth();
                Resources resources = getResources();
                String endLink = resources.getString(R.string.link);
                String fightText = resources.getString(R.string.page17_1, userDamagePoints, luckyHealthPoints, luckyDamagePoints, userHealthPoints);
                if (luckyHealthPoints > 0 && userHealthPoints > 0) {
                    storyTextView.setText(fightText);
                }
                else if (luckyHealthPoints > 0 && userHealthPoints <= 0) {  // Lucky wins
                    if (leiaIsHappy) {
                        String endText = resources.getString(R.string.page17_4) + endLink;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    else {
                        String endText = resources.getString(R.string.page17_5) + endLink;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                }
                else if (luckyHealthPoints <= 0 && userHealthPoints > 0) {  // user wins
                    if (leiaIsHappy) {
                        String endText = resources.getString(R.string.page17_2) + endLink;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    else {
                        String endText = resources.getString(R.string.page17_3) + endLink;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                }
                else if (luckyHealthPoints <= 0 && userHealthPoints <= 0) {  // it's a tie
                    if (leiaIsHappy) {
                        String endText = resources.getString(R.string.page17_6) + endLink;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                    else {
                        String endText = resources.getString(R.string.page17_7) + endLink;
                        storyTextView.setText(endText);
                        playAgain();
                    }
                }
                isFinalPage = true;
                break;
        }
    }

    private void playAgain() {
        removeChoiceButtons(0);
        nextChapter.setNextPage(0);
        loadNextChapterButton();
    }
}
