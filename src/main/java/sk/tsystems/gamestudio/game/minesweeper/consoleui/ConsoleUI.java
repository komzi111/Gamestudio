package sk.tsystems.gamestudio.game.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sk.tsystems.gamestudio.consoleui.Menu;
import sk.tsystems.gamestudio.game.minesweeper.UserInterface;
import sk.tsystems.gamestudio.game.minesweeper.core.*;
//import minesweeper.core.Clue;
//import minesweeper.core.Field;
//import minesweeper.core.GameState;
//import minesweeper.core.Mine;
//import minesweeper.core.Tile;
//import minesweeper.core.Tile.State;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Starts the game.
	 * 
	 * @param field field of mines and clues
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();

			if (field.getState() == GameState.SOLVED) {
				System.out.println("You won");
				System.exit(0);
			}  if (field.getState() == GameState.FAILED) {
				System.err.println("Game over");
				System.exit(0);
			}

			processInput();

		} while (true);
	}

	/**
	 * Updates user interface - prints the field.
	 */
	@Override
	public void update() {

		for (int i = 0; i < field.getRowCount(); i++) {

			if (i == 0) {
				System.out.print("    0 1 2 3 4 5 6 7 8 \n");
			}

			System.out.printf("%c  ", 'A' + i);

			for (int j = 0; j < field.getColumnCount(); j++) {
				Tile tile = field.getTile(i, j);

				if (tile.getState() == Tile.State.CLOSED) {
					System.out.print(" -");
				}
				if (tile.getState() == Tile.State.OPEN) {
					if (tile instanceof Clue) {
						Clue clue = (Clue) tile;
						System.out.print(" " + clue.getValue() + "");
					} 
					else if (tile instanceof Mine) {
						Mine mine = (Mine) tile;
						System.out.print(" X");
					}
				}

				if (tile.getState() == Tile.State.MARKED) {
					System.out.print(" M");
				}

			}
			System.out.println();

		}

		System.out.print("   ");
		
		
		
		
		
//		for (int i = 0; i < field.getColumnCount(); i++) {
//			System.out.printf("%d ", 0 + i);
//
//		}
//		System.out.println();
//
//		for (int i = 0; i < field.getRowCount(); i++) {
//			System.out.printf("%c ", 'A' + i);
//			for (int j = 0; j < field.getColumnCount(); j++) {
//				if (field.getTile(i, j).getState() == Tile.State.CLOSED) {
//					System.out.print(" -");
//				} else if (field.getTile(i, j).getState() == Tile.State.MARKED) {
//					System.out.print(" M");
//				} else {
//					if (field.getTile(i, j) instanceof Mine) {
//						System.out.print(" X");
//					} else if (field.getTile(i, j) instanceof Clue) {
//						Clue clues = (Clue) field.getTile(i, j);
//						System.out.print(" " + clues.getValue());
//					}
//				}
//			}
//			System.out.println();
//		}

	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 * 
	 * @throws IOException
	 */
	private void processInput() {

		System.out.print("Please enter your selection <X> Exit, <MA1> MARK, <OB4> OPEN :");

		try {

			handleInput(readLine());
		} catch (WrongFormatException ex) {
			ex.getMessage();
		}

	}

	void handleInput(String input) throws WrongFormatException {

		String answer = input.trim().toUpperCase();

		Pattern pat = Pattern.compile("(M|O)([A-Z])([0-9])");
		Matcher matcher = pat.matcher(answer);

		if (answer.equals("X")) {
			var menu = new Menu();
			menu.run();
			
		}

		if (!matcher.matches()) {
			throw new WrongFormatException();
		}
		char c = matcher.group(2).charAt(0);
		
		int axisX = (int) c - 65;
		int axisY = Integer.parseInt(matcher.group(3));

		if (matcher.group(1).equals("M")) {
			field.markTile(axisX, axisY);
		}
		if (matcher.group(1).equals("O")) {
			field.openTile(axisX, axisY);
		}
	}

}
