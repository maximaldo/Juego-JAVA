package com.game.gfx;

import java.awt.Dimension;
import javax.swing.JFrame;
import com.game.main.Game;



// Clase Window que crea y gestiona la ventana del juego.
public class Window {
    private JFrame frame; // Marco de la ventana
    private Dimension size; // Dimensiones de la ventana

    
     // Constructor para crear una ventana.
    public Window(int width, int height, String title, Game game) {
        size = new Dimension(width, height);
        frame = new JFrame(title);

        // Configurar las dimensiones de la ventana
        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);

        // Configuraciones de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centrar ventana en la pantalla
        frame.add(game); // Añadir el juego al marco
        frame.setVisible(true); // Hacer visible la ventana
    }
}