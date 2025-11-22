package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !nave.estaDestruido()) {
             Bullet b = nave.disparar();
             if (b != null) {
                 gestorJuego.agregarBalaJugador(b);
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
                                             3, 3, 10 + ronda * 2));
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
}