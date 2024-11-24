package com.game.gfx;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

 
// Clase SoundPlayer para reproducir sonidos.
public class SoundPlayer {

    
     // Reproduce un archivo de sonido desde la ruta especificada.
    	//soundPath es la ruta relativa del archivo, generalmente .wav.
    public static void playSound(String soundPath) {
        try {
            // Cargar el archivo de sonido desde los recursos
            InputStream audioSrc = SoundPlayer.class.getResourceAsStream(soundPath);
            if (audioSrc == null) {
                System.err.println("Archivo de sonido no encontrado: " + soundPath);
                return;
            }
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Inicia la reproducción
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Muestra cualquier error de reproducción
        }
    }
}
