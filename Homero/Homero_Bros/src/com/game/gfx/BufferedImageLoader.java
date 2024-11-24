package com.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


 // Clase para cargar imágenes desde los recursos del proyecto.
public class BufferedImageLoader {
    private BufferedImage image;
    
      
    //Carga una imagen desde la ruta especificada.
       //path es la ruta relativa de la imagen
       // Devuelve la imagen o null si tira error

    public BufferedImage loadImage(String path) {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace(); // Muestra un error si no se puede cargar la imagen
        }
        return image;
    }
}
