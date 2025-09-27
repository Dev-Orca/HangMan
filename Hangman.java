import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.util.Objects;

public class Hangman extends ConsoleProgram {
	
/** random number generator, to generate a random word */
	private RandomGenerator rgen = new RandomGenerator();
	
/** canvas */
	private HangmanCanvas canvas;
	
	private String alreadyGuessedChars = "";
	private String unrevealedWord = "";
	private boolean gameOver = false;
	private int lives = 8;
	private boolean exitGame = false;

/** ADDS CANVAS FOR USER */
	public void init() { 
		canvas = new HangmanCanvas(); 
		add(canvas); 
		
	} 

/** the entire game */
    public void run() {    	
    	while(exitGame == false){
    		println("Welcome To Hangman");
    		String word = getTheWord().toUpperCase();
    		setUp(word);
        	playGame(word);
        	askForReplay();
    	}
    	
    	
	}

/** adds canvases essentials and forms unrevealed word */
	private void setUp(String word) {
		canvas.run();
    	formUnrevealedWord(word);
	}

/** asks for replay and replays if user wants to replay */
	private void askForReplay() {
		String wantsToPlayAgain = readLine("Type YES if you wish to play again: ");
		checkIfUserSaidYes(wantsToPlayAgain);
	}

/** replays if user said yes to replay */
	private void checkIfUserSaidYes(String wantsToPlayAgain) {
		if(!isTheSameString(wantsToPlayAgain.toUpperCase(), "YES")){
			exitGame = true;
		}
		else{
			restartGame();
		}
		
	}

/** checks if 2 inputed strings are the same */
	private boolean isTheSameString(String input, String yes) {
		if(input.length() != yes.length()){
			return false;
		}
		for(int i = 0; i < input.length(); i ++)
		{
			if(input.charAt(i) != yes.charAt(i)){
				return false;
			}
		}
		return true;
	}

/** restarts game */
	private void restartGame() {
		alreadyGuessedChars = "";
		unrevealedWord = "";
		gameOver = false;
		lives = 8;
		canvas.reset();
	}

	
/** forms unrevealed word */
	private void formUnrevealedWord(String word) {
		for(int i = 0; i < word.length(); i++){
			unrevealedWord += "—";
			
		}
		canvas.displayWord(unrevealedWord, Color.WHITE);
	}

/** plays the game once */
	private void playGame(String word) {
		while(!gameOver){
			char guessedChar = getUsersChar();
			checkIfCharIsInWord(word, guessedChar);
			checkForGameEndingConditions(word);
		}
		
	}

/** checks For Game Ending Conditions */
	private void checkForGameEndingConditions(String word) {
		checkForWin(word);
		checkForLoss(word);
		
	}

/** checks For Loss */
	private void checkForLoss(String word) {
		if(lives == 0){
			onLoss(word);
		}
		
	}

/** prints a message(on console and canvass) and ends thee game on loss */
	private void onLoss(String word) {
		gameOver = true;
		println("you are so bad at this game, YOU LOST, the word was: " + word);
		
	}

/** checks For Win */
	private void checkForWin(String word) {
		if(wordIsGuessed(word)){
			onWin();
		}
		
	}

/** prints a message (on console and canvass) and ends thee game on win */
	private void onWin() {
		gameOver = true;
		println("You Win");
		canvas.updateCanvasOnWin();
	}

/** returns a boolean, depending on if the word is guessed or not */
	private boolean wordIsGuessed(String word) {
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) != unrevealedWord.charAt(i)){
				return false;
			}
		}
		return true;
	}

/** checks if the inputed char is in the word and does needed actions, depending on it */
	private void checkIfCharIsInWord(String word, char guessedChar) {
		boolean charIsFound = false;
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == guessedChar && !charIsAlreadyGuessed(guessedChar)){
				newCharIsCorrectlyGuessed(word, guessedChar);
				charIsFound = true;
				break;
				
			}
			else if(word.charAt(i) == guessedChar){
				charIsFound = true;
				break;
			}
		}
		if(!charIsFound){
			onIncorrectGuess(guessedChar, word);
		}
	}

/** prints a message, subtracts from lives and updates the canvas on an incorrect guess */
	private void onIncorrectGuess(char guessedChar, String word) {
		lives--;
		printMessageDependingOnLives();
		canvas.noteIncorrectGuess(guessedChar, lives, word);
		canvas.updateLifeCounter(lives);
	}

/** prints message, reveals guessed characters and updates canvas on a correctly guessed letter */
	private void newCharIsCorrectlyGuessed(String word, char guessedChar) {
		revealGuessedCharEverywhere(guessedChar, word);
		println("that guess was correct.");
		println(unrevealedWord);
		canvas.displayWord(unrevealedWord, Color.WHITE);

	}
	
/** prints Message Depending On the remaining Lives */
	private void printMessageDependingOnLives() {
		if(lives == 1){
			println("that guess was totally incorrect, u have only " + lives + 
					" life left, USE YOUR BRAIN MORE!!, its your last chance");
		}
		else if (lives != 0){
			println("that guess was totally incorrect, u have " + lives + 
					" lives left, USE YOUR BRAIN MORE!!");
		}
		
	}

/** reveals Guessed Chars Everywhere in the word*/
	private void revealGuessedCharEverywhere(char guessedChar, String word) {
		for(int i = 0;  i < word.length(); i ++){
			if(word.charAt(i) == guessedChar){
				revealGuessedChar(i, guessedChar);	
			}
		}
		
	}

/** reveals Guessed Char */
	private void revealGuessedChar(int indexOfGuessedChar, char guessedChar) {
		String newUnrevealedWord = "";
		for(int i = 0; i < unrevealedWord.length(); i++){
			if(i == indexOfGuessedChar){
				newUnrevealedWord += guessedChar;
			}
			else{
				newUnrevealedWord += unrevealedWord.charAt(i);
			}
		}
		unrevealedWord = newUnrevealedWord;
		alreadyGuessedChars += guessedChar;
	}

/** checks if a particular letter is already guessed */
	private boolean charIsAlreadyGuessed(char guessedChar) {
		for(int i = 0; i < alreadyGuessedChars.length(); i++){
			if(alreadyGuessedChars.charAt(i) == guessedChar){
				return true;
			}
		}
		return false;
	}

/** gets the letter that the user wants to guess */
	private char getUsersChar() {
		while(true){
			String guessedCharInStringForm = readLine("enter a valid guess: ");
			guessedCharInStringForm = guessedCharInStringForm.toUpperCase();
			if('A' <= guessedCharInStringForm.charAt(0) && 'Z' >= guessedCharInStringForm.charAt(0) && 
					guessedCharInStringForm.length() == 1){
				return guessedCharInStringForm.charAt(0);
			}
				
		}
	}

/** gets a random word from a file */
	private String getTheWord() {
		HangmanLexicon words = new HangmanLexicon();
    	return words.getWord(rgen.nextInt(0, words.getWordCount() - 1));
	}

}
