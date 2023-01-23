package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;



@Controller
@RequestMapping(path="/")
public class MainController {
	@Autowired
	private PlayerService service;

	@PostMapping(path="/rest/players/") // Map ONLY POST Requests
	public @ResponseBody Player createPlayer (@RequestBody Player player) throws ParseException {
		return service.createPlayer(player);
	}



	@GetMapping(path="/rest/players")
	public @ResponseBody Iterable<Player> getPlayersList(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize,
														 @RequestParam(required = false) PlayerOrder order,  @RequestParam(required = false) String name,
														 @RequestParam(required = false) String title, @RequestParam(required = false) Race race,
														 @RequestParam(required = false) Profession profession, @RequestParam(required = false) Long after,
														 @RequestParam(required = false) Long before, @RequestParam(required = false) Boolean banned,
														 @RequestParam(required = false) Integer minExperience, @RequestParam(required = false) Integer maxExperience,
														 @RequestParam(required = false) Integer minLevel, @RequestParam(required = false) Integer maxLevel) {
		return service.getPlayerList(pageNumber, pageSize,order, name, title, race, profession,
				after, before, banned, minExperience, maxExperience, minLevel,maxLevel);
	}

	@GetMapping(path="/rest/players/count")
	public @ResponseBody Integer getPlayersCount(@RequestParam(required = false) String name, @RequestParam(required = false) String title,
												 @RequestParam(required = false) Race race, @RequestParam(required = false) Profession profession,
												 @RequestParam(required = false) Long after, @RequestParam(required = false) Long before,
												 @RequestParam(required = false) Boolean banned, @RequestParam(required = false) Integer minExperience,
												 @RequestParam(required = false) Integer maxExperience, @RequestParam(required = false) Integer minLevel,
												 @RequestParam(required = false) Integer maxLevel) {
	return service.getPlayersCount(name,title,race,profession,after, before, banned, minExperience, maxExperience,minLevel,maxLevel);
	}

	@GetMapping(path="/rest/players/{id}")
	public @ResponseBody Player getPlayer(@PathVariable("id") Long id) {
		return service.getPlayer(id);
	}


	@PostMapping(path="/rest/players/{id}")
	public @ResponseBody Player updatePlayer(@PathVariable("id") String id, @RequestBody Player playerX) throws ParseException {
		return service.updatePlayer(id, playerX);
	}



	@DeleteMapping(path="/rest/players/{id}")
	public void deletePlayer(@PathVariable("id") String id) {
		service.deletePlayer(id);
	}

}





