package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class Nave4 extends EntidadMovil implements IDestructible {

    private int vidas = 3;
    private boolean herido = false;
    private int tiempoHerido;
    private int tiempoHeridoMax = 50;
    
    private ArrayList<EstrategiaDisparo> inventarioArmas;
    private int indiceArmaActual;
    
    private Sound sonidoHerido;
    private Sound soundBala;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(x, y, 0, 0, tx);
        
        this.spr.setSize(40, 40);
        this.spr.setOriginCenter();
        
        this.sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        
        // Inicializar inventario
        this.inventarioArmas = new ArrayList<>();
        this.inventarioArmas.add(new DisparoBasico());
        this.indiceArmaActual = 0;
    }

    
    @Override
    protected void mover() {
        // Lógica de movimiento (Rotación y Empuje)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spr.rotate(4.0f);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spr.rotate(-4.0f);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            float rad = (float) Math.toRadians(spr.getRotation() + 90);
            xSpeed += Math.cos(rad) * 0.2f;
            ySpeed += Math.sin(rad) * 0.2f;
        }
        
        // Fricción
        xSpeed *= 0.98f;
        ySpeed *= 0.98f;
        
        // Aplicar velocidad
        spr.translate(xSpeed, ySpeed);
    }
    
    public void teletransportar() {
        // 1. Elegir nueva posición aleatoria dentro de la pantalla
        float newX = MathUtils.random(0, Gdx.graphics.getWidth() - getWidth());
        float newY = MathUtils.random(0, Gdx.graphics.getHeight() - getHeight());
        
        // 2. Mover el sprite
        spr.setPosition(newX, newY);
        
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    @Override
    protected void verificarLimites() {
        // Screen Wrapping (Pantalla Envolvente)
        if (spr.getX() > Gdx.graphics.getWidth()) spr.setX(-spr.getWidth());
        else if (spr.getX() + spr.getWidth() < 0) spr.setX(Gdx.graphics.getWidth());
        
        if (spr.getY() > Gdx.graphics.getHeight()) spr.setY(-spr.getHeight());
        else if (spr.getY() + spr.getHeight() < 0) spr.setY(Gdx.graphics.getHeight());
    }

    @Override
    protected void comportamientoEspecifico() {
        // Lógica única de la nave: Cambiar arma con tecla 'C'
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            cambiarArma();
        }
    }
    
    
    private void cambiarArma() {
        if (inventarioArmas.size() > 1) {
            indiceArmaActual++;
            if (indiceArmaActual >= inventarioArmas.size()) {
                indiceArmaActual = 0;
            }
        }
    }

    public void desbloquearEstrategia(EstrategiaDisparo nuevaEstrategia) {
        boolean yaLaTiene = false;
        for (EstrategiaDisparo e : inventarioArmas) {
            if (e.getClass() == nuevaEstrategia.getClass()) {
                yaLaTiene = true;
                break;
            }
        }
        if (!yaLaTiene) {
            inventarioArmas.add(nuevaEstrategia);
        }
    }
    
    public EstrategiaDisparo getEstrategia() {
        return inventarioArmas.get(indiceArmaActual);
    }

    public void disparar(ArrayList<Bullet> balas) {
        getEstrategia().disparar(balas, this);
        soundBala.play();
    }

    
    public boolean checkCollision(EntidadMovil b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            // Física de rebote simple
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
            if (vidas <= 0) destroyed = true;
        }
    }
    
    public void agregarVida() {
        this.vidas++;
    }

    @Override
    public boolean estaDestruido() { return destroyed; }

    @Override
    public void draw(SpriteBatch batch) {
        if (!herido) {
            super.draw(batch);
        } else {
            // Efecto de daño
            float xOriginal = spr.getX();
            spr.setX(xOriginal + MathUtils.random(-2, 2));
            super.draw(batch);
            spr.setX(xOriginal);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }
    
    // Método para reiniciar la posición al cambiar de nivel
    // conservando el inventario y la vida.
    public void reposicionar() {
        this.spr.setPosition(Gdx.graphics.getWidth()/2 - 50, 30);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.spr.setRotation(0);
        // No tocamos 'inventarioArmas' ni 'indiceArmaActual' para que se guarden.
    }
    
    public int getVidas() { return vidas; }
    public void setVidas(int vidas) { this.vidas = vidas; }
    public float getRotation() { return spr.getRotation(); }
    public float getWidth() { return spr.getWidth(); }
    public float getHeight() { return spr.getHeight(); }
}