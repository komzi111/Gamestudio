package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;




@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumberController {
	
	
	private Random random = new Random();
	private int randomNumber;
	private String message;
	private int guessedNumber;
	private String commentText;
	private long startMillis;
	
	
	
	
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private LoginController loginController;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RatingService ratingService;
	
	
	@RequestMapping("/guessnumber")
	public String index() {
		guessedNumber = 0;
		randomNumber = random.nextInt(10)+1;
		startMillis = System.currentTimeMillis();
		return "guessnumber";
	}
	
	
	
	@RequestMapping("/guessnumber/guess")
	public String guess(String number) {
		try {
			int parseNumber = Integer.parseInt(number);
			 guessedNumber = parseNumber;
			 if(isSolved() && loginController.isLogged()) {
					scoreService.addScore(new Score(loginController.getLoggedPlayer().getName(),"guessnumber",getGuessNumberScores()));
				}
			
		} catch (Exception e) {
			e.getMessage();
		}
		return "guessnumber";
	}
	
	@RequestMapping("/guessnumber/comment")
	public String comment(String text) {
		commentText = text;
		commentService.addComment(new Comment(loginController.getLoggedPlayer().getName(),"guessnumber", commentText));
		return "guessnumber";
	}
	
	@RequestMapping("/guessnumber/rating")
	public String rate(String rating) {
		try {
			
			int parseRating = Integer.parseInt(rating);
			ratingService.setRating(new Rating(loginController.getLoggedPlayer().getName(),"guessnumber",parseRating));
			
		} catch (Exception e) {
				e.getMessage();		}
		
		
		return "guessnumber";
	}
	
	public double getAverageRating() {
		 return ratingService.getAvgRating("guessnumber");
		
	}
	
	
	public String getNumberField() {
		Formatter f = new Formatter();
		
		f.format("<p>Insert random number from 1 to 10</p>");
		
		f.format("<form  action=\"/guessnumber/guess\">");
		  f.format("<div class=\"form-group\" >");	
			f.format("<label>Your Guess Number: <input class=\"form-control\" type=\"text\" name=\"number\" autofocus /></label><br/>");
			f.format("<input type=\"submit\" value=\"Submit\">");
		  f.format("</div>");
		f.format("</form>");
		
		
		return f.toString();
		
	}
	
	
	public String getMessage() {
		if (guessedNumber < randomNumber) {
			Formatter f = new Formatter();
			return f.format("<p>The number is lower than your guess</p>").toString();
		}
			
			
		if (guessedNumber > randomNumber) {
			Formatter f = new Formatter();
			 return f.format("<p>The number is higher than your guess</p>").toString();
		}
		
		return message;
	}
	
	public int getGuessedNumber() {
		return guessedNumber;
	}

	public String getCommentText() {
		return commentText;
	}
	
	
	public boolean isSolved() {
		if (guessedNumber != randomNumber) return false;
		return true;
	}
	public List<Score> getScores(){
		return scoreService.getTopScores("guessnumber");
	}
	
	public List<Comment> getComments(){
		return commentService.getComments("guessnumber");
	}
	
	private int getGuessNumberScores() {
		if(isSolved()) {
	        int seconds = (int)((System.currentTimeMillis() - startMillis) / 1000);
	        int score = 300 - seconds;
	        return score;
        }
		
		return 0;
    
}

}





