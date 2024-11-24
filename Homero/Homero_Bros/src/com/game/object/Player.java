package com.game.object;

// import java.awt.Color;

import java.awt.Graphics;
// import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.gfx.Animation;
import com.game.gfx.SoundPlayer;
import com.game.gfx.Texture;
import com.game.main.Game;
import com.game.main.GameState;
import com.game.object.util.Handler;
import com.game.object.util.ObjectId;


public class Player extends GameObject {
	// Dimensiones del jugador
	private static final float WIDTH = 16;
	private static final float HEIGHT = 32;
	
	private Handler handler; // Manejador de objetos
	private Texture tex; // Textura del jugador
	
	// Animaciones y sprites
	private BufferedImage[] spriteL;
	private Animation playerWalkL;
	private BufferedImage[] currSprite;
	private Animation currAnimation;
	
	
	// Rutas de sonido para eventos de ganar o perder
    private static final String LOSE_SOUND_PATH = "/sounds/Lose.wav"; 
    private static final String WIN_SOUND_PATH = "/sounds/Win.wav";
	
    
    // Estados de movimiento del jugador
	private boolean jumped =  false;
	private boolean forward = false;
	
	
	// Constructor del jugador
	public Player(float x, float y, int scale, Handler handler) {
		super(x, y, ObjectId.Player, WIDTH, HEIGHT, scale);
		this.handler = handler;
		tex = Game.getTexture();
		
		// Inicializa los sprites y animaciones
		spriteL = tex.getHomero_L();		
		playerWalkL = new Animation(4, spriteL[2], spriteL[1], spriteL[2], spriteL[2]);
		currSprite = spriteL;
		currAnimation = playerWalkL;
		
		
		
	}

	@Override
	public void tick() {
		// Actualiza la posicion
		setX(getVelX() + getX());
		setY(getVelY() + getY());
		applyGravity(); // Aplica gravedad
		collision(); // Verifica colisiones
		currAnimation.runAnimation();
		// Caida (condicion para perder)
		if (getY() > 1700 * Game.getScreenHeight() / 720) {
        	SoundPlayer.playSound(LOSE_SOUND_PATH); // Reproducir el sonido al perder
	        handler.getGame().setGameState(GameState.LOSE); // Cambia el estado de juego a perdido
	    }
	}

	@Override
	public void render(Graphics g) {
		if(jumped) { // Si el jugador esta saltando
			if(forward) { // Mirando hacia adelante
				g.drawImage(currSprite[3], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);
			}else { // Mirando hacia atras
				g.drawImage(currSprite[3], (int) (getX() + getWidth()), (int) getY(), (int) -getWidth(), (int) getHeight(), null);
			}
		} else if(getVelX() > 0) { // Movimiento hacia la derecha
			currAnimation.drawAnimation(g,  (int) getX() , (int) getY(), (int) getWidth(), (int) getHeight());
			forward = true;
		} else if(getVelX() < 0) { // Movimiento hacia la izquierda
			currAnimation.drawAnimation(g,  (int) (getX() + getWidth()) , (int) getY(), (int) -getWidth(), (int) getHeight());
			forward = false;
		} else { // Idle (Inactivo)
			g.drawImage(currSprite[0], (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), null);

		}
		 //showBounds(g);
		
	}

	// Maneja las colisiones
	private void collision() {
	    for (int i = 0; i < handler.getGameObjs().size(); i++) {
	        GameObject temp = handler.getGameObjs().get(i);

	        if (temp.getId() == ObjectId.Block) {
	            Block block = (Block) temp;
	            
	         // Detectar colisión con bloques de meta (26, 27, 54, 55)
	            if (block.getIndex() == 26 || block.getIndex() == 27 || block.getIndex() == 54 || block.getIndex() == 55) {
	                if (getBounds().intersects(block.getBounds()) || 
	                    getBoundsTop().intersects(block.getBounds()) || 
	                    getBoundsLeft().intersects(block.getBounds()) || 
	                    getBoundsRight().intersects(block.getBounds())) {
	                	SoundPlayer.playSound(WIN_SOUND_PATH); // Reproducir el sonido al ganar
	                    handler.getGame().setGameState(GameState.WIN); // Cambia a WIN
	                }
	            }
	            // Detectar colisión con bloques de índice 15 (enemigos)
	            if (block.getIndex() == 15) {
	                // Si colisiona con los lados derecho o izquierdo del bloque
	                if (getBoundsRight().intersects(block.getBounds()) || getBoundsLeft().intersects(block.getBounds())) {
	                	SoundPlayer.playSound(LOSE_SOUND_PATH); // Reproducir el sonido al perder
	                    handler.getGame().setGameState(GameState.LOSE); // Cambia el estado a PERDER
	                }

	                // Si colisiona con la parte superior del bloque con la parte inferior del jugador
	                if (getBounds().intersects(block.getBounds())) {
	                    handler.removeObj(block); // Elimina el bloque del juego
	                }
	            }

	            // Colisión normal con el bloque
	            if (getBounds().intersects(block.getBounds())) {
	                setY(block.getY() - getHeight());
	                setVelY(0);
	                jumped = false;
	            }

	            // Colisión por arriba
	            if (getBoundsTop().intersects(block.getBounds())) {
	                setY(block.getY() + block.getHeight());
	                setVelY(0);
	            }

	            // Colisión por la derecha
	            if (getBoundsRight().intersects(block.getBounds())) {
	                setX(block.getX() - getWidth());
	            }

	            // Colisión por la izquierda
	            if (getBoundsLeft().intersects(block.getBounds())) {
	                setX(block.getX() + block.getWidth());
	            }
	        }
	    }
	}

	// Metodos para detectar las colisiones del jugador
	@Override
	public Rectangle getBounds() {
		return new Rectangle( (int) (getX() + getWidth()/2 - getWidth()/4),
							(int) (getY() + getHeight()/2),
							(int) getWidth()/2,
							(int) getHeight()/2);
	}
	public Rectangle getBoundsTop() {
		return new Rectangle((int) (getX() + getWidth()/2 - getWidth()/4),
							(int) getY(),
							(int) getWidth()/2,
							(int) getHeight()/2);
				
	}
	public Rectangle getBoundsRight() {
		return new Rectangle(   (int) (getX() + getWidth() - 5),
								(int) getY() + 5,
								5,
								(int) getHeight() - 10);
	}
	public Rectangle getBoundsLeft() {
		return new Rectangle( (int) getX(),
								 (int) (getY()+ 5),
								 5,
								 (int) (getHeight() - 10));
	}
	
	// Funcion para mostrar los bordes de colision del jugador
/*	private void showBounds(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.blue);
		g2d.draw(getBounds());
		g2d.draw(getBoundsRight());
		g2d.draw(getBoundsLeft());
		g2d.draw(getBoundsTop());
	}
	*/
	
	public boolean hasJumped() {
		return jumped;
	}
	
	public void setJumped(boolean hasJumped) {
		jumped = hasJumped;
	}
	
}
