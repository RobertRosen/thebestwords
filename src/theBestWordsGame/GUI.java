package theBestWordsGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Contains the main parts of the GUI. Also has some logic for matching words.
 *
 * @author Robert Rosencrantz
 */
public class GUI extends JFrame {

    // Components
    private JPanel      pnlMain;
    private JPanel      pnlTyping;
    private WordsRain   pnlGame;
    private JTextField  textFieldWordTyped;
    private JTextField  textFieldPoints;

    // Store current score.
    private int         points;

    /**
     * Construct and initialize the GUI.
     */
    public GUI() {
        setupGamePanel();
        setupTypePanel();
        setupMainPanel();
        setupFrame();
        listenToTyping();
    }

    // Helps to setup the GUI
    private void setupFrame() {
        setSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);
        setTitle("The Best Words");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(pnlMain);
        setVisible(true);
        setResizable(false);
        pack();
    }

    // Helps to setup the GUI
    private void setupMainPanel() {
        pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setPreferredSize(new Dimension(1000, 600));

        pnlMain.add(pnlTyping, BorderLayout.NORTH);
        pnlMain.add(pnlGame, BorderLayout.CENTER);
    }

    // Helps to setup the GUI
    private void setupTypePanel() {
        pnlTyping = new JPanel(new BorderLayout());

        JLabel lblTrump = new JLabel();
        lblTrump.setIcon(new ImageIcon("images/i_know_words.jpg"));

        setupTextFieldWordTyped();
        setupTextFieldPoints();

        pnlTyping.add(lblTrump, BorderLayout.WEST);
        pnlTyping.add(textFieldWordTyped, BorderLayout.CENTER);
        pnlTyping.add(textFieldPoints, BorderLayout.EAST);
    }

    // Helps to setup the GUI
    private void setupGamePanel() {
        pnlGame = new WordsRain();
    }

    // Helps to setup the GUI
    private void setupTextFieldWordTyped() {
        textFieldWordTyped = new JTextField("");
        textFieldWordTyped.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        textFieldWordTyped.setBackground(Color.BLACK);
        textFieldWordTyped.setForeground(Color.WHITE);
        textFieldWordTyped.setOpaque(true);
        textFieldWordTyped.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    // Helps to setup the GUI
    private void setupTextFieldPoints() {
        textFieldPoints = new JTextField("0p  ");
        textFieldPoints.setFont(new Font("Comic Sans MS", Font.BOLD, 80));
        textFieldPoints.setBackground(Color.BLACK);
        textFieldPoints.setForeground(Color.WHITE);
        textFieldPoints.setOpaque(true);
        textFieldPoints.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    /**
     * Listens for when a keyboard key is released.
     */
    private void listenToTyping() {
        textFieldWordTyped.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                matchingWord();
            }
        });
    }

    /**
     * Check for matching words and trigger consequences of for a match.
     */
    private void matchingWord() {
        for (int i = 0; i < pnlGame.getFallingWordsList().size(); i++) {
            if (textFieldWordTyped.getText().equals(pnlGame.getFallingWordsList().get(i).getText())) {
                textFieldWordTyped.setText("");         // Reset typing area after a matching word.
                pnlGame.getFallingWordsList().get(i).setMatched(true);
                points++;
                textFieldPoints.setText(points + "p");
                pnlGame.setFallingWordsList(i);         // Avoid getting points for same word again
                i--;                                    // To not miss a word in the list
            }
        }
    }
}