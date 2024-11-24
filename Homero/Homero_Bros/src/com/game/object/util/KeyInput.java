package com.game.object.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.game.gfx.SoundPlayer;


 // Clase KeyInput que gestiona las entradas del teclado.
public class KeyInput extends KeyAdapter {
    private boolean[] keyDown = new boolean[4]; // Controla si una tecla está presionada
    private static final String JUMP_SOUND_PATH = "/sounds/sonido.wav"; // Ruta del sonido de salto
    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0); // Salir del juego al presionar ESC
        }

        // Saltar
        if (key == KeyEvent.VK_W && !handler.getPlayer().hasJumped()) {
            handler.getPlayer().setVelY(-15);
            keyDown[0] = true;
            handler.getPlayer().setJumped(true);
            SoundPlayer.playSound(JUMP_SOUND_PATH); // Reproducir sonido de salto
        }

        // Mover a la izquierda
        if (key == KeyEvent.VK_A) {
            handler.getPlayer().setVelX(-8);
            keyDown[1] = true;
        }

        // Mover a la derecha
        if (key == KeyEvent.VK_D) {
            handler.getPlayer().setVelX(8);
            keyDown[2] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) keyDown[0] = false;
        if (key == KeyEvent.VK_A) keyDown[1] = false;
        if (key == KeyEvent.VK_D) keyDown[2] = false;

        // Detener movimiento horizontal si no hay teclas de dirección presionadas
        if (!keyDown[1] && !keyDown[2]) {
            handler.getPlayer().setVelX(0);
        }
    }
}
