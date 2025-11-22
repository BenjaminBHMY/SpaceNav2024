package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Ovni extends EntidadMovil implements IDestructible {

    private int vidas;
    private float tiempoEntreDisparos;
    private float tiempoAcumulado;
    private boolean esGrande;

    public Ovni(float x, float y, boolean esGrande, Texture tx) {
        super(x, y, 0, 0, tx);
        this.esGrande = esGrande;

        if (esGrande) {
            // OVNI Grande: Lento, resistente, dispara lento
            this.vidas = 3;
            this.spr.setSize(60, 60);
            this.xSpeed = MathUtils.random(-2f, 2f);
            this.ySpeed = MathUtils.random(-2f, 2f);
            this.tiempoEntreDisparos = 2.0f;
        } else {
            // OVNI Pequeño: Rápido, frágil, dispara rápido
            this.vidas = 1;
            this.spr.setSize(30, 30);
            this.xSpeed = MathUtils.random(-5f, 5f);
            this.ySpeed = MathUtils.random(-5f, 5f);
            this.tiempoEntreDisparos = 1.0f;
        }
        
        this.spr.setOriginCenter();
    }

    @Override
    public void update() {
        spr.translate(xSpeed, ySpeed);

        if (spr.getX() > Gdx.graphics.getWidth()) spr.setX(-spr.getWidth());
        else if (spr.getX() + spr.getWidth() < 0) spr.setX(Gdx.graphics.getWidth());

        if (spr.getY() > Gdx.graphics.getHeight()) spr.setY(-spr.getHeight());
        else if (spr.getY() + spr.getHeight() < 0) spr.setY(Gdx.graphics.getHeight());

        if (MathUtils.random(0, 100) < 2) { 
            if (esGrande) {
                xSpeed = MathUtils.random(-2f, 2f);
                ySpeed = MathUtils.random(-2f, 2f);
            } else {
                xSpeed = MathUtils.random(-5f, 5f);
                ySpeed = MathUtils.random(-5f, 5f);
            }
        }
    }

    public Bullet disparar(Nave4 objetivo, Texture txBala, float delta) {
        tiempoAcumulado += delta;

        if (tiempoAcumulado >= tiempoEntreDisparos) {
            tiempoAcumulado = 0;

            float origenX = spr.getX() + spr.getWidth() / 2;
            float origenY = spr.getY() + spr.getHeight() / 2;
            
            float destinoX = objetivo.getX() + 22; 
            float destinoY = objetivo.getY() + 22;

            float angulo = MathUtils.atan2(destinoY - origenY, destinoX - origenX);
            float velBala = 6.0f;
            
            float velX = MathUtils.cos(angulo) * velBala;
            float velY = MathUtils.sin(angulo) * velBala;

            return new Bullet(origenX, origenY, velX, velY, txBala);
        }
        return null;
    }

    @Override
    public void recibirDano(int cantidad) {
        this.vidas -= cantidad;
        if (this.vidas <= 0) {
            this.destroyed = true;
        }
    }

    @Override
    public boolean estaDestruido() {
        return destroyed;
    }
}