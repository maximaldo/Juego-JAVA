package com.game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;



	// Clase Animation que gestiona la animación por frames.
public class Animation {
    private int speed; // Velocidad de cambio entre frames
    private int frames; // Cantidad total de frames en la animación
    
    private int index = 0; // indice para controlar la velocidad
    private int count = 0; // Contador para el frame actual
    
    private BufferedImage[] images; // Array de imágenes de la animación
    private BufferedImage currentImg; // Imagen actual que se está mostrando
    
   
    // Constructor para inicializar una animación.
    public Animation(int speed, BufferedImage... args) {
        this.speed = speed;
        images = new BufferedImage[args.length];
        
        // Carga todas las imágenes en el array
        for (int i = 0; i < args.length; i++) {
            images[i] = args[i];
        }
        frames = args.length;
    }
    
    
     // Ejecuta la animación actualizando los frames según la velocidad.
    public void runAnimation() {
        index++;
        if (index > speed) {
            index = 0;
            nextFrame();
        }
    }
    
    
     // Cambia al siguiente frame de la animación.
    private void nextFrame() {
        currentImg = images[count];
        count++;
        if (count >= frames) {
            count = 0; // Reinicia la animación cuando llega al final
        }
    }
    
    
     // Dibuja el frame actual de la animación en la pantalla.
 
    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
        g.drawImage(currentImg, x, y, scaleX, scaleY, null);
    }
}
