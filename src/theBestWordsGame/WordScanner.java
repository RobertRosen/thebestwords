package theBestWordsGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Reads a text file, and returns a list of words from getListWords-method.
 */
public class WordScanner {

    // list to return
    private ArrayList<String> listWords = new ArrayList<>();
    private File file;

    public WordScanner(String textFilePath) {
        file = new File(textFilePath);      // Create file from a text file
        readWords();
    }

    private void readWords() {
        Scanner input = null;
        try {
            input = new Scanner(file);      // Create input from file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (input != null) {
            while (input.hasNext()) {
                listWords.add(input.next());    // Add Words from input as strings to a list
            }
            input.close();
        }

        Collections.shuffle(listWords);     // Randomise the list order of words
    }

    public ArrayList<String> getListWords() {
        return listWords;
    }
}