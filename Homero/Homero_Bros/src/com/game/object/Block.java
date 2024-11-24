package com.game.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.object.util.ObjectId;

public class Block extends GameObject {

    // Textura del bloque
    private Texture tex = Game.getTexture();
    private int index; // Índice del sprite del bloque
    private BufferedImage[] sprite; // Arreglo de imágenes del sprite

    // Variables para el movimiento horizontal de bloques enemigos
    private boolean movable; // Indica si el bloque es movible
    private int direction = 1; // Dirección del movimiento (1 = derecha, -1 = izquierda)
    private int speed = 3; // Velocidad de movimiento del bloque
    private int moveRange = 200; // Rango máximo de movimiento
    private float initialX; // Posición inicial en X

    
    // Constructor de la clase Block.
    public Block(int x, int y, int width, int height, int index, int scale) {
        super(x, y, ObjectId.Block, width, height, scale);
        this.index = index; // Asigna el índice del sprite
        this.sprite = tex.getTile1(); // Obtiene las imágenes del sprite
        this.movable = (index == 15); // Solo los bloques con índice 15 son movibles
        this.initialX = x; // Registra la posición inicial en X
    }

    
    // Lógica de actualización del bloque.
    @Override
    public void tick() {
        if (movable) { // Solo aplica si el bloque es movible
            // Calculamos la nueva posición en X
            float newX = getX() + direction * speed;

            // Verificamos si el bloque ha alcanzado los límites de su rango
            if (newX >= initialX + moveRange) {
                newX = initialX + moveRange; // Ajusta la posición al límite derecho
                direction *= -1; // Cambia la dirección hacia la izquierda
            } else if (newX <= initialX - moveRange) {
                newX = initialX - moveRange; // Ajusta la posición al límite izquierdo
                direction *= -1; // Cambia la dirección hacia la derecha
            }

            // Actualizamos la posición en X del bloque
            setX(newX);
        }
    }

    
    // Dibuja el bloque en pantalla.
    @Override
    public void render(Graphics g) {
        g.drawImage(sprite[index], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);

        // Mostrar los bordes del bloque (opcional)
        // showBounds(g);
    }

    
     // Devuelve los límites del bloque como un rectángulo (útil para detección de colisiones)
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    
     // Método opcional para mostrar los límites del bloque.
    /* private void showBounds(Graphics g) {
         Graphics2D g2d = (Graphics2D) g;
         g.setColor(Color.red);
         g2d.draw(getBounds());
     }
*/
    
     // Devuelve el índice del sprite del bloque.
    public int getIndex() {
        return index;
    }
}

