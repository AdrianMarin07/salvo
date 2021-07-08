package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
    @Autowired
    GameRepository gameRepo;

    //@Autowired
    //ShipRepository shipRepo;

    @Autowired
    GamePlayerRepository gamePlayerRepo;

    //@Autowired
    //SalvoRepository salvoRepo;

    @Autowired
    PlayerRepository playerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());
        dto.put("gamePlayers", game.getGamePlayers().stream().map(this::makeGamePlayerDTO).collect(Collectors.toList()));
        dto.put("scores", game.getGamePlayers().stream().map(this::makeScoreDTO).collect(Collectors.toList()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getType());
        dto.put("locations", ship.getLocations());
        return dto;
    }

    private Map<String, Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", salvo.getTurn());
        dto.put("player", salvo.getGamePlayer().getPlayer().getId());
        dto.put("locations", salvo.getLocations());
        return dto;
    }

    private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }

    private Map<String, Object> makeScoreDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if (gamePlayer.getScore().isPresent()) {
            dto.put("player", gamePlayer.getPlayer().getId());
            dto.put("score", gamePlayer.getScore().get().getScore());
            dto.put("finishDate", gamePlayer.getScore().get().getFinishDate());
        } else {
            dto.put("score", "Game has no score");
        }
        return dto;
    }

    //@RequestMapping("/games")
    @GetMapping("/games")
    public Map<String, Object> getAllGames() {
        List<Game> list = gameRepo.findAll();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("games", list.stream().map(this::makeGameDTO).collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping("game_view/{gamePlayerId}")
    public Map<String, Object> findGame(@PathVariable long gamePlayerId) {
        GamePlayer gamePlayer = gamePlayerRepo.getById(gamePlayerId);
        Map<String, Object> dto = makeGameDTO(gamePlayer.getGame());
        dto.put("ships", gamePlayer.getShips().stream().map(this::makeShipDTO).collect(Collectors.toList()));
        dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream().flatMap((salvo -> salvo.getSalvoes().stream().map(this::makeSalvoDTO))));
        return dto;
    }

    @RequestMapping(path = "/´players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String username, @RequestParam String password) {

        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepo.findByUsername(username) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        playerRepo.save(new Player(username, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*@RequestMapping("/games/{gameId}/players")
    public Map<String, Object> getPlayersInGame(@PathVariable long gameId) {
        Game game = repo.findById(gameId);
        List<Player> list = game.getGamePlayers().stream().map(gamePlayer -> gamePlayer.getPlayer()).collect(Collectors.toList());
        return makePlayerDTO(list.stream().findFirst().get());
    }*/
}
