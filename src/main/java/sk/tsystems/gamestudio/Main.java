package sk.tsystems.gamestudio;

import java.util.Scanner;

import sk.tsystems.gamestudio.consoleui.Menu;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;

public class Main {

	public static void main(String[] args) {
		
		
		Scanner scanner = new Scanner(System.in);
		
		ScoreService scoreService = new ScoreServiceJDBC();
		
		var menu = new Menu();
		
		menu.run();
		
		
		

		//scoreService.addScore(new Score("marek","npuzzle",15));
		
		
		
		
		
		
	
//		for (Score score : scoreService.getTopScores("npuzzle")) {
//			System.out.println(score.getUsername() + " " + score.getValue());
//		}
		
		
	}

}
