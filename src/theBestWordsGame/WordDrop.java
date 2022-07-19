package theBestWordsGame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Represents a word dropping down while running.
 *
 * @author Robert Rosencrantz
 */
public class WordDrop extends JTextField implements Runnable {
    private Thread thread;
    private WordsRain wordsRain;

    private int yPosition = 0;  // Origin of the word appearing

    private boolean alive = false;
    private boolean matched = false;
    private int xPosition;      // Origin is set randomly in constructor

    /**
     * Constructs andinitialize this thread.
     * @param wordsrain The panel where this word will appear.
     * @param word Word from list in GUI.
     */
    public WordDrop(WordsRain wordsrain, String word) {
        this.wordsRain = wordsrain;
        setText(word);               // Set this threads word from construction in wordsrain.

        setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        setSize(20*word.length(), 30);
        setForeground(Color.WHITE);
        setOpaque(false);
        setBorder(null);
        setEditable(false);

        xPosition = new Random().nextInt(800);      // Words appear random to this number
    }

    /**
     * Thread keeps running until there is a match typed in GUI.
     */
    @Override
    public void run() {     // Long method, split up...
        while(alive) {
            if (matched) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setForeground(Color.GREEN);
                    }
                });
                alive = false;
                thread = null;
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                yPosition++;
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        setLocation(xPosition, yPosition);
                    }
                });
                checkGameOver();
            }
        }
    }

    // Starts a new word thread if argument passed when called is true.
    public void setAlive(boolean alive) {
        this.alive = alive;
        thread = new Thread(this);
        thread.start();
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    /**
     * Checks if this thread has reached bottom of the game panel and stops the game.
     */
    private void checkGameOver() {
        if (yPosition > 439) {
            yPosition = 269;        // Set enlarged text to bottom of screen
            xPosition = 0;
            setLocation(xPosition,yPosition);
            setFont(new Font("Comic Sans MS", Font.PLAIN, 190));    // Enlarge font size
            setSize(1000, 200);
            setForeground(Color.RED);
            setOpaque(false);
            setBorder(null);
            setText("Game Over");
            repaint();              // Repaint this panel.
            alive = false;          // Kill thread
            thread = null;

            wordsRain.gameOver();   // Call words rain to stop.
        }
    }
}