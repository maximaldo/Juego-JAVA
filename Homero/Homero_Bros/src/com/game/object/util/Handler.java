package com.game.object.util;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import com.game.main.Game;
import com.game.object.GameObject;
import com.game.object.Player;

public class Handler {
    // Lista que contiene todos los objetos del juego
    private List<GameObject> gameObjs;
    private Player player; // Referencia al jugador
    private Game game; // Referencia al objeto principal del juego

    // Constructor que inicializa la lista de objetos y establece el juego
    public Handler(Game game) {
        this.game = game;
        gameObjs = new LinkedList<>();
    }

    // Actualiza cada objeto en la lista
    public void tick() {
        for (GameObject obj : gameObjs) {
            obj.tick();
        }
    }

    // Renderiza cada objeto en la lista
    public void render(Graphics g) {
        for (GameObject obj : gameObjs) {
            obj.render(g);
        }
    }

    // Añade un objeto a la lista
    public void addObj(GameObject obj) {
        gameObjs.add(obj);
    }

    // Elimina un objeto de la lista
    public void removeObj(GameObject obj) {
        gameObjs.remove(obj);
    }

    // Obtiene la lista de objetos del juego
    public List<GameObject> getGameObjs() {
        return gameObjs;
    }

    // Establece el jugador si aún no se ha definido
    public int setPlayer(Player player) {
        if (this.player != null) {
            return -1; // Retorna -1 si ya hay un jugador definido
        }
        addObj(player);
        this.player = player;
        return 0; // Operación exitosa
    }

    // Elimina al jugador actual
    public int removePlayer() {
        if (player == null) {
            return -1; // No hay jugador para eliminar
        }
        removeObj(player);
        this.player = null;
        return 0; // Operación exitosa
    }

    // Obtiene el jugador actual
    public Player getPlayer() {
        return player;
    }

    // Devuelve la referencia al juego principal
    public Game getGame() {
        return game;
    }
}

