package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.gfx.Camera;
import com.game.gfx.SoundPlayer;
import com.game.gfx.Texture;
import com.game.gfx.Window;
import com.game.main.util.LevelHandler;
import com.game.object.util.Handler;
import com.game.object.util.KeyInput;

public class Game extends Canvas implements Runnable {

    // Identificador de la clase (para evitar advertencias por serialización)
    private static final long serialVersionUID = 1L;

    // Constantes del juego
    private static final int MILLIS_PER_SEC = 1000; // Milisegundos en un segundo
    private static final int NANOS_PER_SEC = 1000000000; // Nanosegundos en un segundo
    private static final double NUM_TICKS = 60.0; // Ticks por segundo
    private static final String NAME = "Homero Bros"; // Nombre del juego

    // Dimensiones de la ventana y pantalla
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    private static final int SCREEN_WIDTH = WINDOW_WIDTH - 67;
    private static final int SCREEN_HEIGHT = WINDOW_HEIGHT;
    private static final int SCREEN_OFFSET = 16 * 3;

    // Rutas de recursos
    private static final String BACKGROUND_PATH = "/tile/background.png";
    private static final String SOUND_PATH = "/sounds/Song.wav";

    // Variables del juego
    private boolean running; // Indica si el juego está en ejecución
    private Thread thread; // Hilo del juego

    // Componentes principales del juego
    private Handler handler; // Maneja los objetos del juego
    private Camera cam; // Cámara para seguir al jugador
    private static Texture tex; // Texturas
    private LevelHandler levelHandler; // Controlador de niveles

    // Estado del juego
    private GameState gameState = GameState.MENU; // Estado inicial: Menú

    // Recursos gráficos
    private BufferedImage gameBackground; // Imagen de fondo del juego

    // Botones del menú
    private Rectangle playButton = new Rectangle(WINDOW_WIDTH / 2 - 100, 300, 200, 50);
    private Rectangle exitButton = new Rectangle(WINDOW_WIDTH / 2 - 100, 400, 200, 50);

    // Constructor
    public Game() {
        initialize();
    }

    // Método principal
    public static void main(String[] args) {
        new Game(); // Inicia el juego
    }

    // Inicializa componentes y recursos
    private void initialize() {
        tex = new Texture(); // Carga texturas

        // Cargar fondo
        try {
            gameBackground = ImageIO.read(getClass().getResource(BACKGROUND_PATH));
        } catch (IOException e) {
            System.err.println("Error cargando el fondo: " + BACKGROUND_PATH);
            e.printStackTrace();
        }

        // Configurar manejadores
        handler = new Handler(this);
        this.addKeyListener(new KeyInput(handler)); // Entrada de teclado

        levelHandler = new LevelHandler(handler);
        levelHandler.start(); // Inicia el controlador de niveles

        cam = new Camera(0, SCREEN_OFFSET); // Configurar cámara
        new Window(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this); // Crear ventana

        // Configurar entrada de ratón para el menú
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                handleMouseInput(e.getX(), e.getY());
            }
        });

        start(); // Iniciar el hilo del juego
    }

    // Manejar entrada del ratón
    private void handleMouseInput(int mx, int my) {
        if (gameState == GameState.MENU) {
            if (playButton.contains(mx, my)) {
                gameState = GameState.GAME; // Inicia el juego
            }
            if (exitButton.contains(mx, my)) {
                System.exit(0); // Sale del juego
            }
        } else if (gameState == GameState.WIN || gameState == GameState.LOSE) {
            System.exit(0); // En otros estados, sale del juego
        }
    }

    // Inicia el juego
    private synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();

        // Reproducir música de fondo
        SoundPlayer.playSound(SOUND_PATH);
    }
    
    
    // Detiene el juego
    private synchronized void stop() {
        try {
            running = false;
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Ciclo principal del juego
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = NANOS_PER_SEC / NUM_TICKS;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick(); // Actualiza el juego
                delta--;
            }

            if (running) {
                render(); // Renderiza la pantalla
                frames++;
            }

            if (System.currentTimeMillis() - timer > MILLIS_PER_SEC) {
                timer += MILLIS_PER_SEC;
                System.out.println("FPS: " + frames); // Muestra los FPS
                frames = 0;
            }
        }

        stop();
    }

    private void tick() {
        if (gameState == GameState.GAME) {
            handler.tick();
            cam.tick(handler.getPlayer());
        }
    }
    // Renderiza el juego en pantalla
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3); // Triple buffering
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        switch (gameState) {
            case MENU:
                drawMenu(g);
                break;
            case GAME:
                drawGame(g);
                break;
            case WIN:
                drawWinScreen(g);
                break;
            case LOSE:
                drawLoseScreen(g);
                break;
        }

        g.dispose();
        bs.show();
    }

    
    // Métodos para dibujar diferentes estados de juego
    private void drawMenu(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Super Homero Bros", WINDOW_WIDTH / 2 - 200, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawRect(playButton.x, playButton.y, playButton.width, playButton.height);
        g.drawString("Jugar", playButton.x + 60, playButton.y + 35);

        g.drawRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);
        g.drawString("Salir", exitButton.x + 65, exitButton.y + 35);
    }

    private void drawGame(Graphics g) {
        if (gameBackground != null) {
            g.drawImage(gameBackground, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(cam.getX(), cam.getY());
        handler.render(g); // Renderiza los objetos del juego
        g2d.translate(-cam.getX(), -cam.getY());
    }

    private void drawWinScreen(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("¡GANASTE!", WINDOW_WIDTH / 2 - 300, WINDOW_HEIGHT / 2);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Haz clic para salir", WINDOW_WIDTH / 2 - 110, WINDOW_HEIGHT / 2 + 100);
    }

    private void drawLoseScreen(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString("¡PERDISTE!", WINDOW_WIDTH / 2 - 300, WINDOW_HEIGHT / 2);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Haz clic para salir", WINDOW_WIDTH / 2 - 110, WINDOW_HEIGHT / 2 + 100);
    }

    
    
    // Métodos de utilidad
    public void setGameState(GameState newState) {
        this.gameState = newState;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static Texture getTexture() {
        return tex;
    }
}

