import java.awt.Color;
import java.awt.Font;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class HangmanCanvas extends GCanvas {
	
/** width and height of the canvas */
	private static final int WIDTH = 376;
	private static final int HEIGHT = 464;

/** unrevealed word */
	private GLabel unrevealedWord = new GLabel("");
	
/** incorrect chars */
	private String incorrectChars = "";
	
/** incorrect chars as glabel */
	private GLabel incorrectLetters = new GLabel(""); 	
	
/** remaining lives */
	private GLabel remainingLives = new GLabel("");
	
/** updates canvas on win */
	public void updateCanvasOnWin(){
		displayWord(unrevealedWord.getLabel(), Color.GREEN);
		coolDeleteAnimation();
		addWinText();
	}
	
/** adds text on win on canvas */
	private void addWinText() {
		GLabel youWinMessage = new GLabel("YOU WIN!", 60, 240);
		youWinMessage.setFont(new Font("Serif", Font.BOLD, 50));
		youWinMessage.setColor(Color.GREEN);
		add(youWinMessage);
		
	}

/** does a human remove animation */
	private void coolDeleteAnimation() {	
		for(int i = SCAFFOLD_HEIGHT - ROPE_LENGTH; i > -1; i--){
			GRect deleter = new GRect(WIDTH / 2 - BEAM_LENGTH + 1,
					i + 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH, BEAM_LENGTH * 2, 2);
			deleter.setFilled(true);
			deleter.setFillColor(Color.BLACK);
			add(deleter);
			deleter.pause(5);
		}
	}

/** updates canvas on loss */
	private void updateCanvasOnLoss(String word){
		displayWord(word, Color.RED);
		messageOnLoss();
	}
	
/** prints a message when a game is lost */
	private void messageOnLoss() {
		GLabel youLostMessage = new GLabel("YOU LOST!", 60, 240);
		youLostMessage.setFont(new Font("Serif", Font.BOLD, 50));
		youLostMessage.setColor(Color.RED);
		add(youLostMessage);
	}

/** Resets the display so that only the scaffold appears */
	public void reset() {
		GRect resetRect = new GRect(WIDTH, HEIGHT);
		resetRect.setFilled(true);
		resetRect.setFillColor(Color.BLACK);
		resetRect.setColor(Color.BLACK);
		add(resetRect);
	}

/** this is called at the start of each round, adds essential components to canvas */
	public void run(){
		incorrectChars = "";
		reset();
		addFullScaffold();
		addLifeCounter();
	}


/** adds all the needed components for a full scaffold */
	private void addFullScaffold() {
		addScaffold();
		addBeam();
		addRope();
		
	}

/** adds a live counter at the top right corner*/
	private void addLifeCounter() {
		remainingLives = new GLabel("Lives: 8", 300,20);
		remainingLives.setColor(Color.BLUE);
		remainingLives.setFont(new Font("Serif", Font.BOLD, 20));
		add(remainingLives);
	}
	
/** updates life counter */
	public void updateLifeCounter(int lives){
		remove(remainingLives);
		remainingLives = new GLabel("Lives: " + lives, 300,20);
		remainingLives.setFont(new Font("Serif", Font.BOLD, 20));
		changeColorDependingOnLives(lives, remainingLives);
		add(remainingLives);
	}

/** changes the color of an object depending on lives left */
	private void changeColorDependingOnLives(int lives, GObject object) {
		if(lives == 7){
			object.setColor(Color.BLUE);
		}
		if(lives == 6 || lives == 5){
			object.setColor(Color.GREEN);
		}
		if(lives == 4){
			object.setColor(Color.YELLOW);
		}
		if(lives == 2 || lives == 3){
			object.setColor(Color.ORANGE);
		}
		if(lives == 0 || lives == 1){
			object.setColor(Color.RED);
		}
		
	}

/** adds rope */
	private void addRope() {
		GLine rope = new GLine(WIDTH / 2 ,385 - SCAFFOLD_HEIGHT, 
				WIDTH / 2 ,385 - SCAFFOLD_HEIGHT + ROPE_LENGTH);	
		rope.setColor(Color.LIGHT_GRAY  );
		add(rope);
		
	}

/** adds beam */
	private void addBeam() {
		GLine beam = new GLine(WIDTH / 2 - BEAM_LENGTH ,385 - SCAFFOLD_HEIGHT, 
				WIDTH / 2 ,385 - SCAFFOLD_HEIGHT);
		beam.setColor(Color.LIGHT_GRAY  );
		add(beam);
		
	}

/** adds scaffold */
	private void addScaffold() {
		GLine scaffold = new GLine(WIDTH / 2 - BEAM_LENGTH, 385 ,
				WIDTH / 2 - BEAM_LENGTH ,385 - SCAFFOLD_HEIGHT);
		scaffold.setColor(Color.LIGHT_GRAY  );
		add(scaffold);
			
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word, Color color) {
		remove(unrevealedWord);
		unrevealedWord = new GLabel(word, 24 , getHeight() - 44);
		unrevealedWord.setColor(color);
		unrevealedWord.setFont(new Font("Serif", Font.BOLD, 20));
		add(unrevealedWord);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter, int amountOfLives, String word) {
		updateDrawingAndTheWord(amountOfLives, word);
		updateIncorrectGuesses(letter);
	}

/** updates drawing based on the amount of lives left */
	private void updateDrawingAndTheWord(int amountOfLives, String word) {
		if(amountOfLives == 7){
			addHead();
		}
		if(amountOfLives == 6){
			addBody();
		}
		if(amountOfLives == 5){
			addLeftArm();
		}
		if(amountOfLives == 4){
			addRightArm();
		}
		if(amountOfLives == 3){
			addLeftLegAndHip();
		}
		if(amountOfLives == 2){
			addRightLegAndHip();
		}
		if(amountOfLives == 1){
			addLeftFoot();
		}
		if(amountOfLives == 0){
			addRightFoot();
			updateCanvasOnLoss(word);
		}
	
	}

/** adds right foot */
	private void addRightFoot() {
		GLine rightFoot = new GLine(WIDTH / 2 + HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				WIDTH / 2 + HIP_WIDTH  + FOOT_LENGTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		rightFoot.setColor(Color.WHITE);
		add(rightFoot);
		animate(rightFoot);
	}

/** adds left foot */
	private void addLeftFoot() {
		GLine leftFoot = new GLine(WIDTH / 2 - HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				WIDTH / 2 - HIP_WIDTH  - FOOT_LENGTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		leftFoot.setColor(Color.WHITE);
		add(leftFoot);
		animate(leftFoot);
		
	}

/** adds right leg and right hip */
	private void addRightLegAndHip() {
		addRightHip();
		addRightLeg();
		
	}

/** adds right hip */
	private void addRightHip() {
		GLine rightHip = new GLine(WIDTH / 2 + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				WIDTH / 2 + HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		rightHip.setColor(Color.WHITE);
		add(rightHip);
		animate(rightHip);
	}

/** adds right leg */
	private void addRightLeg() {
		GLine rightLeg = new GLine(WIDTH / 2 + HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				WIDTH / 2 + HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		rightLeg.setColor(Color.WHITE);
		add(rightLeg);
		animate(rightLeg);
	}

/** adds left leg and left hip */
	private void addLeftLegAndHip() {
		addLeftHip();
		addLeftLeg();
	}

/** adds left leg */
	private void addLeftLeg() {
		GLine leftLeg = new GLine(WIDTH / 2 - HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				WIDTH / 2 - HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		leftLeg.setColor(Color.WHITE);
		add(leftLeg);
		animate(leftLeg);
	}

/** adds left hip */
	private void addLeftHip() {
		GLine leftHip = new GLine(WIDTH / 2 + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				WIDTH / 2 - HIP_WIDTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		leftHip.setColor(Color.WHITE);
		add(leftHip);
		animate(leftHip);
	}

/** adds right arm */
	private void addRightArm() {
		addRightUpperArm();
		addRightLowerArm();
		
	}

/** adds right lower arm */
	private void addRightLowerArm() {
		GLine rightLowerArm = new GLine(WIDTH / 2 + UPPER_ARM_LENGTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				WIDTH / 2 + UPPER_ARM_LENGTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		rightLowerArm.setColor(Color.WHITE);
		add(rightLowerArm);
		animate(rightLowerArm);
	}

/** adds right upper arm */
	private void addRightUpperArm() {
		GLine rightUpperArm = new GLine(WIDTH / 2 + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				WIDTH / 2 + UPPER_ARM_LENGTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		rightUpperArm.setColor(Color.WHITE);
		add(rightUpperArm);
		animate(rightUpperArm);
	}

/** adds left arm */
	private void addLeftArm() {
		addLeftUpperArm();
		addLeftLowerArm();
	}

/** adds left lower arm */
	private void addLeftLowerArm() {
		GLine leftLowerArm = new GLine(WIDTH / 2 - UPPER_ARM_LENGTH  + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				WIDTH / 2 - UPPER_ARM_LENGTH + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		leftLowerArm.setColor(Color.WHITE);
		add(leftLowerArm);
		animate(leftLowerArm);
	}

/** adds left upper arm */
	private void addLeftUpperArm() {
		GLine leftUpperArm = new GLine(WIDTH / 2  + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				WIDTH / 2 - UPPER_ARM_LENGTH  + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		leftUpperArm.setColor(Color.WHITE);
		add(leftUpperArm);
		animate(leftUpperArm);
	}

/** adds BODY */
	private void addBody() {
		GLine body = new GLine(WIDTH / 2  + OFFSET, 385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS, WIDTH / 2  + OFFSET,
				385 - SCAFFOLD_HEIGHT + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		body.setColor(Color.WHITE);
		add(body);
		animate(body);
	}

/** adds head */
	private void addHead() {
		GOval head = new GOval(WIDTH / 2 - HEAD_RADIUS + OFFSET,
				385 - SCAFFOLD_HEIGHT + ROPE_LENGTH, HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		head.setColor(Color.WHITE);
		add(head);
		animate(head);
			
	}

/** animates a body part (comes from outside the screen) */
	private void animate(GObject object) {
		double toBePlaced = object.getX() - OFFSET;
		while(toBePlaced < object.getX()){
			object.move(-1, 0);
			object.pause(1);
		}
		
	}

/** updates the list of incorrect letters */
	private void updateIncorrectGuesses(char letter) {
		remove(incorrectLetters);
		getNewIncorreectLetters(letter);
		add(incorrectLetters);
	}

/** updares the list of incorrect letters on the canvas */
	private void getNewIncorreectLetters(char letter) {
		addInIncorrectChars(letter);
		incorrectLetters = new GLabel(incorrectChars, 24, getHeight() - 14);
		incorrectLetters.setFont(new Font("Serif", Font.ITALIC, 14));
		incorrectLetters.setColor(Color.LIGHT_GRAY );
		
	}

/** adds incorrect letter to the list of incorrect letters if needed */
	private void addInIncorrectChars(char letter) {
		boolean alreadyExists = false; 
		
		for(int i = 0; i < incorrectChars.length(); i++){
			if(incorrectChars.charAt(i) == letter){
				alreadyExists = true;
			}
		}
		if(alreadyExists == false){
			incorrectChars += letter;
		}
		
	}

/* offset of the body part animations */
	private static final int OFFSET = 200;
	
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 40;
	private static final int LOWER_ARM_LENGTH = 82;
	private static final int HIP_WIDTH = 20;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 14;

}