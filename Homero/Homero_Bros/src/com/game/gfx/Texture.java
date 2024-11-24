package com.game.gfx;

import java.awt.image.BufferedImage;


 // Clase Texture que gestiona las texturas del juego, como los sprites del jugador y los tiles
public class Texture {

    private final String PARENT_FOLDER = "/tile"; // Carpeta donde se encuentran los recursos

    private final int HOMERO_L_COUNT = 4; // Cantidad de frames para la animación de Homero caminando
    private final int TILE_1_COUNT = 28; // Cantidad de tiles del primer conjunto
    private final int TILE_2_COUNT = 33; // Cantidad de tiles del segundo conjunto

    private BufferedImageLoader loader; // Cargador de imágenes
    private BufferedImage player_sheet, tile_sheet; // Sprites del jugador y de los tiles

    public BufferedImage[] homero_l, tile_1, tile_2, tile_3, tile_4; // Arrays para almacenar los sprites

    
     //Constructor que inicializa las texturas del juego.
    public Texture() {
        homero_l = new BufferedImage[HOMERO_L_COUNT];
        tile_1 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile_2 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile_3 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];
        tile_4 = new BufferedImage[TILE_1_COUNT + TILE_2_COUNT];

        loader = new BufferedImageLoader();

        try {
            // Carga los sprites desde los recursos
            player_sheet = loader.loadImage(PARENT_FOLDER + "/Homer (2).png");
            tile_sheet = loader.loadImage(PARENT_FOLDER + "/Tileset.png");
        } catch (Exception e) {
            e.printStackTrace(); // Muestra errores de carga
        }

        getPlayerTextures();
        getTileTextures();
    }

   
    // Carga los sprites del jugador desde el sprite sheet / hoja de sprites.  
    private void getPlayerTextures() {
        int x_off = 0, y_off = 0;
        int width = 76, height = 147; // Dimensiones de cada frame del jugador

        for (int i = 0; i < HOMERO_L_COUNT; i++) {
            homero_l[i] = player_sheet.getSubimage(x_off + i * (width + 1), y_off, width, height);
        }
    }

    
     //Carga los tiles desde el sprite sheet.
    private void getTileTextures() {
        int x_off = 0, y_off = 0;
        int width = 16, height = 16; // Dimensiones de cada bloque (16x16 pixeles)

        // Cargar los primeros conjuntos de tiles
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < TILE_1_COUNT; i++) {
                if (j == 0) {
                    tile_1[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 1) {
                    tile_2[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 2) {
                    tile_3[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 3) {
                    tile_4[i] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                }
            }
        }

        y_off += height;

        // Cargar los segundos conjuntos de tiles
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < TILE_2_COUNT; i++) {
                if (j == 0) {
                    tile_1[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 1) {
                    tile_2[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 2) {
                    tile_3[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                } else if (j == 3) {
                    tile_4[i + TILE_1_COUNT] = tile_sheet.getSubimage(x_off + i * width, y_off + j * height * 2, width, height);
                }
            }
        }
    }

    // Métodos getter para obtener las texturas
    public BufferedImage[] getHomero_L() {
        return homero_l;
    }

    public BufferedImage[] getTile1() {
        return tile_1;
    }

    public BufferedImage[] getTile2() {
        return tile_2;
    }

    public BufferedImage[] getTile3() {
        return tile_3;
    }

    public BufferedImage[] getTile4() {
        return tile_4;
    }
}
