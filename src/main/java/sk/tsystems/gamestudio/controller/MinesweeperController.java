package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;



@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {
	
	private Field field;
	private boolean marking;
	private String commentText;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private LoginController loginController;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RatingService ratingService;
	
	@RequestMapping("/minesweeper")
	public String index() {
			field = new Field(9,9,4);
			
			
		
		return "minesweeper";
	}
	
	@RequestMapping("/minesweeper/open")
	public String open(int row,int column) {
		if(field.getState() == GameState.PLAYING)
			if(marking)
				field.markTile(row, column);
			else
				field.openTile(row,column);
		if(field.getState() == GameState.SOLVED && loginController.isLogged()) {
			scoreService.addScore(new Score(loginController.getLoggedPlayer().getName(),"minesweeper",field.getMinesScores()));
		}
		
		return "minesweeper";
	}
	@RequestMapping("/minesweeper/mark")
	public String mark(int row,int column) {
		field.markTile(row,column);
		return "minesweeper";
	}
	@RequestMapping("/minesweeper/change")
	public String change() {
		marking = !marking;
		return "minesweeper";
	}
	
	@RequestMapping("/minesweeper/comment")
	public String comment(String text) {
		commentText = text;
		commentService.addComment(new Comment(loginController.getLoggedPlayer().getName(),"minesweeper", commentText));
		return "minesweeper";
	}
	
	@RequestMapping("/minesweeper/rating")
	public String rate(String rating) {
		try {
			
			int parseRating = Integer.parseInt(rating);
			ratingService.setRating(new Rating(loginController.getLoggedPlayer().getName(),"minesweeper",parseRating));
			
		} catch (Exception e) {
				e.getMessage();		}
		
		
		return "minesweeper";
	}
	
	public double getAverageRating() {
		 return ratingService.getAvgRating("minesweeper");
		
	}
	
	
	public String getHtmlField() {
		Formatter f = new Formatter();
		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);
				
				if (tile.getState() == Tile.State.CLOSED) {
					f.format("<a href='/minesweeper/open?row=%d&column=%d'><img src='/images/minesweeper/closed.png'></img></a>",row,column);
				} else if (tile.getState() == Tile.State.OPEN) {
					if (tile instanceof Clue) {
						Clue clue = (Clue) tile;
						f.format( "<a href='/minesweeper/open?row=%d&column=%d'><img src='/images/minesweeper/open%d.png'></img></a>", row,column,((Clue) tile).getValue());
					} 
					else if (tile instanceof Mine) {
						Mine mine = (Mine) tile;
						f.format("<a href='/minesweeper/open?row=%d&column=%d'><img src='/images/minesweeper/mine.png'></img></a>",row,column);
					}
				} else if (tile.getState() == Tile.State.MARKED) {
					f.format("<a href='/minesweeper/mark?row=%d&column=%d'><img src='/images/minesweeper/marked.png'></img></a>",row,column);
				}
					
				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}
		f.format("</table>\n");
		
		return f.toString();
	}
	
	public boolean isMarking() {
		return marking;
	}
	
	public List<Score> getScores(){
		return scoreService.getTopScores("minesweeper");
	}
	
	public boolean isSolved() {
	 return field.getState() == GameState.SOLVED;
	}
	
	public List<Comment> getComments(){
		return commentService.getComments("minesweeper");
	}
	
	
}
