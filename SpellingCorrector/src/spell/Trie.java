package spell;
import spell.ITrie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

public class Trie implements  ITrie{
        private Integer nodeCount = 1;
        private Node[] mainNode = new Node[26];
        private Set <String> mySet = new HashSet<String>();
       // private StringBuilder total = new StringBuilder();
        /**
         * Adds the specified word to the trie (if necessary) and increments the word's frequency count
         *
         * @param word The word being added to the trie
         */


        public void add(String word) {
               word = word.toLowerCase();
               recursiveAdd(0, word, mainNode, null);
               mySet.add(word);
        }

    public void recursiveAdd(Integer index, String word, Node[] currNode, Node parentNode) {
            if (word.length() == index) {
                parentNode.incrementFreq();
               return;
            }
            char currLetter = word.charAt(index);
            if (currNode[currLetter - 'a'] != null) {
                parentNode = currNode[currLetter - 'a'];
            }
            else {
                nodeCount++;
                currNode[currLetter - 'a'] = new Node(currLetter);
                parentNode = currNode[currLetter - 'a'];
            }
            currNode = currNode[currLetter - 'a'].getNodes();
            recursiveAdd(index + 1, word, currNode, parentNode);
            return;
        }

        /**
         * Searches the trie for the specified word
         *
         * @return A reference to the trie node that represents the word,
         * 			or null if the word is not in the trie
         */
        public spell.ITrie.INode find(String word) {
            word = word.toLowerCase();
            return recursiveFind(0, word, mainNode, null);
        }

        public spell.ITrie.INode recursiveFind(Integer index, String word, Node[] currNode, Node parentNode) throws NullPointerException{
            char currLetter = word.charAt(index);

            if (index == word.length() - 1) {
                if (currNode[currLetter - 'a'] == null) {
                    return null;
                }
                else if (currNode[currLetter - 'a'].getValue() > 0) {
                    return currNode[currLetter - 'a'];
                }
                else {
                    return null;
                }
            }
            else if (currNode[currLetter - 'a'] == null) {
                return null;
            }
            else {
                parentNode = currNode[currLetter - 'a'];
                currNode = currNode[currLetter - 'a'].getNodes();
                return recursiveFind(index + 1, word, currNode, parentNode);
            }
        }
// equals and tostring must be recursive
        /**
         * Returns the number of unique words in the trie
         *
         * @return The number of unique words in the trie
         */
        public int getWordCount() {
            return mySet.size();
        }

        /**
         * Returns the number of nodes in the trie
         *
         * @return The number of nodes in the trie
         */
        public int getNodeCount() {

            return nodeCount;
        }

        /**
         * The toString specification is as follows:
         * For each word, in alphabetical order:
         * <word>\n
         */

        @Override
         public String toString() {
            StringBuilder total = new StringBuilder();
            for (Node node : mainNode) {
                StringBuilder sb = new StringBuilder();
                if (node != null) {
                    RecursiveToString(node, sb, total);
                }
            }

            return total.toString();
        }

        public void RecursiveToString(Node currNode, StringBuilder word, StringBuilder total) throws NullPointerException {
            Node [] childNodesObject = currNode.getNodes();
            if (currNode.getValue() > 0) {
                word.append(currNode.associatedLetter);
                total.append(word.toString() + "\n");
            }
            else {
                word.append(currNode.associatedLetter);
            }
            for (char y = 'a'; y < 'z'; y++) {
                if (childNodesObject[y - 'a'] != null) {
                    RecursiveToString(childNodesObject[y - 'a'], word, total);
                }

            }
            if (word.length() > 0) {
                word.setLength(word.length() - 1);
            }

            return;
        }

        @Override
        public int hashCode() {

            return mySet.size() * nodeCount * 7;
        }

        @Override
        public boolean equals(Object o) {
            if (o == (null)) {
                return false;
            }

            if (o.getClass() != this.getClass()) {
                return false;
            }
            else {
                Trie newObject = (Trie) o;
                if (newObject.getNodeCount() != this.getNodeCount()) {
                    return false;
                }
                if (newObject.getWordCount() != this.getWordCount()) {
                    return false;
                }
                Boolean done = false;
                for (Integer i = 0; i < 26; i++) {
                    if (newObject.mainNode[i] != null && this.mainNode[i] == null) {
                        return false;
                    }
                    else if (newObject.mainNode[i] == null && this.mainNode[i] != null) {
                        return false;
                    }
                    else if (newObject.mainNode[i] != null && this.mainNode != null) {
                        done = RecursiveEquals(newObject.mainNode[i], this.mainNode[i]);
                        if (done == false) {
                            return done;
                        }
                    }
                }

                //(Trie) that
                //Do something here
                return done;
            }
        }

        public boolean RecursiveEquals(Node currNodeObject, Node thisObject) {
            Node [] childNodesObject = currNodeObject.getNodes();
            Node [] thisNodesObject = thisObject.getNodes();
             if (currNodeObject.getValue() != thisObject.getValue()) {
                return false;
            }
                for (char y = 'a'; y < 'z'; y++) {
                    if (currNodeObject != null && thisObject == null) {
                        return false;
                    }
                    if (currNodeObject == null && thisObject != null) {
                        return false;
                    }
                    if (childNodesObject[y - 'a'] != null) {
                        return RecursiveEquals(childNodesObject[y - 'a'], thisNodesObject[y - 'a']);
                    }

                }

            return true;
        }

        /**
         * Your trie node class should implement the ITrie.INode interface
         */
        public class Node implements  ITrie.INode{

            private char associatedLetter;
            private Integer frequency;
            private Node[] alphabet = new Node[26];
            Node(char letter) {
                associatedLetter = letter;
                frequency = 0;
            }

            void incrementFreq() {
                frequency++;
            }

            char getAssociatedLetter() {
                return associatedLetter;
            }

            Node[] getNodes() { return alphabet;}
            /**
             * Returns the frequency count for the word represented by the node
             *
             * @return The frequency count for the word represented by the node
             */

            public int getValue() {

                return frequency;
            }

    }

}
