package hangman;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, GuessAlreadyMadeException {

        try {
            String dictionaryFile = args[0];
            Integer wordLength = Integer.parseInt(args[1]);
            Integer guesses = Integer.parseInt(args[2]);
            IEvilHangmanGame myGame = new EvilHangmanGame();
            ((EvilHangmanGame) myGame).SetNumGuesses(guesses);
            myGame.startGame(new File(dictionaryFile), wordLength);
            while (!((EvilHangmanGame) myGame).GetDoneStatus()) {
                char guess = ((EvilHangmanGame) myGame).PrintTurnInfo();

                ((EvilHangmanGame) myGame).SetLastGuess(guess);
                myGame.makeGuess(guess);
                Integer numFound = ((EvilHangmanGame) myGame).FoundLetter();
                if (numFound > 0) {
                    if (((EvilHangmanGame) myGame).GuessedCorrectWord()) {
                        System.out.println("You Win!");
                        System.out.println("The word was: " + ((EvilHangmanGame) myGame).GetStringBuilder().toString());
                        return;
                    } else {
                        System.out.println("Yes, there is " + numFound + " " + guess);
                    }
                } else {
                    ((EvilHangmanGame) myGame).DecreaseNumGuesses();
                    if (((EvilHangmanGame) myGame).GetNumGuessesLeft() == 0) {
                        System.out.println("You lose!");
                        System.out.println("The word was: " + ((EvilHangmanGame) myGame).PrintLostPrompt());
                        ((EvilHangmanGame) myGame).SetDoneStatus();
                    } else {
                        System.out.println("Sorry, there are no " + guess + "'s");
                    }
                }
            }
            ((EvilHangmanGame) myGame).DeleteGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
