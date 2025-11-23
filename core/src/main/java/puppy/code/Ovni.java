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
            this.vidas = 5; 
            this.spr.setSize(70, 70);
            this.xSpeed = MathUtils.random(-1.0f, 1.0f); 
            this.ySpeed = MathUtils.random(-1.0f, 1.0f);
            this.tiempoEntreDisparos = 3.5f; 
        } else {
            this.vidas = 1;
            this.spr.setSize(30, 30);
            this.xSpeed = MathUtils.random(-3.0f, 3.0f); 
            this.ySpeed = MathUtils.random(-3.0f, 3.0f);
            this.tiempoEntreDisparos = 2.0f; 
        }
        this.spr.setOriginCenter();
    }

    @Override
    protected void mover() {
        spr.translate(xSpeed, ySpeed);
    }

    @Override
    protected void comportamientoEspecifico() {
        if (MathUtils.random(0, 100) < 1) { 
            if (esGrande) {
                xSpeed = MathUtils.random(-1.0f, 1.0f);
                ySpeed = MathUtils.random(-1.0f, 1.0f);
            } else {
                xSpeed = MathUtils.random(-3.0f, 3.0f);
                ySpeed = MathUtils.random(-3.0f, 3.0f);
            }
        }
    }

    @Override
    protected void verificarLimites() {
        if (spr.getX() > Gdx.graphics.getWidth()) spr.setX(-spr.getWidth());
        else if (spr.getX() + spr.getWidth() < 0) spr.setX(Gdx.graphics.getWidth());
        if (spr.getY() > Gdx.graphics.getHeight()) spr.setY(-spr.getHeight());
        else if (spr.getY() + spr.getHeight() < 0) spr.setY(Gdx.graphics.getHeight());
    }

    public Bullet disparar(Nave4 objetivo, Texture txBala, float delta) {
        tiempoAcumulado += delta;

        if (tiempoAcumulado >= tiempoEntreDisparos) {
            tiempoAcumulado = 0;
            
            float origenX = spr.getX() + spr.getWidth() / 2;
            float origenY = spr.getY() + spr.getHeight() / 2;
            
            // Apuntar al jugador
            float destinoX = objetivo.getX() + 22; 
            float destinoY = objetivo.getY() + 22;

            float angulo = MathUtils.atan2(destinoY - origenY, destinoX - origenX);
            
            float velBala = 4.0f; 
            
            float velX = MathUtils.cos(angulo) * velBala;
            float velY = MathUtils.sin(angulo) * velBala;
            
            Bullet b = new Bullet(origenX, origenY, velX, velY, txBala);
            
            if (esGrande) {
                b.setSize(60, 60); // Balas gigantes para el OVNI tanque
            } else {
                b.setSize(30, 30); // Balas medianas para el OVNI pequeño
            }


            return b;
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
    
    public int getPuntaje() {
        return esGrande ? 50 : 25;
    }
}