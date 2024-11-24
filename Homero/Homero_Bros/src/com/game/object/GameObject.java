package com.game.object;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.object.util.ObjectId;

public abstract class GameObject {

    // Posición del objeto
    private float x;
    private float y;

    // Identificador del objeto
    private ObjectId id;

    // Velocidades en X e Y
    private float velX, velY;

    // Dimensiones del objeto
    private float width, height;

    // Escala del objeto
    private int scale;

    /**
     * Constructor de la clase GameObject.
     * 
     *  x      Posición inicial en X
     *  y      Posición inicial en Y
     * id     Identificador del objeto
     * width  Ancho del objeto
     * height Altura del objeto
     * scale  Escala del objeto
     */
    public GameObject(float x, float y, ObjectId id, float width, float height, int scale) {
        this.x = x * scale;
        this.y = y * scale;
        this.id = id;
        this.width = width * scale;
        this.height = height * scale;
        this.scale = scale;
    }

    // Métodos abstractos que deben implementar las subclases
    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    
     // Aplica gravedad al objeto aumentando su velocidad en Y. 
    public void applyGravity() {
        velY += 0.5f;
    }

    // Métodos setter y getter para acceder y modificar las propiedades del objeto
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void setWidth(float width) {
        this.width = width * scale;
    }

    public void setHeight(float height) {
        this.height = height * scale;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ObjectId getId() {
        return id;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}

