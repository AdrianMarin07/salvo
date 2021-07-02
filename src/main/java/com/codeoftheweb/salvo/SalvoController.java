package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    GameRepository gameRepo;

    @Autowired
    ShipRepository shipRepo;

    @Autowired
    GamePlayerRepository gamePlayerRepo;


    private Map<String, Object> makeGameDTO(Game game){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(gamePlayer -> makeGamePlayerDTO(gamePlayer)).collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("locations", ship.getLocations());
        return dto;
    }

    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    //@RequestMapping("/games")
    @GetMapping("/games")
    public List<Object> getAllGames() {
        List<Game> list = gameRepo.findAll();
        return list.stream().map(game -> makeGameDTO(game)).collect(Collectors.toList());
    }

    /*@RequestMapping("/ships")
    public Map<String, Object> getAllPositions() {
        List<Ship> list = shipRepo.findAll();
        return list.stream().map(ship -> makeShipDTO(ship)).collect(Collectors.toMap());
    }*/

    @RequestMapping("game_view/{gamePlayerId}")
    public Map<String, Object> findGame(@PathVariable long gamePlayerId) {
        GamePlayer gamePlayer = gamePlayerRepo.getById(gamePlayerId);
        Map<String, Object> dto = makeGameDTO(gamePlayer.getGame());
        dto.put("ships", gamePlayer.getShips().stream().map(ship -> makeShipDTO(ship)).collect(Collectors.toList()));
        return dto;
    }


    /*@RequestMapping("/games/{gameId}/players")
    public Map<String, Object> getPlayersInGame(@PathVariable long gameId) {
        Game game = repo.findById(gameId);
        List<Player> list = game.getGamePlayers().stream().map(gamePlayer -> gamePlayer.getPlayer()).collect(Collectors.toList());
        return makePlayerDTO(list.stream().findFirst().get());
    }*/
}
