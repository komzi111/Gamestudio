package sk.tsystems.gamestudio.consoleui;

import java.util.Scanner;

import sk.tsystems.gamestudio.game.guessnumber.core.GuessMain;
import sk.tsystems.gamestudio.game.minesweeper.Minesweeper;
import sk.tsystems.gamestudio.game.npuzzle.core.Puzzle;

public class Menu {
	private static final String[] GAMES = { "Minesweeper", "GuessNumber", "nPuzzle" };
	String user= System.getProperty("user.name");
	
	private Scanner scanner = new Scanner(System.in);
	
	private String readLine() {
		return scanner.nextLine();
	}

	public void run() {
		show();
		processInput();

	}

	private void show() {
		System.out.println("Welcome in GAMESTUDIO");
		System.out.println("---------------------------");
		System.out.println("USER:" + user );
		System.out.println("Choose a game: ");

		for (int i = 1; i <= GAMES.length; i++) {
			System.out.println(i + ". " + GAMES[i - 1]);
		}
	}

	public void processInput() {
		System.out.println("Insert number of game or push <X> to EXIT: ");
		
		while (true) {
			try {
				String endapp = readLine().trim().toUpperCase();
				if(endapp.equals("X")) {
					System.err.println("End of application -- BYE BYE");
					System.exit(0);
				}
				
				int number = Integer.parseInt(endapp);
				switch (number) {
				case 1:
					new Minesweeper();
					break;
				case 2:
					
					new GuessMain();
					break;
				case 3:
					new Puzzle();
					break;
				default:
					System.err.println("Game doesn't exist");
					break;
				}
				
			} catch (Exception e) {
				System.err.println("Wrong input");
			}
		}
		
	}
	
}
