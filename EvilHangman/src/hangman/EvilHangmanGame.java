package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Character.isLetter;

public class EvilHangmanGame implements IEvilHangmanGame{
    private Set<String> myDictionary;
    private Integer numGuessesLeft;
    private TreeSet<Character> usedGuesses;
    private Character lastGuess;
    private Map<Pattern, Set<String>> myMap;
    private StringBuilder masterSb;
    private boolean done;

    public EvilHangmanGame(int guesses) {
        myDictionary = new LinkedHashSet<>();
        numGuessesLeft = guesses;
        usedGuesses = new TreeSet<>();
        myMap = new HashMap<>();
        masterSb = new StringBuilder();
        done = false;
    }
    @Override
    public void startGame(File dictionary, int wordLength) {
        try {
            Scanner scanner = new Scanner(dictionary);
            InitializeMasterSb(wordLength);
            while(scanner.hasNext()) {
                scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
                String myWord = scanner.next();
                if (myWord.length() == wordLength) {
                    myDictionary.add(myWord.toLowerCase());
                }
            }
            if (myDictionary.size() == 0) {
                System.out.println("File is without String Tokens");
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(!done) {
         char guess = PrintTurnInfo();
         this.lastGuess = guess;
         try {
             makeGuess(guess);
             Integer numFound = FoundLetter();
             if(numFound > 0) {
                 if (GuessedCorrectWord()) {
                     System.out.println("You Win!");
                     System.out.println("The word was: " + masterSb.toString());
                     return;
                 }
                 //return index arraylist so we can see where we should add them in the stringbuilder
                 else {
                     System.out.println("Yes, there is " + numFound + " " + guess);
                 }
             }
             else {
                 numGuessesLeft--;
                 if(numGuessesLeft == 0) {
                     System.out.println("You lose!");
                     System.out.println("The word was: " + PrintLostPrompt());
                     done = true;
                 }
                 else {
                     System.out.println("Sorry, there are no " + guess + "'s");
                 }
             }
         }catch (GuessAlreadyMadeException e) {
             e.printStackTrace();
         }
        }
        return;
    }

    public String PrintLostPrompt() {
        for (String word : this.myDictionary) {
            return word;
        }
        return null;
    }
    public boolean GuessedCorrectWord() {
        for (int i = 0; i < masterSb.length(); i++) {
            if (masterSb.charAt(i) == '-') {
                return false;
            }
        }
        return true;
    }
    public void InitializeMasterSb(int wordLength) {
        masterSb.setLength(wordLength);
        for (int i = 0; i < wordLength; i++) {
            masterSb.setCharAt(i, '-');
        }
    }
    public Integer FoundLetter() {
        ArrayList<Integer> maxVals = new ArrayList<>();
        ArrayList<Integer> locVals = new ArrayList<>();
        String minWord = "";
        for(String word : myDictionary) {
            Integer numIndices = 0;
            if (word.indexOf(lastGuess) >= 0){
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == lastGuess) {
                        numIndices++;
                        locVals.add(i);
                    }
                }
                maxVals.add(numIndices);
            }
            if (!maxVals.isEmpty() && Collections.min(maxVals) == numIndices) {
                minWord = word;
            }
        }
        if (maxVals.size() == 0) {
            return 0;
        }
        for (int i = 0; i < minWord.length(); i++) {
            if (minWord.charAt(i) == lastGuess) {
                masterSb.setCharAt(i, lastGuess);
            }
        }

        return Collections.min(maxVals);
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
        this.myMap = new HashMap<>();
        //make a function to run evil algorithm
        return myDictionary;
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
            return RunFirstPriority(myCandidateMap);
        }
        return null;
    }

    public Set<String> RunFirstPriority(Map<Pattern, Set<String>> mapCandidate) {
        Map<Pattern, Set<String>> myMapCopy = mapCandidate;
        Map<Pattern, Set<String>> myCandidateMap = new HashMap<>();
        for(Map.Entry<Pattern, Set<String>> entry : myMapCopy.entrySet()){
            Pattern myPattern = entry.getKey();
            Set<String> myStrings = entry.getValue();
            if(myPattern.ReturnIndices().size() == 0) {
                myCandidateMap.put(myPattern, myStrings);
            }
        }
        if (myCandidateMap.size() == 0) {
            return RunSecondPriority(mapCandidate);
        }
        else if (myCandidateMap.size() == 1) {
            for (Set<String> myStringSet : myCandidateMap.values()) {
                //this.myMap = myCandidateMap;
                return myStringSet;
            }
        }
        else if (myCandidateMap.size() > 1) {
            return RunSecondPriority(myCandidateMap);
        }
        return null;
    }

    public Set<String> RunSecondPriority(Map<Pattern, Set<String>> mapCandidate) {
        Map<Pattern, Set<String>> mapCandidateNew = new HashMap<>();
        int maxValue = 0;
        //choose the one with the fewest guessed letters
        //which means choose the one with the biggest size after subtracting guessed letters

        for (Map.Entry<Pattern, Set<String>> entry : mapCandidate.entrySet()) {
            Pattern myPattern = entry.getKey();
            Set<String> myStrings = entry.getValue();
            int numUnguessedLetters = this.masterSb.length() - myPattern.ReturnIndices().size();
            if(maxValue < numUnguessedLetters) {
                maxValue = numUnguessedLetters;
                mapCandidateNew = new HashMap<>();
                mapCandidateNew.put(myPattern, myStrings);
            }
            else if (maxValue > numUnguessedLetters) {
                //do nothing
            }
            else if (maxValue == numUnguessedLetters) {
                mapCandidateNew.put(myPattern, myStrings);
            }
        }
        if (mapCandidateNew.size() == 0) {
            return RunThirdPriority(mapCandidate);
        }
        else if (mapCandidateNew.size() == 1) {
            for (Set<String> myStringSet : mapCandidateNew.values()) {
                return myStringSet;
            }
        }
        else if(mapCandidateNew.size() >= 2) {
            return RunThirdPriority(mapCandidateNew);
        }
        return null;
    }

    public Set<String> RunThirdPriority(Map<Pattern, Set<String>> mapCandidate) {
        Map<Pattern, Set<String>> mapCandidateNew = new HashMap<>();
        int maxValue = 0;
        for (Map.Entry<Pattern, Set<String>> entry : mapCandidate.entrySet()) {
            Pattern myPattern = entry.getKey();
            Set<String> myStrings = entry.getValue();
            ArrayList<Integer> myIndices = myPattern.ReturnIndices();
            for (Integer index : myIndices) {
                if (maxValue < index) {
                    maxValue = index;
                    mapCandidateNew = new HashMap<>();
                    mapCandidateNew.put(myPattern, myStrings);
                }
                else if (maxValue > index) {
                    //do nothing
                }
                else if (maxValue == index) {
                    mapCandidateNew.put(myPattern, myStrings);
                }
            }
            ArrayList<Integer> maxValArray = new ArrayList<>();
            maxValArray.add(maxValue);
            if (mapCandidateNew.size() == 0) {
                return RunFourthPriority(mapCandidate, maxValArray);
            }
            else if (mapCandidateNew.size() == 1) {
                for (Set<String> myStringSet : mapCandidateNew.values()) {
                    return myStringSet;
                }
            }
            else if(mapCandidateNew.size() >= 2) {
                return RunFourthPriority(mapCandidateNew, maxValArray);
            }

        }
        return null;
    }

    public Set<String> RunFourthPriority(Map<Pattern, Set<String>> mapCandidate, ArrayList<Integer> avoidVals) {
        Map<Pattern, Set<String>> mapCandidateNew = new HashMap<>();
        int maxValue = 0;
        for (Map.Entry<Pattern, Set<String>> entry : mapCandidate.entrySet()) {
            Pattern myPattern = entry.getKey();
            Set<String> myStrings = entry.getValue();
            ArrayList<Integer> myIndices = myPattern.ReturnIndices();
            myIndices.removeAll(avoidVals);
            for (Integer index : myIndices) {
                if (maxValue < index) {
                    maxValue = index;
                    mapCandidateNew = new HashMap<>();
                    mapCandidateNew.put(myPattern, myStrings);
                }
                else if (maxValue > index) {
                    //do nothing
                }
                else if (maxValue == index) {
                    mapCandidateNew.put(myPattern, myStrings);
                }
            }
            avoidVals.add(maxValue);
            if (mapCandidateNew.size() == 1) {
                for (Set<String> myStringSet : mapCandidateNew.values()) {
                    return myStringSet;
                }
            }
            else if(mapCandidateNew.size() >= 2) {
                return RunFourthPriority(mapCandidateNew, avoidVals);
            }
        }
        return null;
    }

    public void DeleteGame() {
        myDictionary.clear();
        usedGuesses.clear();
        myMap.clear();
        masterSb.setLength(0);
        done = false;
        //Run This to clear everything from the last game
    }


    public String PrintUsedGuesses() {
        StringBuilder OSS = new StringBuilder();
        for(Character letter: usedGuesses) {
            OSS.append(letter + " ");
        }
        return OSS.toString();
    }

    public char PrintTurnInfo() {
        System.out.println("You have " + numGuessesLeft + " guesses left");
        System.out.println("Used letters: " + PrintUsedGuesses());//used letters go here
        System.out.println("Word: " + masterSb.toString()); //Need the dashes here
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
            if (response.equals("") || !Character.isLetter(myGuess) || response.length() != 1) {
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
