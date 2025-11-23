package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Ball2 extends EntidadMovil implements IDestructible {

    public Ball2(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);
        
        float size = com.badlogic.gdx.math.MathUtils.random(30, 60);
        this.spr.setSize(size, size);
        
        this.spr.setOriginCenter();
    }

    @Override
    protected void mover() {
        // Movimiento inercial simple
        spr.translate(xSpeed, ySpeed);
    }

    @Override
    protected void verificarLimites() {
        // Lógica de Screen Wrapping
        if (spr.getX() > Gdx.graphics.getWidth()) spr.setX(-spr.getWidth());
        else if (spr.getX() + spr.getWidth() < 0) spr.setX(Gdx.graphics.getWidth());
        
        if (spr.getY() > Gdx.graphics.getHeight()) spr.setY(-spr.getHeight());
        else if (spr.getY() + spr.getHeight() < 0) spr.setY(Gdx.graphics.getHeight());
    }

    @Override
    public void recibirDano(int cantidad) {
        this.destroyed = true;
    }

    @Override
    public boolean estaDestruido() { return destroyed; }
}