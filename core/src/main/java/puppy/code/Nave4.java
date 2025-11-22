package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 extends EntidadMovil implements IDestructible {

    private int vidas = 3;
    private boolean herido = false;
    private int tiempoHerido;
    private int tiempoHeridoMax = 50;
    
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(x, y, 0, 0, tx); // Velocidad inicial 0
        this.sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            spr.rotate(4.0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            spr.rotate(-4.0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            float anguloRadianes = (float) Math.toRadians(spr.getRotation() + 90);
            
            xSpeed += Math.cos(anguloRadianes) * 0.2f; // Aceleración
            ySpeed += Math.sin(anguloRadianes) * 0.2f;
        }

        xSpeed *= 0.98f;
        ySpeed *= 0.98f;

        spr.translate(xSpeed, ySpeed);

        if (spr.getX() > Gdx.graphics.getWidth()) spr.setX(-spr.getWidth());
        else if (spr.getX() + spr.getWidth() < 0) spr.setX(Gdx.graphics.getWidth());
        
        if (spr.getY() > Gdx.graphics.getHeight()) spr.setY(-spr.getHeight());
        else if (spr.getY() + spr.getHeight() < 0) spr.setY(Gdx.graphics.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!herido) {
            super.draw(batch);
        } else {
            float xOriginal = spr.getX();
            spr.setX(xOriginal + MathUtils.random(-2, 2));
            super.draw(batch);
            spr.setX(xOriginal);
            
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    public Bullet disparar() {
        float anguloRadianes = (float) Math.toRadians(spr.getRotation() + 90);
        float balaVel = 7.0f;
        
        float velX = (float) Math.cos(anguloRadianes) * balaVel;
        float velY = (float) Math.sin(anguloRadianes) * balaVel;
        
        float x = spr.getX() + spr.getWidth()/2 - 5;
        float y = spr.getY() + spr.getHeight()/2 - 5;

        soundBala.play();
        return new Bullet(x, y, velX, velY, txBala);
    }

    public boolean checkCollision(EntidadMovil b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            if (xSpeed == 0) xSpeed += b.getXSpeed() / 2;
            if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + xSpeed / 2);
            xSpeed = -xSpeed;
            b.setXSpeed(-b.getXSpeed());

            if (ySpeed == 0) ySpeed += b.getYSpeed() / 2;
            if (b.getYSpeed() == 0) b.setYSpeed(b.getYSpeed() + ySpeed / 2);
            ySpeed = -ySpeed;
            b.setYSpeed(-b.getYSpeed());
            
            return true; 
        }
        return false;
    }

    @Override
    public void recibirDano(int cantidad) {
        if (!herido) {
            vidas -= cantidad;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            
            if (vidas <= 0) {
                destroyed = true;
            }
        }
    }

    @Override
    public boolean estaDestruido() {
        return destroyed;
    }

    public int getVidas() { return vidas; }
    public void setVidas(int vidas) { this.vidas = vidas; }
    public boolean estaHerido() { return herido; }
}