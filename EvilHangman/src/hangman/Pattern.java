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
         myToStringBuild = new StringBuilder(sampleWord);
        for (int i = 0; i < sampleWord.length(); i++) {
            if(myToStringBuild.charAt(i) == letter) {

            }
            else {
                myToStringBuild.setCharAt(i, '-');
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
