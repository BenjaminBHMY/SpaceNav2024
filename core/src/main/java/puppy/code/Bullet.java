package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bullet extends EntidadMovil {

    public Bullet(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);
        this.spr.setSize(10, 20); 
        this.spr.setOriginCenter();
    }
    
    public void setSize(float ancho, float alto) {
        this.spr.setSize(ancho, alto);
        this.spr.setOriginCenter();
    }
    
    @Override
    protected void mover() {
        spr.translate(xSpeed, ySpeed);
    }

    @Override
    protected void verificarLimites() {
        // Destruir al salir
        if (spr.getX() < 0 || spr.getX() + spr.getWidth() > Gdx.graphics.getWidth() ||
            spr.getY() < 0 || spr.getY() + spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }
}