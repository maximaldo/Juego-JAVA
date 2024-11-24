package com.game.gfx;

import com.game.main.Game;
import com.game.object.GameObject;


// Clase Camera para gestionar el desplazamiento del juego.
public class Camera {
    private int x, y; // Posición de la cámara

    // Constructor de la camara
    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    // Actualiza la posición de la cámara según la posición del jugador.
    public void tick(GameObject player) {
        x = (int) (-player.getX() + Game.getScreenWidth() / 3);
        y = (int) (-player.getY() + Game.getScreenHeight() / 2);
    }

    // Métodos getter y setter para X e Y
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}