package com.game.main.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.game.gfx.BufferedImageLoader;
import com.game.object.Block;
import com.game.object.Player;
import com.game.object.util.Handler;


// Clase que maneja los niveles del juego.
// Se encarga de cargar y procesar las imágenes de los niveles.
public class LevelHandler {
    // Constantes
    private final String PARENT_FOLDER = "/level";
    private final int TILE_SIZE = 16;

    // Componentes necesarios
    private BufferedImageLoader loader;
    private Handler handler;
    private Map<Color, BiConsumer<Integer, Integer>> tileActions;

 
    // Constructor que inicializa el LevelHandler.
    public LevelHandler(Handler handler) {
        this.handler = handler;
        this.loader = new BufferedImageLoader();
        this.tileActions = new HashMap<>();
        initializeTileActions();
    }

     // Método para inicializar el nivel.
     // Carga los tiles y los personajes.
    public void start() {
        setLevel(PARENT_FOLDER + "/1_1.png"); // Carga los tiles del nivel.
        loadCharacters(PARENT_FOLDER + "/1_1c.png"); // Carga los personajes.
    }

    
     // Define las acciones que corresponden a cada color en el mapa.
    private void initializeTileActions() {
        tileActions.put(new Color(255, 255, 255), (x, y) -> {}); // Blanco: no hacer nada.
        tileActions.put(new Color(0, 0, 0), (x, y) -> 
            handler.addObj(new Block(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 17, 3))
        ); // Negro: añadir bloque.
        tileActions.put(new Color(0, 0, 255), (x, y) -> 
            handler.addObj(new Block(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 15, 3))
        ); // Azul: añadir otro tipo de bloque.

        tileActions.put(new Color(255, 0, 0), (x, y) -> {
            // Rojo: añadir un bloque complejo (4 partes).
            handler.addObj(new Block(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 26, 3)); // Superior izquierda.
            handler.addObj(new Block((x + 1) * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, 27, 3)); // Superior derecha.
            handler.addObj(new Block(x * TILE_SIZE, (y + 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE, 54, 3)); // Inferior izquierda.
            handler.addObj(new Block((x + 1) * TILE_SIZE, (y + 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE, 55, 3)); // Inferior derecha.
        });
    }

   
     // Procesa los tiles del nivel.
    public void setLevel(String levelTilesPath) {
        BufferedImage levelTiles = loader.loadImage(levelTilesPath);
        processPixels(levelTiles, (x, y, color) -> {
            BiConsumer<Integer, Integer> action = tileActions.get(color);
            if (action != null) {
                action.accept(x, y);
            }
        });
    }

    
     // Carga los personajes del nivel.
        private void loadCharacters(String levelCharactersPath) {
        BufferedImage levelTiles = loader.loadImage(levelCharactersPath);
        processPixels(levelTiles, (x, y, color) -> {
            if (color.equals(new Color(0, 0, 0))) { // Negro: añadir al jugador.
                handler.setPlayer(new Player(x * TILE_SIZE, y * TILE_SIZE, 2, handler));
            }
        });
    }

    
     // Procesa los píxeles de una imagen para ejecutar acciones según el color.
    private void processPixels(BufferedImage image, PixelProcessor processor) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);
                processor.process(x, y, color);
            }
        }
    }

    
     //Interfaz funcional para procesar los píxeles de una imagen.
    @FunctionalInterface
    private interface PixelProcessor {
        void process(int x, int y, Color color);
    }
}
