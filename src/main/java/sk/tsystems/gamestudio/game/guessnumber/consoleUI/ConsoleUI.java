package sk.tsystems.gamestudio.game.guessnumber.consoleUI;

import java.util.Scanner;

import sk.tsystems.gamestudio.consoleui.Menu;

public class ConsoleUI {
	
	
	private Scanner scanner = new Scanner(System.in);
	
	public void play(int guess, int randomNumber) {
		do {
			try {
				System.out.print("Vloz nahodne cislo od 1 do 10 alebo stlac <X> pre KONIEC HRY: ");
				String finish = scanner.nextLine().trim().toUpperCase();
				
				if(finish.equals("X")) {
					var menu = new Menu();
					menu.run();
				}
				guess =  Integer.parseInt(finish);
				
				if (guess == randomNumber)
					
					System.out.println("Uhadol si, gratulujem");
				
				else if (guess < randomNumber)
					
					System.out.println("Tvoj odhad je mensi ako spravne cislo.");
				
				else if (guess > randomNumber)
					
					System.out.println("Tvoja odhad je vacsi ako spravne cislo.");
			} catch (NumberFormatException e) {
				System.err.println("You put again an input");
			}
		} while (guess != randomNumber);
		
	}
	

	
	
	
	
	
	

}
