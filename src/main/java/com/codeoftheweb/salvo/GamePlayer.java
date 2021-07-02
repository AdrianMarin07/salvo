package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;

    private Date joinedDate ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Ship> ships = new HashSet<>();


    public GamePlayer() { }

    public GamePlayer(Game game, Player player, Date joinedDate) {
        this.game = game;
        this.player = player;
        this.joinedDate = joinedDate;
    }

    public Player getPlayer() {
        return player;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShip(Set<Ship> ship) {
        this.ships = ship;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return joinedDate;
    }

    public void setCreationDate(Date creationDate) {
        this.joinedDate = joinedDate;
    }
}
