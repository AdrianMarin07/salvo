package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository) {
		return (args) -> {

			Player player1 = new Player("Jack Black");
			Player player2 = new Player("Max Power");
			Player player3 = new Player("John Batman");

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);

			Date creationDate = new Date();
			Game game1 = new Game(Date.from(creationDate.toInstant().plusSeconds(3600)));
			Game game2 = new Game(Date.from(creationDate.toInstant().plusSeconds(7200)));
			Game game3 = new Game(Date.from(creationDate.toInstant().plusSeconds(10800)));

			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);

			GamePlayer gamePlayer1 = new GamePlayer(game1, player1, Date.from(creationDate.toInstant()));
			GamePlayer gamePlayer2 = new GamePlayer(game1, player2, Date.from(creationDate.toInstant()));
			GamePlayer gamePlayer3 = new GamePlayer(game2, player2, Date.from(creationDate.toInstant()));
			GamePlayer gamePlayer4 = new GamePlayer(game2, player3, Date.from(creationDate.toInstant()));
			GamePlayer gamePlayer5 = new GamePlayer(game3, player3, Date.from(creationDate.toInstant()));
			GamePlayer gamePlayer6 = new GamePlayer(game3, player1, Date.from(creationDate.toInstant()));

			gamePlayerRepository.save(gamePlayer1);
			gamePlayerRepository.save(gamePlayer2);
			gamePlayerRepository.save(gamePlayer3);
			gamePlayerRepository.save(gamePlayer4);
			gamePlayerRepository.save(gamePlayer5);
			gamePlayerRepository.save(gamePlayer6);

			List<String> locations1= List.of("A2", "A3", "A4", "A5", "A6");
			List<String> locations2= List.of("D3", "E3", "F3", "G3");
			List<String> locations3= List.of("D5", "E5", "F5");
			List<String> locations4= List.of("I6", "I7", "I8");
			List<String> locations5= List.of("F9", "G9");
			List<String> locations6= List.of("B9", "C9", "D9", "E9", "F9");
			List<String> locations7= List.of("H4", "H5", "H6", "H7");
			List<String> locations8= List.of("B2", "B3", "B4");
			List<String> locations9= List.of("C6", "D6", "E6");
			List<String> locations10= List.of("J1", "J2");

			Ship ship1 = new Ship("Carrier", locations1,gamePlayer1);
			Ship ship2 = new Ship("Battleship", locations2,gamePlayer1);
			Ship ship3 = new Ship("Submarine", locations3,gamePlayer1);
			Ship ship4 = new Ship("Destroyer", locations4,gamePlayer1);
			Ship ship5 = new Ship("Patrol Boat", locations5,gamePlayer1);

			Ship ship6 = new Ship("Carrier", locations6,gamePlayer2);
			Ship ship7 = new Ship("Battleship", locations7,gamePlayer2);
			Ship ship8 = new Ship("Submarine", locations8,gamePlayer2);
			Ship ship9 = new Ship("Destroyer", locations9,gamePlayer2);
			Ship ship10 = new Ship("Patrol Boat", locations10,gamePlayer2);

			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);

			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);
		};
	}

}
