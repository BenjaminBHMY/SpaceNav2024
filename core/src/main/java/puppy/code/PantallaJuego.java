package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class PantallaJuego implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private GestorRecursos recursos;
    private GestorJuego gestorJuego;
    
    private Nave4 nave;
    private int score;
    private int ronda;

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score, 
                         int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;

        this.batch = game.getBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 640);

        this.recursos = GestorRecursos.getInstance();
        
        this.nave = new Nave4(
            Gdx.graphics.getWidth() / 2 - 50, 
            30, 
            recursos.getTxNave(), 
            recursos.getSndHerido(), 
            recursos.getTxBala(), 
            recursos.getSndDisparo()
        );
        this.nave.setVidas(vidas);

        this.gestorJuego = new GestorJuego();
        this.gestorJuego.iniciarNivel(cantAsteroides, velXAsteroides, velYAsteroides);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        dibujaEncabezado();

        nave.update();
        nave.draw(batch);

        int puntosFrame = gestorJuego.actualizar(delta, batch, nave);
        score += puntosFrame;
        
        //aumento de arma al llegar a cierto puntaje
        if (score >= 40)  nave.desbloquearNivel(2); // Doble
        if (score >= 100) nave.desbloquearNivel(3); // Triple
        if (score >= 200) nave.desbloquearNivel(4); // Linea
        if (score >= 230) nave.desbloquearNivel(5); // X
        if (score >= 280) nave.desbloquearNivel(6); // Abanico
        if (score >= 310) nave.desbloquearNivel(7); // Circular

        //control de teclas
        if (!nave.estaDestruido()) {
            
            // ESPACIO: Básico (Nivel 1 - Siempre disponible)
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                dispararConEstrategia(new DisparoBasico());
            }

            // tecla Z: Doble (Nivel 2)
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
                if (nave.getNivelArmaDesbloqueado() >= 2) dispararConEstrategia(new DisparoDoble());
            }

            // tecla X: Triple (Nivel 3)
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                if (nave.getNivelArmaDesbloqueado() >= 3) dispararConEstrategia(new DisparoTriple());
            }

            // tecla C: Largo (Nivel 4)
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                if (nave.getNivelArmaDesbloqueado() >= 4) dispararConEstrategia(new DisparoLinea());
            }

            // tecla V: Abanico (Nivel 5)
            if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
                if (nave.getNivelArmaDesbloqueado() >= 5) dispararConEstrategia(new DisparoX());
            }

            // tecla B: Cruz (Nivel 6)
            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                if (nave.getNivelArmaDesbloqueado() >= 6) dispararConEstrategia(new DisparoAbanico());
            }

            // tecla N: Circular
            if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                if (nave.getNivelArmaDesbloqueado() >= 7) dispararConEstrategia(new DisparoCircular());
            }
        }

        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            game.setScreen(new PantallaGameOver(game));
            dispose();
        }
        
        if (gestorJuego.nivelCompletado()) {
            game.setScreen(new PantallaJuego(game, ronda + 1, nave.getVidas(), score, 
                                             3, 3, 5 + ronda * 2));
            dispose();
        }

        batch.end();
    }

    private void dibujaEncabezado() {
        CharSequence str = "Vidas: " + nave.getVidas() + " Ronda: " + ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score: " + this.score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore: " + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void show() {
        recursos.reproducirMusica();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
    //disparo con distinta tecla
    private void dispararConEstrategia(EstrategiaDisparo nuevaEstrategia) {
        // cambiamos la estrategia de la nave
        nave.setEstrategia(nuevaEstrategia);
        
        // pedir a la nave que dispare con esa nueva estrategia
        java.util.ArrayList<Bullet> balas = nave.disparar();
        
        // agregamos las balas al juego
        if (balas != null) {
            for (Bullet b : balas) {
                gestorJuego.agregarBalaJugador(b);
            }
        }
    }
}