package spell;
import spell.ISpellCorrector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    private  ITrie itrie = new Trie();
    private ArrayList<Pair<String, Integer>> candidateWords = new ArrayList<Pair<String, Integer>>();
    private ArrayList<String> deleteList = new ArrayList<>();
    private ArrayList<String> transList = new ArrayList<>();
    private ArrayList<String> alterList = new ArrayList<>();
    private ArrayList<String> insertList = new ArrayList<>();
    private Set <String> mySet = new LinkedHashSet<>();

    public void useDictionary(String dictionaryFileName) throws IOException {
        Scanner sc = new Scanner(new File(dictionaryFileName));
        while (sc.hasNext()) {
            sc.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
            String word = sc.next();
            word = word.toLowerCase();
            itrie.add(word);
        }
    }
    public String suggestSimilarWord(String inputWord) {
        if (inputWord.equals("")) {
            return null;
        }
        /*ITrie test = new Trie();
        test.add("cares");
        test.add("caress");
        test.add("car");
        test.add("baboon");
        System.out.println(test.toString());
        System.out.println(itrie.toString());*/
       inputWord = inputWord.toLowerCase();
            if (itrie.find(inputWord) != null) {
                return inputWord;
            }
        try {
            if (!(inputWord.length() < 2)) {
                DeletionDistance(inputWord, false);
            }
            TranspositionDistance(inputWord, false);
            AlterationDistance(inputWord, false);
            InsertionDistance(inputWord, false);
            if (candidateWords.size() == 0) {
                int deleteListSize = deleteList.size();
                int transListSize = transList.size();
                int alterListSize = alterList.size();
                int insertListSIze = insertList.size();
                addVecs();
                for (String word : mySet) {
                    if (!(word.length() < 2)) {
                            DeletionDistance(word, true);
                    }
                    TranspositionDistance(word, true);
                    AlterationDistance(word, true);
                    InsertionDistance(word, true);
                }

            }
            return FindMostSimilar();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addVecs() {
        for (int i = 0; i < deleteList.size(); i++) {
            mySet.add(deleteList.get(i));
        }
        for (int j = 0; j < transList.size(); j++) {
            mySet.add(transList.get(j));
        }
        for (int k = 0; k < alterList.size(); k++) {
            mySet.add(alterList.get(k));

        }
        for (int l = 0; l < insertList.size(); l++) {
            mySet.add(insertList.get(l));
        }
    }


    public void DeletionDistance(String inputWord, boolean secTime) {

        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder sb = new StringBuilder(inputWord);
            sb.deleteCharAt(i);
            ITrie.INode myWord = itrie.find(sb.toString());
            if (myWord != null) {
                Pair<String, Integer> myPair = new Pair<String, Integer>(sb.toString(),Integer.valueOf(myWord.getValue()));
                myPair.setL(sb.toString());
                myPair.setR(Integer.valueOf(myWord.getValue()));
                candidateWords.add(myPair);
            }
            else if (myWord == null && secTime == false) {
              deleteList.add(sb.toString());
            }
        }
    }



    public void TranspositionDistance(String inputWord, boolean secTime) {
        for (int i = 0; i < inputWord.length() - 1; i++) {
            StringBuilder sb = new StringBuilder(inputWord);
            sb.setCharAt(i, inputWord.charAt(i + 1));
            sb.setCharAt(i + 1, inputWord.charAt(i));
            ITrie.INode myWord = itrie.find(sb.toString());
            if (myWord != null) {
                Pair<String, Integer> myPair = new Pair<String, Integer>(sb.toString(),Integer.valueOf(myWord.getValue()));
                myPair.setL(sb.toString());
                myPair.setR(Integer.valueOf(myWord.getValue()));
                candidateWords.add(myPair);
            }
            else if (myWord == null && secTime == false) {
                transList.add(sb.toString());
            }
        }
    }


    public void AlterationDistance(String inputWord, boolean secTime) {
        for (int i = 0; i < inputWord.length(); i++) {
            char currLetter = 'a';
            for (int j = 0; j < 26; j++) {
                StringBuilder sb = new StringBuilder(inputWord);
                sb.setCharAt(i, currLetter);
                ITrie.INode myWord = itrie.find(sb.toString());
                if (myWord != null) {
                    Pair<String, Integer> myPair = new Pair<String, Integer>(sb.toString(),Integer.valueOf(myWord.getValue()));
                    myPair.setL(sb.toString());
                    myPair.setR(Integer.valueOf(myWord.getValue()));
                    candidateWords.add(myPair);
                }
                else if (myWord == null && secTime == false) {
                    alterList.add(sb.toString());
                }
                currLetter++;
            }
        }
    }


    public void InsertionDistance(String inputWord, boolean secTime) {
        for (int i = 0; i < inputWord.length(); i++) {
            char currLetter = 'a';
            for (int j = 0; j < 26; j++) {
                StringBuilder sb = new StringBuilder(inputWord);
                sb.insert(i, currLetter);
                ITrie.INode myWord = itrie.find(sb.toString());
                if (myWord != null) {
                    Pair<String, Integer> myPair = new Pair<String, Integer>(sb.toString(),Integer.valueOf(myWord.getValue()));
                    myPair.setL(sb.toString());
                    myPair.setR(Integer.valueOf(myWord.getValue()));
                    candidateWords.add(myPair);
                }
                else if (myWord == null && secTime == false) {
                    insertList.add(sb.toString());
                }
                currLetter++;
            }
        }
    }

    public String FindMostSimilar() {
        ArrayList<String> myWords = new ArrayList<>();
        int maxSize = 0;
        if(candidateWords.size() == 0) {
            return null;
        }
        for (int i = 0; i < candidateWords.size(); i++) {
            String currWord = candidateWords.get(i).getL();
            Integer freqValue = candidateWords.get(i).getR();
            if (maxSize < freqValue) {
                maxSize = freqValue;
                myWords.clear();
                myWords.add(currWord);
            }
            else if (maxSize == freqValue) {
                myWords.add(currWord);
            }
        }
        if (myWords.size() == 0) {
            return null;
        }
       else if (myWords.size() == 1) {
            return myWords.get(0);
        }
        else if (myWords.size() > 1) {
            while(myWords.size() != 1) {
                String currWord = myWords.get(0);
                String nextWord = myWords.get(1);
                int compareToVal = currWord.compareToIgnoreCase(nextWord);
                if (compareToVal == 0) {
                    myWords.remove(currWord);
                }
                else if (compareToVal < 0) {
                    myWords.remove(nextWord);
                }
                else if (compareToVal > 0) {
                    myWords.remove(currWord);
                }
            }
        }



        return myWords.get(0);
    }

}
