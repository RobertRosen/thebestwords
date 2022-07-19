package theBestWordsGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * The task to add new words to the GUI. Word drops are raining down the screen.
 *
 * @author Robert Rosencrantz
 */
public class WordsRain extends JPanel implements Runnable {

    private boolean alive = true;   // Set to false to stop thread.
    private Random random;

    private ArrayList<WordDrop> fallingWordsList = new ArrayList<>();   // Stores word drop threads

    /**
     * Construct and initialize a thread with this class' tasks.
     */
    public WordsRain() {
        random = new Random();
        setBackground(Color.BLACK);

        // Create a list of string words.
        ArrayList<String> wordBuffer = new WordScanner("textFiles/the_best_words.txt").getListWords();
        // Fill upp the falling words list with threads.
        for (int i = 0; i < wordBuffer.size(); i++) {
            fallingWordsList.add(new WordDrop(this, wordBuffer.remove(0)));
        }

        new Thread(this).start();
    }

    /**
     * Drops a word on a random interval.
     */
    @Override
    public void run() {
        int delayWord;
        int i = 0;
        while(alive) {
            delayWord = random.nextInt(5000) + 1000;
            fallingWordsList.get(i).setAlive(true);                                // Start a new word drop thread.
            add(fallingWordsList.get(i++));                                // Put the new word on wordsrain thread.
            try {
                Thread.sleep(delayWord);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fallingWordsList.clear();                                   // To not keep getting points after game over.
    }

    /**
     * Method is called when the game is over. Triggers changes in the view.
     */
    public void gameOver() {
        for (WordDrop wordDrop : fallingWordsList) {
            wordDrop.setAlive(false);                                                    // Stop all word threads.
        }
        alive = false;                                                            // Stops this words rain thread.
    }

    public ArrayList<WordDrop> getFallingWordsList() {
        return fallingWordsList;
    }

    public void setFallingWordsList(int i) {
        fallingWordsList.remove(i);
    }
}
