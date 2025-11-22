package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class GestorJuego {

    private List<EntidadMovil> enemigos;
    private List<Bullet> balasJugador;
    private List<Bullet> balasEnemigas;
    private GestorRecursos recursos;

    public GestorJuego() {
        this.recursos = GestorRecursos.getInstance();
        this.enemigos = new ArrayList<>();
        this.balasJugador = new ArrayList<>();
        this.balasEnemigas = new ArrayList<>();
    }

    public void iniciarNivel(int cantAsteroides, int velX, int velY) {
        enemigos.clear();
        balasJugador.clear();
        balasEnemigas.clear();

        for (int i = 0; i < cantAsteroides; i++) {
            float x = MathUtils.random(0, Gdx.graphics.getWidth());
            float y = MathUtils.random(0, Gdx.graphics.getHeight());
            float vx = velX + MathUtils.random(-2, 2);
            float vy = velY + MathUtils.random(-2, 2);

            enemigos.add(new Ball2(x, y, vx, vy, recursos.getTxAsteroide()));
        }
    }

    public void agregarBalaJugador(Bullet b) {
        balasJugador.add(b);
    }

    public int actualizar(float delta, SpriteBatch batch, Nave4 nave) {
        int puntosGanados = 0;

        spawnEnemigosAleatorios();

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
                if (e instanceof IDestructible) {
                    aplicarDano((IDestructible) e, 100);
                }
                aplicarDano(nave, 1);
            }

            if (e instanceof IDestructible && ((IDestructible) e).estaDestruido()) {
                enemigos.remove(i--);
                recursos.getSndExplosion().play();
                puntosGanados += (e instanceof Ovni) ? 50 : 10;
            } else if (e.isDestroyed()) {
                enemigos.remove(i--);
            }
        }

        puntosGanados += manejarBalasJugador(batch);
        manejarBalasEnemigas(batch, nave);

        return puntosGanados;
    }

    private void spawnEnemigosAleatorios() {
        if (MathUtils.random(0, 500) < 2) {
            boolean grande = MathUtils.randomBoolean();
            float x = MathUtils.random(0, Gdx.graphics.getWidth());
            float y = MathUtils.random(0, Gdx.graphics.getHeight());
            enemigos.add(new Ovni(x, y, grande, recursos.getTxOvni()));
        }

        if (MathUtils.random(0, 600) < 1) {
            float x = -50;
            float y = MathUtils.random(0, Gdx.graphics.getHeight());
            enemigos.add(new AgujeroNegro(x, y, 3, 0, recursos.getTxAgujero()));
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
                if (e instanceof IDestructible) {
                    if (b.getArea().overlaps(e.getArea())) {
                        aplicarDano((IDestructible) e, 1);
                        b.destroyed = true;

                        if (((IDestructible) e).estaDestruido()) {
                            recursos.getSndExplosion().play();
                            enemigos.remove(j);
                            puntos += (e instanceof Ovni) ? 50 : 10;
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
        return enemigos.isEmpty();
    }
}