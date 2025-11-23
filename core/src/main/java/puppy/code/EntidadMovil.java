package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class EntidadMovil {

    protected Sprite spr;
    protected float xSpeed;
    protected float ySpeed;
    protected boolean destroyed = false;

    public EntidadMovil(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setOriginCenter();
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public final void update() {
        mover();
        
        comportamientoEspecifico();
        
        verificarLimites();
    }


    protected abstract void mover();

    protected void comportamientoEspecifico() {} 

    protected abstract void verificarLimites();

    // --- Métodos Comunes ---
    public void draw(SpriteBatch batch) { spr.draw(batch); }
    public Rectangle getArea() { return spr.getBoundingRectangle(); }
    public boolean isDestroyed() { return destroyed; }
    public float getX() { return spr.getX(); }
    public float getY() { return spr.getY(); }
    public float getXSpeed() { return xSpeed; }
    public float getYSpeed() { return ySpeed; }
    public void setXSpeed(float x) { this.xSpeed = x; }
    public void setYSpeed(float y) { this.ySpeed = y; }
    
    // Métodos utilitarios para las subclases
    public float getRotation() { return spr.getRotation(); }
    public float getWidth() { return spr.getWidth(); }
    public float getHeight() { return spr.getHeight(); }
}