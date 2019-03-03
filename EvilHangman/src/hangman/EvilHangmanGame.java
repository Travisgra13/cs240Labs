package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class EvilHangmanGame implements IEvilHangmanGame{
    private Set<String> myDictionary;
    private Integer numGuessesLeft;
    private TreeSet<Character> usedGuesses;
    private Character lastGuess;
    private Map<Pattern, Set<String>> myMap;
    private StringBuilder masterSb;
    private boolean done;

    public EvilHangmanGame() {
        myDictionary = new LinkedHashSet<>();
        numGuessesLeft = 0;
        usedGuesses = new TreeSet<>();
        myMap = new HashMap<>();
        masterSb = new StringBuilder();
        done = false;
    }

    public Set<String> GetMyDictionary() {
        return myDictionary;
    }
    public void SetNumGuesses(int num) {
        numGuessesLeft = num;
    }
    public Integer GetNumGuessesLeft() {
        return numGuessesLeft;
    }
    public TreeSet<Character> GetUsedGuesses() {
        return usedGuesses;
    }
    public Character GetLastGuess() {
        return lastGuess;
    }
    public void SetLastGuess(Character character) {
        this.lastGuess = character;
    }
    public void DecreaseNumGuesses() {
        numGuessesLeft--;
    }
    public void SetDoneStatus() {
        done = true;
    }
    public Map<Pattern, Set<String>> GetMyMap() {
        return myMap;
    }
    public StringBuilder GetStringBuilder() {
        return masterSb;
    }
    public boolean GetDoneStatus() {
        return done;
    }

    @Override
    public void startGame(File dictionary, int wordLength) {
        try {
            Scanner scanner = new Scanner(dictionary);
            InitializeMasterSb(wordLength);
            while(scanner.hasNext()) {
                scanner.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
                String myWord = scanner.next();
                if (myWord.length() == wordLength && isAlpha(myWord)) {
                    myDictionary.add(myWord.toLowerCase());
                }
            }
            if (myDictionary.size() == 0) {
                System.out.println("File is without String Tokens of Specified Size");
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
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
       // ArrayList<Integer> locVals = new ArrayList<>();
        String minWord = "";
        for(String word : myDictionary) {
            Integer numIndices = 0;
            if (word.indexOf(lastGuess) >= 0){
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == lastGuess) {
                        numIndices++;
                        //locVals.add(i);
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
                } else if (maxValue > index) {
                    //do nothing
                } else if (maxValue == index) {
                    mapCandidateNew.put(myPattern, myStrings);
                }
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
                } else if (maxValue > index) {
                    //do nothing
                } else if (maxValue == index) {
                    mapCandidateNew.put(myPattern, myStrings);
                }
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

    public char PrintTurnInfo() throws GuessAlreadyMadeException{
        System.out.println("You have " + numGuessesLeft + " guesses left");
        System.out.println("Used letters: " + PrintUsedGuesses());//used letters go here
        System.out.println("Word: " + masterSb.toString()); //Need the dashes here
        System.out.println("Enter guess: ");
        String response = "";
        boolean validInput = false;
        Character myGuess = null;

       /* while (!validInput) {
            try {
               Scanner input = new Scanner(System.in);
                response = input.nextLine();
                //enter response into repos
                response = response.toLowerCase();
                if (response.isEmpty()) {
                    System.out.println("Invalid Input");
                    continue;
                }
                //check what happens if it is an empty string
                myGuess = response.charAt(0);
                if (response.equals("") || !Character.isLetter(myGuess) || response.length() != 1) {
                    System.out.println("Invalid Input");
                    continue;
                }
                else if (usedGuesses.contains(myGuess)) {
                    throw new GuessAlreadyMadeException();
                    //System.out.println("You already used that letter");
                    //figure out how to throw the exception
                } else {
                    validInput = true;
                }
            }catch (GuessAlreadyMadeException ex) {
                System.out.println("You already used that letter");
            }
        }
        usedGuesses.add(myGuess);
        return myGuess;*/
        //sorry or Yes there is numLetters of char
    }
}
