package sk.tsystems.gamestudio.service;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import sk.tsystems.gamestudio.entity.Rating;


@Component
@Transactional
public class RatingServiceJPA implements RatingService{

	@PersistenceContext
	private EntityManager entityManager;

	

	
	@Override
	public double getAvgRating(String game) {
		Object result = entityManager.createQuery("select  round(avg(r.rating),2)  from Rating r where r.game = :game")
				 .setParameter("game", game)
				 .getSingleResult();
		
		if(result == null) {
			return  0.0;
		} 
		
		return ((Double)result).doubleValue();
		
		 
	}

	

	@Override
	public void setRating(Rating rating) {
		
		try {
			Rating dbRating = (Rating) entityManager.createQuery("select r from Rating r where r.username = :username and r.game = :game")
					.setParameter("username", rating.getUsername())
					.setParameter("game",rating.getGame())
					.getSingleResult();
			dbRating.setRating(rating.getRating());
		} catch (NoResultException e) {
			entityManager.persist(rating);
		}
		
		

		
		
	}

	

	


	
		
		
	

}
