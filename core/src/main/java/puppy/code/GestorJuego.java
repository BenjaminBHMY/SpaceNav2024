package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class GestorJuego {

    private List<EntidadMovil> enemigos;
    private ArrayList<Bullet> balasJugador;
    private ArrayList<Bullet> balasEnemigas;
    private List<EntidadMovil> powerups;
    private GestorRecursos recursos;
    private FabricaAmenazas fabricaAmenazas;
    
    private int rondaActual;

    public GestorJuego() {
        this.recursos = GestorRecursos.getInstance();
        this.enemigos = new ArrayList<>();
        this.balasJugador = new ArrayList<>();
        this.balasEnemigas = new ArrayList<>();
        this.fabricaAmenazas = new AmenazasNivelBajo(); 
        this.powerups = new ArrayList<>();
        this.rondaActual = 1;
    }

    public void iniciarNivel(int cantAsteroides, int ronda) {
        enemigos.clear();
        balasJugador.clear();
        balasEnemigas.clear();
        powerups.clear(); 
        
        this.rondaActual = ronda; 

        // Selección de fábrica según ronda
        if (ronda < 3) {
            this.fabricaAmenazas = new AmenazasNivelBajo();
        } else {
            this.fabricaAmenazas = new AmenazasNivelAlto();
        }

        // Crear Asteroides iniciales
        for (int i = 0; i < cantAsteroides; i++) {
            enemigos.add(fabricaAmenazas.crearAsteroide());
        }
    }
    
    public ArrayList<Bullet> getBalasJugador() {
        return balasJugador;
    }

    public int actualizar(float delta, SpriteBatch batch, Nave4 nave, int scoreActual) {
        int puntosGanados = 0;

        spawnEnemigos();
        verificarEvolucionArmas(nave, scoreActual);

        for (int i = 0; i < enemigos.size(); i++) {
            EntidadMovil e = enemigos.get(i);
            e.update();
            e.draw(batch);

            if (e instanceof Ovni) {
                Ovni o = (Ovni) e;
                if (!nave.estaDestruido()) {
                    Bullet bala = o.disparar(nave, recursos.getTxBalaEnemiga(), delta);
                    if (bala != null) balasEnemigas.add(bala);
                }
            }

            if (nave.checkCollision(e)) {
                
                // CASO ESPECIAL: AGUJERO NEGRO
                if (e instanceof AgujeroNegro) {
                    nave.teletransportar();
                    enemigos.remove(i--);
                } 
                // CASO ESTÁNDAR: ENEMIGOS FÍSICOS (Ovnis, Asteroides)
                else {
                    if (e instanceof IDestructible) {
                        aplicarDano((IDestructible) e, 100);
                    }
                    aplicarDano(nave, 1);
                }
            }

            if (e instanceof IDestructible && ((IDestructible) e).estaDestruido()) {
                enemigos.remove(i--);
                recursos.getSndExplosion().play();
                if (e instanceof Ovni) {
                    puntosGanados += ((Ovni) e).getPuntaje(); // 25 o 50
                } else {
                    puntosGanados += 10; // Asteroide
                }
            } else if (e.isDestroyed()) { 
                enemigos.remove(i--);
            }
        }
        
        for (int i = 0; i < powerups.size(); i++) {
            EntidadMovil p = powerups.get(i);
            p.update();
            p.draw(batch);

            if (nave.getArea().overlaps(p.getArea())) {
                nave.agregarVida();

                if (recursos.getSndVida() != null) {
                    recursos.getSndVida().play();
                }

                powerups.remove(i--);
            }
        }

        puntosGanados += manejarBalasJugador(batch);
        manejarBalasEnemigas(batch, nave);

        return puntosGanados;
    }

    private void verificarEvolucionArmas(Nave4 nave, int score) {
        if (score >= 600) nave.desbloquearEstrategia(new DisparoCircular());
        else if (score >= 500) nave.desbloquearEstrategia(new DisparoAbanico());
        else if (score >= 400) nave.desbloquearEstrategia(new DisparoX());
        else if (score >= 300) nave.desbloquearEstrategia(new DisparoLinea());
        else if (score >= 200) nave.desbloquearEstrategia(new DisparoTriple());
        else if (score >= 100) nave.desbloquearEstrategia(new DisparoDoble());
    }

    private int contarOvnisVivos() {
        int count = 0;
        for (EntidadMovil e : enemigos) {
            if (e instanceof Ovni) count++;
        }
        return count;
    }

    private void spawnEnemigos() {
        
        int maxOvnisSimultaneos = 1 + (rondaActual / 2); 
        
        if (contarOvnisVivos() < maxOvnisSimultaneos) {
            if (MathUtils.random(0, 500) < 2) {
                enemigos.add(fabricaAmenazas.crearEnemigo());
            }
        }
        
        if (MathUtils.random(0, 2000) < 1) {
         float x = MathUtils.random(50, Gdx.graphics.getWidth() - 50);
         float y = Gdx.graphics.getHeight(); // Aparece arriba y baja

         // Cae lentamente
         powerups.add(new Corazon(x, y, 0, -1.5f, recursos.getTxCorazon()));
        }
        
        if (MathUtils.random(0, 400) < 1) { 
             float x = MathUtils.random(50, Gdx.graphics.getWidth() - 50); 
             float y = MathUtils.random(50, Gdx.graphics.getHeight() - 50);
             
             // Velocidad lenta
             float vx = MathUtils.random(-1f, 1f);
             float vy = MathUtils.random(-1f, 1f);
             
             enemigos.add(new AgujeroNegro(x, y, vx, vy, recursos.getTxAgujero()));
        }
    }

    private int manejarBalasJugador(SpriteBatch batch) {
        int puntos = 0;
        for (int i = 0; i < balasJugador.size(); i++) {
            Bullet b = balasJugador.get(i);
            b.update();
            b.draw(batch);

            for (int j = 0; j < enemigos.size(); j++) {
                EntidadMovil e = enemigos.get(j);
                // Solo chocamos con cosas destructibles
                if (e instanceof IDestructible) {
                    if (b.getArea().overlaps(e.getArea())) {
                        aplicarDano((IDestructible) e, 1);
                        b.destroyed = true;

                        if (((IDestructible) e).estaDestruido()) {
                            recursos.getSndExplosion().play();
                            enemigos.remove(j);
                            if (e instanceof Ovni) {
                                puntos += ((Ovni) e).getPuntaje(); // 25 o 50
                            } else {
                                puntos += 10; // Asteroide
                            }
                        }
                        break;
                    }
                }
            }

            if (b.isDestroyed()) {
                balasJugador.remove(i--);
            }
        }
        return puntos;
    }

    private void manejarBalasEnemigas(SpriteBatch batch, Nave4 nave) {
        for (int i = 0; i < balasEnemigas.size(); i++) {
            Bullet b = balasEnemigas.get(i);
            b.update();
            b.draw(batch);

            if (nave.checkCollision(b)) {
                aplicarDano(nave, 1);
                b.destroyed = true;
            }

            if (b.isDestroyed()) {
                balasEnemigas.remove(i--);
            }
        }
    }

    private void aplicarDano(IDestructible obj, int cant) {
        obj.recibirDano(cant);
    }

    public boolean nivelCompletado() {
        
        for (EntidadMovil e : enemigos) {
            if (e instanceof Ball2) {
                return false; // Todavía quedan rocas, sigue jugando.
            }
        }
        return true; 
    }
}