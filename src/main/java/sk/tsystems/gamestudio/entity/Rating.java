package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity 
public class Rating {
	
	
	
	@Id
	@GeneratedValue
	private int ident;
	private String username;
	private String game;
	private int rating;
	
	
	public Rating() {
		
	}

	public Rating(String username, String game, int rating) {
		super();
		this.username = username;
		this.game = game;
		this.rating = rating;
	}


	
	public int getIdent() {
		return ident;
	}
	
	public void setIdent(int ident) {
		this.ident = ident;
	}
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getGame() {
		return game;
	}


	public void setGame(String game) {
		this.game = game;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Score [ident=" + ident + ", username=" + username + ", game=" + game + ", rating=" + rating + "]";
	}
	
	
	
	


	
	
	
}
