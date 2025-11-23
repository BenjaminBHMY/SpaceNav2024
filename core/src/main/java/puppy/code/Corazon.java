package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Corazon extends EntidadMovil {

    public Corazon(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);
        this.spr.setSize(30, 30); 
        this.spr.setOriginCenter();
    }

    @Override
    protected void mover() {
        spr.translate(xSpeed, ySpeed);
    }

    @Override
    protected void comportamientoEspecifico() {
        spr.rotate(1.0f); 
    }

    @Override
    protected void verificarLimites() {
        // Si el jugador no lo agarra y sale de pantalla, se pierde para siempre
        if (spr.getX() > Gdx.graphics.getWidth() || spr.getX() + spr.getWidth() < 0 ||
            spr.getY() > Gdx.graphics.getHeight() || spr.getY() + spr.getHeight() < 0) {
            this.destroyed = true;
        }
    }
}