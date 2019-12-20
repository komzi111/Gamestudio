package sk.tsystems.gamestudio.game.npuzzle.core;

import sk.tsystems.gamestudio.game.npuzzle.consoleui.*;

public class Puzzle {
	private UserInterface userInterface;

	public Puzzle() {
		userInterface = new ConsoleUI();
		Field field = new Field(4, 4);
		userInterface.newGameStarted(field);
	}

	public static void main(String[] args) {

		new Puzzle();

	}

}
