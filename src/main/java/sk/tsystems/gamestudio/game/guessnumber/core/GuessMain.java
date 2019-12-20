package sk.tsystems.gamestudio.game.guessnumber.core;

import java.util.Random;

import sk.tsystems.gamestudio.game.guessnumber.consoleUI.*;

public class GuessMain {
	
	Random random = new Random();
	int guess = 0;
	int randomNumber = random.nextInt(10)+1;
	
	/**
     * Constructor.
     */
    public GuessMain() {

    	var console = new ConsoleUI();
    	console.play(guess,randomNumber);
    }

    
	public static void main(String[] args) {
		
		new GuessMain();
		
		
	}

}
