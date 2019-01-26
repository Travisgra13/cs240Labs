package hangman;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, GuessAlreadyMadeException {
        String dictionaryFile = args[0];
        Integer wordLength = Integer.parseInt(args[1]);
        Integer guesses = Integer.parseInt(args[2]);
        IEvilHangmanGame myGame = new EvilHangmanGame(guesses);
        myGame.startGame(new File(dictionaryFile), wordLength);
        ((EvilHangmanGame) myGame).DeleteGame();
    }
}
