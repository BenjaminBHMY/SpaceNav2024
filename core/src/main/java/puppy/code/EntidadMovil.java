package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * CLASE ABSTRACTA
 * Define una plantilla para cualquier objeto del juego que se mueve y 
 * puede ser destruido.
 *
 * Cumple con el requisito GM1.4.
 */
public abstract class EntidadMovil {

    // --- ATRIBUTOS COMUNES ---
    // 'protected' significa que esta clase y sus clases hijas (Bullet, AgujeroNegro)
    // pueden acceder a estas variables directamente.
    
    protected Sprite spr;
    protected float xSpeed;
    protected float ySpeed;
    protected boolean destroyed = false;

    /**
     * Constructor base. Lo llamarán las clases hijas.
     */
    public EntidadMovil(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setOriginCenter(); // Buena práctica que establecimos
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    // --- MÉTODOS ABSTRACTOS ---
    
    /**
     * El método 'update' es abstracto porque CADA entidad hija
     * definirá su propia lógica de movimiento (algunas rebotan, 
     * otras se destruyen, otras envuelven la pantalla).
     */
    public abstract void update();


    // --- MÉTODOS COMUNES (Concretos) ---
    // Estas funciones son idénticas para todas las entidades, 
    // así que las definimos aquí una sola vez.

    /**
     * Dibuja el sprite en la pantalla.
     */
    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    /**
     * Devuelve el rectángulo de colisión.
     */
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    /**
     * Informa si la entidad debe ser eliminada.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Métodos 'getter' para la posición (útiles).
     */
    public float getX() {
        return spr.getX();
    }

    public float getY() {
        return spr.getY();
    }
}