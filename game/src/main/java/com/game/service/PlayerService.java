package com.game.service;

import com.game.entity.Player;
import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PlayerService {


    @Autowired
    PlayerRepository playerRepository;


    public Player createPlayer(Player player) throws ParseException {
        Date dateSince = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "01.01.2000" );
        Date datelimit = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "31.12.3000" );
        if(player.getName() == null || player.getTitle() == null || player.getRace() == null
                || player.getProfession() == null || player.getBirthday() == null || player.getExperience() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if(player.getName().length() > 12 || player.getTitle().length() > 30)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if(player.getName().trim().length() == 0 || player.getName().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if(player.getExperience() < 0 || player.getExperience() > 10000000 || player.getBirthday().getTime() < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if(player.getBirthday().getTime() < dateSince.getTime() || player.getBirthday().getTime() > datelimit.getTime())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        player.setLevel((int)((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100));
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
        playerRepository.save(player);
        return player;
    }


    public List<Player> getPlayerList(Integer pageNumber, Integer pageSize, PlayerOrder order, String name, String title, Race race, Profession profession, Long after,
                                      Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel){
        List<Player> players = playerRepository.findAll();
        List<Player> result = new ArrayList<>();
        Date dateAfter = null;
        Date dateBefore = null;
        if(after != null){
            dateAfter = new Date(after);}
        if(before != null)
            dateBefore = new Date(before);
        //PlayerService service = new PlayerService();
        //players = service.filterList(players,name,title,race,profession,after, before, banned, minExperience, maxExperience,minLevel,maxLevel);
        players = playerRepository.filterList(name,title,race,profession,dateAfter, dateBefore, banned, minExperience, maxExperience,minLevel,maxLevel);
        if(order == null)
            order = PlayerOrder.ID;
        if(order == PlayerOrder.ID){
            result = players.stream().sorted((Comparator.comparing(Player::getId))).collect(Collectors.toList());
            players = result;}
        else if(order == PlayerOrder.NAME){
            result = players.stream().sorted((Comparator.comparing(Player::getName))).collect(Collectors.toList());
            players = result;}
        else if(order == PlayerOrder.EXPERIENCE){
            result = players.stream().sorted((Comparator.comparing(Player::getExperience))).collect(Collectors.toList());
            players = result;}
        else if (order == PlayerOrder.BIRTHDAY){
            result = players.stream().sorted((Comparator.comparing(Player::getBirthday))).collect(Collectors.toList());
            players = result;}


        if(pageNumber == null){
            pageNumber = 0;
        }
        if(pageSize == null){
            pageSize = 3;
        }
        int firtElement = pageNumber * pageSize;
        int lastElement = pageNumber * pageSize + pageSize;
        if(lastElement < players.size()) return players.subList(firtElement, lastElement);
        else return players.subList(firtElement, players.size());
    }

    public Integer getPlayersCount(String name, String title, Race race, Profession profession, Long after,
                                   Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel){
        List<Player> players = playerRepository.findAll();
        Date dateAfter = null;
        Date dateBefore = null;
        if(after != null){
            dateAfter = new Date(after);}
        if(before != null)
            dateBefore = new Date(before);
        //PlayerService service = new PlayerService();
        //players = service.filterList(players,name,title,race,profession,after, before, banned, minExperience, maxExperience,minLevel,maxLevel);
        players = playerRepository.filterList(name,title,race,profession,dateAfter, dateBefore, banned, minExperience, maxExperience,minLevel,maxLevel);
        return players.size();
    }

    public Player getPlayer(Long id){
        if(id == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if(!playerRepository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return playerRepository.findById(id).get();
    }

    public Player updatePlayer(String id, Player playerX) throws ParseException {
        try{
            Long s = Long.parseLong(id);
            if(s == 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            Player x = playerRepository.findById(s).get();
        }catch (NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        catch (NumberFormatException e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        Date dateSince = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "01.01.2000" );
        Date datelimit = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "31.12.3000" );
        Player player = playerRepository.findById(Long.parseLong(id)).get();
        if(playerX.getName() != null)
            player.setName(playerX.getName());
        if(playerX.getTitle() != null)
            player.setTitle(playerX.getTitle());
        if(playerX.getRace() != null)
            player.setRace(playerX.getRace());
        if(playerX.getProfession() != null)
            player.setProfession(playerX.getProfession());
        if(playerX.getBirthday() != null){
            if(playerX.getBirthday().getTime() < dateSince.getTime() || playerX.getBirthday().getTime() > datelimit.getTime())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            else player.setBirthday(playerX.getBirthday());}
        if(playerX.getBanned() != null)
            player.setBanned(playerX.getBanned());
        if(playerX.getExperience() != null ){
            if(playerX.getExperience() < 0 || playerX.getExperience() > 10000000)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            else
                player.setExperience(playerX.getExperience());}
        player.setLevel((int)((Math.sqrt(2500 + 200 * player.getExperience()) - 50) / 100));
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
        return player;
    }

    public void deletePlayer(String id){
        try {
            Long id2 = Long.parseLong(id);
            if (id2 == 0)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            Player player = playerRepository.findById(Long.parseLong(id)).get();
            playerRepository.delete(player);
        }catch (NumberFormatException e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        catch (NoSuchElementException e){throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    }
    public List<Player> filterList(List<Player> list, String name, String title, Race race, Profession profession,
                                   Long after, Long before, Boolean banned, Integer minExperience,
                                   Integer maxExperience, Integer minLevel, Integer maxLevel) {

        List<Player> result = new ArrayList<>();
        for(Player player : list){
            if (name != null && !player.getName().contains(name))
                continue;
            if (title != null && !player.getTitle().contains(title))
                continue;
            if (race != null && player.getRace() != race)
                continue;
            if (profession != null && player.getProfession() != profession)
                continue;
            if (after != null && player.getBirthday().getTime() < after)
                continue;
            if (before != null && player.getBirthday().getTime() > before)
                continue;
            if (banned != null && player.getBanned() != banned)
                continue;
            if (minExperience != null && player.getExperience() < minExperience)
                continue;
            if (maxExperience != null && player.getExperience() > maxExperience)
                continue;
            if (minLevel != null && player.getLevel() < minLevel)
                continue;
            if(maxLevel != null && player.getLevel() > maxLevel)
                continue;
            result.add(player);
        }
        return result;
    }
}



