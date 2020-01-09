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
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController {

	private Field field;
	private String commentText;

	@Autowired
	private CommentService commentService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private LoginController loginController;

	@Autowired
	private RatingService ratingService;

	@RequestMapping("/puzzle")
	public String puzzle() {

		field = new Field(4, 4);

		return "puzzle";
	}

	@RequestMapping("/puzzle/move")
	public String move(int tile) {
		field.move(tile);
		if (field.isState() && loginController.isLogged()) {
			scoreService.addScore(
					new Score(loginController.getLoggedPlayer().getName(), "puzzle", field.getPuzzleScores()));
		}
		return "puzzle";
	}

	@RequestMapping("/puzzle/comment")
	public String comment(String text) {
		commentText = text;
		if(commentText.trim().length() < 2 || commentText.length() > 255) {
			return "puzzle";
		}else {
			commentService.addComment(new Comment(loginController.getLoggedPlayer().getName(), "puzzle", commentText));
			return "puzzle";
		}
	}

	@RequestMapping("/puzzle/rating")
	public String rate(String rating) {
		try {

			int parseRating = Integer.parseInt(rating);
			if(parseRating > 0 && parseRating <= 5) {
				ratingService.setRating(new Rating(loginController.getLoggedPlayer().getName(), "puzzle", parseRating));				
			}
			

		} catch (Exception e) {
			e.getMessage();
		}

		return "puzzle";
	}

	public double getAverageRating() {
		return ratingService.getAvgRating("puzzle");
		
		
	}

	public String getHtmlField() {
		Formatter f = new Formatter();
		f.format("<table class=\"table table-bordered\">\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);
				if (tile.getValue() != 0) {
					f.format("<a href='/puzzle/move?tile=%d'><img src='/images/puzzle/puzzle%d.png'></img></a>",
							tile.getValue(), tile.getValue());
				}
				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}
		f.format("</table>\n");

		return f.toString();
	}

	public boolean isSolved() {
		return field.isState();
	}

	public List<Score> getScores() {
		return scoreService.getTopScores("puzzle");
	}

	public List<Comment> getComments() {
		return commentService.getComments("puzzle");
	}

}
