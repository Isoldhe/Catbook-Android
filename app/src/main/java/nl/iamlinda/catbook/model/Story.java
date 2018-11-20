package nl.iamlinda.catbook.model;

import nl.iamlinda.catbook.R;

public class Story {
    private Page[] pages;

    public Story() {
        // creating an array of pages (so 18 times a Page object)
        pages = new Page[18];

        // New page every time the buttons and their properties change
        // So one chapter can have multiple pages

        pages[0] = new Page(R.drawable.page0,
                R.string.page0,
                new Choice(R.string.page0_choice1, 50),
                new Choice(R.string.page0_choice2, 25),
                new Choice(R.string.page0_choice3, 75),
                new Choice(R.string.page0_choice4, 25));

        // For the text only chapters (without any calculations etc.) we can use this Page constructor:
        // JS: chapter 2a
        pages[1] = new Page(R.drawable.page1,
                R.string.page1,
                new Choice(R.string.page1_choice1, 1),
                new Choice(R.string.page1_choice2, 2));
        // JS: chapter 2b
        pages[2] = new Page(R.drawable.page1,
                R.string.page2,
                new Choice(R.string.page1_choice1, 1),
                new Choice(R.string.page1_choice2, 2));

        pages[3] = new Page(R.drawable.page3,
                R.string.page3,
                new Choice(R.string.page3_choice1, 1),
                new Choice(R.string.page3_choice2, 2));

        pages[4] = new Page(R.drawable.page4,
                R.string.page4);

        pages[5] = new Page(R.drawable.page5,
                R.string.page5,
                new Choice(R.string.page5_choice1, 1),
                new Choice(R.string.page5_choice2, 2));

        pages[6] = new Page(R.drawable.page5,
                R.string.page6,
                new Choice(R.string.page5_choice1, 1),
                new Choice(R.string.page5_choice2, 2));

        // Skipping page 7, because that one is broken (didn't load choice button 1)
        pages[8] = new Page(R.drawable.page7,
                R.string.page8,
                new Choice(R.string.page7_choice1, 50),
                new Choice(R.string.page7_choice2, 25),
                new Choice(R.string.page7_choice3, 75),
                new Choice(R.string.page7_choice4, 25));

        pages[9] = new Page(R.drawable.page7,
                R.string.page9,
                new Choice(R.string.page7_choice1, 50),
                new Choice(R.string.page7_choice2, 25),
                new Choice(R.string.page7_choice3, 75),
                new Choice(R.string.page7_choice4, 25));

        pages[10] = new Page(R.drawable.page7,
                R.string.page10,
                new Choice(R.string.page7_choice1, 50),
                new Choice(R.string.page7_choice2, 25),
                new Choice(R.string.page7_choice3, 75),
                new Choice(R.string.page7_choice4, 25));

        pages[11] = new Page(R.drawable.page7,
                R.string.page11,
                new Choice(R.string.page7_choice1, 50),
                new Choice(R.string.page7_choice2, 25),
                new Choice(R.string.page7_choice3, 75),
                new Choice(R.string.page7_choice4, 25));

        pages[12] = new Page(R.drawable.page12,
                R.string.page12,
                new Choice(R.string.page12_choice1, 1),
                new Choice(R.string.page12_choice2, 2));

        pages[13] = new Page(R.drawable.page13,
                R.string.page13,
                new Choice(R.string.page12_choice1_1, 1));

        pages[14] = new Page(R.drawable.page13,
                R.string.page14,
                new Choice(R.string.page14_choice1, 1),
                new Choice(R.string.page14_choice2, 2),
                new Choice(R.string.page14_choice3, 3),
                new Choice(R.string.page14_choice4, 4));

        pages[15] = new Page(R.drawable.page15,
                R.string.page15,
                new Choice(R.string.page15_choice1, 1),
                new Choice(R.string.page15_choice2, 2),
                new Choice(R.string.page15_choice3, 3));

        pages[16] = new Page(R.drawable.page16,
                R.string.page16,
                new Choice(R.string.page16_choice1, 1));

        pages[17] = new Page(R.drawable.page16,
                R.string.page17,
                new Choice(R.string.page16_choice1, 1));
    }

    public Page getPage(int pageNumber) {
        if (pageNumber >= pages.length) {
            pageNumber = 0;
        }
        return pages[pageNumber];
    }
}
