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

    public abstract void update();

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public float getX() {
        return spr.getX();
    }

    public float getY() {
        return spr.getY();
    }
    
    public float getXSpeed() {
        return xSpeed;
    }
    
    public float getYSpeed() {
        return ySpeed;
    }
    
    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
}