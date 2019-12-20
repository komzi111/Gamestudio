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
	
	
	@Autowired PlayerService playerService;
	
	@Autowired LoginController loginController;
	
	
	@RequestMapping("/register")
	public String index() {
		return "register";
	}
	
	
	@RequestMapping("/register/process")
		public String processInput(Player player) {
		if(player.getName().length() <= 2 || player.getPassword().length() <= 3  ) {
			return "register";
		}else
			playerService.addPlayer(new Player(player.getName(),player.getPassword()));
			loginController.login(player);
		return "redirect:/";
	}
	
	public String getMessage() {
		
		
		return "register";
	}

		
	
}
