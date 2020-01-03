package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.PlayerService;



@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LoginController {
	
	private Player loggedPlayer;
	private String message;
	
	@Autowired
	private PlayerService playerService;
	
	@RequestMapping("/")
	public String index() {
		message = "";
		return "index";
	}
	
	@RequestMapping("/login")
	public String login( Player player) {
		Player playerInDb = playerService.getPlayer(player.getName());
		if(playerInDb != null) {
			message = "User exists";
		}
		if(player.getName().length() <=2  || player.getPassword().length() <= 3) {
			message = "Invalid name or username";
			return "index";
		}
		else 
			if(playerService.getPlayer(player.getName()).getPassword().equals(player.getPassword()))
				loggedPlayer = player;
				message = "You log in successfully";
			return "redirect:/";
		
		
	}
	@RequestMapping("/logout")
	public String logout( Player player) {
		loggedPlayer = null;
		return "redirect:/";
	}
	
	
	public boolean isLogged() {
		return loggedPlayer != null;
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}
	public String getMessage() {
		return message;
	}
	
	
	
	
	
}
