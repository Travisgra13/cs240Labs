package hangman;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pattern {
    private int wordLength;
    private String sampleWord;
    private StringBuilder myToStringBuild;
    private ArrayList<Integer> myIndices;
    public Pattern(int wordLength, String word, ArrayList<Integer> myIndices) {
        this.wordLength = wordLength;
        this.sampleWord = word;
        this.myIndices = myIndices;
        myToStringBuild = new StringBuilder();
    }
    public void createPattern(char letter) {
        StringBuilder word = new StringBuilder(sampleWord);
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == letter) {

            }
            else {
                word.setCharAt(i, '-');
            }
        }
    }

    public ArrayList<Integer> ReturnIndices() {
        return myIndices;
    }
    public String toString() {
        return myToStringBuild.toString();
    }
}
