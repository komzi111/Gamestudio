package sk.tsystems.gamestudio.service;



import sk.tsystems.gamestudio.entity.Rating;

public interface RatingService {

	
	
	
	double getAvgRating(String game);
	
	void setRating( Rating rating);
	
	
	
}
