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
public class RegisterController {
	
	private String message;
	
	@Autowired PlayerService playerService;
	
	@Autowired LoginController loginController;
	
	
	
	
	
	@RequestMapping("/register")
	public String index() {
		message = "";
		return "register";
	}
	
	
	@RequestMapping("/register/process")
		public String processInput(Player player) {
		Player existPlayer = playerService.getPlayer(player.getName());
		if(player.getName().length() <= 2 || player.getPassword().length() <= 3) {
			message = "Invalid name or invalid password";
			return "register";
		}
		else
			if(existPlayer == null) {
				playerService.addPlayer(new Player(player.getName(),player.getPassword()));
				loginController.login(player);
			}
		return "redirect:/";
	}
	
	public String getMessage() {
		return message;
	}

		
	
}
