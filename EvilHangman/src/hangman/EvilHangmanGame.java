package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Character.isLetter;

public class EvilHangmanGame implements IEvilHangmanGame{
    private Set<String> myDictionary;
    private Integer numGuessesLeft;
    private ArrayList<Character> usedGuesses;
    private Map<Pattern, Set<String>> myMap;
    private boolean done;

    public EvilHangmanGame(int guesses) {
        myDictionary = new LinkedHashSet<>();
        numGuessesLeft = guesses;
        usedGuesses = new ArrayList<>();
        myMap = new HashMap<>();
        done = false;
    }
    @Override
    public void startGame(File dictionary, int wordLength) {
        try {
            Scanner scanner = new Scanner(dictionary);
            while(scanner.hasNext()) {
                scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
                String myWord = scanner.next();
                if (myWord.length() == wordLength) {
                    myDictionary.add(myWord.toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(!done) {
         char guess = PrintTurnInfo();
         try {
             makeGuess(guess);
         }catch (GuessAlreadyMadeException e) {
             e.printStackTrace();
         }




            if(numGuessesLeft == 0) {
                done = true;
            }
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
       // Set<String> myNewDictionary = new LinkedHashSet<>();
        for(String word : myDictionary) {
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < word.length(); i++) {
                if(word.charAt(i) == guess) {
                    indices.add(i);
                }
            }
            boolean matchFound = false;
            for (Map.Entry<Pattern, Set<String>> entry : myMap.entrySet()) {
                Pattern myPattern = entry.getKey();
                Set<String> myStrings = entry.getValue();
                if(indices.equals(myPattern.ReturnIndices())) {
                    myStrings.add(word);
                    //myNewDictionary.add(word);
                    matchFound = true;
                }

            }
            if(matchFound == false) {
                Pattern myNewPattern = new Pattern(word.length(), word, indices);
                Set<String> myNewString = new HashSet<>();
                myNewString.add(word);
                //myNewDictionary.add(word);
                myMap.put(myNewPattern, myNewString);
            }

        }
        this.myDictionary = RunEvilAlgorithm();
        //make a function to run evil algorithm
        return null;
    }

    public Set<String> RunEvilAlgorithm() {
        Map<Pattern, Set<String>> myMapCopy = myMap;
        Map<Pattern, Set<String>> myCandidateMap = new HashMap<>();
        int maxValue = 0;
        for (Map.Entry<Pattern, Set<String>> entry : myMapCopy.entrySet()) {
            Pattern myPattern = entry.getKey();
            Set<String> myStrings = entry.getValue();
            if (maxValue > myStrings.size()) {
                //do nothing
            }
            else if (maxValue < myStrings.size()) {
                maxValue = myStrings.size();
                myCandidateMap = new HashMap<>();
                myCandidateMap.put(myPattern, myStrings);
            }
            else if (maxValue == myStrings.size()) {
                myCandidateMap.put(myPattern, myStrings);
            }
        }
        if (myCandidateMap.size() == 0) {
            return null;
            //error
        }
        else if(myCandidateMap.size() == 1) {
            for (Set<String> myStringSet : myCandidateMap.values()) {
                //this.myMap = myCandidateMap;
                return myStringSet;
            }
        }
        else if (myCandidateMap.size() > 1) {

        }
        return null;
    }

    public Set<String> RunFirstPriority(Map<Pattern, Set<String>> mapCandidate) {
        Map<Pattern, Set<String>> myMapCopy = mapCandidate;
        Map<Pattern, Set<String>> myCandidateMap = new HashMap<>();
        Character lastGuess = usedGuesses.get(usedGuesses.size());
        for(Map.Entry<Pattern, Set<String>> entry : myMapCopy.entrySet()){
            Pattern myPattern = entry.getKey();
            Set<String> myStrings = entry.getValue();
            for(String word : myStrings) {
                if(!(word.indexOf(lastGuess) >= 0)) { //letter not found
                    myCandidateMap.
                }
            }
        }
    }

    public void DeleteGame() {
        //Run This to clear everything from the last game
    }

    public char PrintTurnInfo() {
        System.out.println("You have " + numGuessesLeft + " guesses left");
        System.out.println("Used letters: ");//used letters go here
        System.out.println("Word: ----"); //Need the dashes here
        System.out.println("Enter guess: ");
        String response = "";
        boolean validInput = false;
        Character myGuess = null;
        while (!validInput) {
            Scanner in = new Scanner(System.in);
            response = in.next(); //enter response into repos
            response = response.toLowerCase();
            //check what happens if it is an empty string
            myGuess = response.charAt(0);
            if (response.equals("") || !Character.isLetter(myGuess)) {
                System.out.println("Invalid Input");
            }
            else if (usedGuesses.contains(myGuess)) {
                System.out.println("You already used that letter");
                //figure out how to throw the exception
            }
            else {
                validInput = true;
            }
        }
        usedGuesses.add(myGuess);
        return myGuess;
        //sorry or Yes there is numLetters of char
    }
}
