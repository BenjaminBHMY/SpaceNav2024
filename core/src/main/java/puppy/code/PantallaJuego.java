package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils; // Importar MathUtils


public class PantallaJuego implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private int score;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;

    private Nave4 nave;
    private ArrayList<Ball2> balls1 = new ArrayList<>();
    private ArrayList<Ball2> balls2 = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();

    // --- NUEVO: Lista y Textura para Agujeros Negros ---
    private ArrayList<AgujeroNegro> agujerosNegros = new ArrayList<>();
    private Texture txAgujeroNegro;


    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
            int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        //inicializar assets
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        // --- NUEVO: Cargar la textura del agujero negro ---
        // !!! Asegúrate de tener esta imagen en tu carpeta 'assets' !!!
        txAgujeroNegro = new Texture(Gdx.files.internal("agujeronegro.png"));

        // cargar imagen de la nave
        nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas);

        //crear asteroides
        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            // (Tu código de 'new Ball2' corregido)
            Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
                50+r.nextInt((int)Gdx.graphics.getHeight()-50),
                velXAsteroides+r.nextInt(4), // xSpeed
                velYAsteroides+r.nextInt(4), // ySpeed
                new Texture(Gdx.files.internal("aGreyMedium4.png")));
            balls1.add(bb);
            balls2.add(bb);
        }
    }

    // --- NUEVO: Método para crear un agujero negro ---
    private void spawnAgujeroNegro() {
        // Posición aleatoria en un borde de la pantalla
        float x, y;
        int borde = MathUtils.random(1, 4); // 1=izq, 2=der, 3=arriba, 4=abajo

        if (borde == 1) { // Izquierda
            x = -txAgujeroNegro.getWidth();
            y = MathUtils.random(0, Gdx.graphics.getHeight());
        } else if (borde == 2) { // Derecha
            x = Gdx.graphics.getWidth();
            y = MathUtils.random(0, Gdx.graphics.getHeight());
        } else if (borde == 3) { // Arriba
            x = MathUtils.random(0, Gdx.graphics.getWidth());
            y = Gdx.graphics.getHeight();
        } else { // Abajo
            x = MathUtils.random(0, Gdx.graphics.getWidth());
            y = -txAgujeroNegro.getHeight();
        }

        // Velocidad aleatoria dirigida hacia el centro de la pantalla
        float velX = (Gdx.graphics.getWidth()/2 - x) / 200; // Ajusta el '200' para cambiar velocidad
        float velY = (Gdx.graphics.getHeight()/2 - y) / 200;

        AgujeroNegro ah = new AgujeroNegro(x, y, velX, velY, txAgujeroNegro);
        agujerosNegros.add(ah);
    }

    public void dibujaEncabezado() {
        // (Tu código de encabezado sin cambios)
        CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
        game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        dibujaEncabezado();

        // --- NUEVO: Lógica de Spawning de Agujeros Negros ---
        // Una pequeña probabilidad en cada frame
        // Ajusta el '500' para que sea más o menos frecuente
        if (MathUtils.random(0, 500) < 1) {
            // Solo si no hay ya uno en pantalla (para que no se llene)
            if (agujerosNegros.size() == 0) {
                spawnAgujeroNegro();
            }
        }

        if (!nave.estaHerido()) {
            // colisiones entre balas y asteroides...
            for (int i = 0; i < balas.size(); i++) {
                Bullet b = balas.get(i);
                b.update();
                for (int j = 0; j < balls1.size(); j++) {
                    if (b.checkCollision(balls1.get(j))) {
                        explosionSound.play();
                        balls1.remove(j);
                        balls2.remove(j);
                        j--;
                        score +=10;
                    }
                }

                if (b.isDestroyed()) {
                    balas.remove(b);
                    i--;
                }
            }
            //actualizar movimiento de asteroides
            for (Ball2 ball : balls1) {
                ball.update();
            }
            //colisiones entre asteroides
            for (int i=0;i<balls1.size();i++) {
                Ball2 ball1 = balls1.get(i);
                for (int j=0;j<balls2.size();j++) {
                    Ball2 ball2 = balls2.get(j);
                    if (i<j) {
                        ball1.checkCollision(ball2);
                    }
                }
            }
        }
        //dibujar balas
        for (Bullet b : balas) {
            b.draw(batch);
        }

        nave.draw(batch, this);

        // --- NUEVO: Dibujar y actualizar Agujeros Negros ---
        for (int i = 0; i < agujerosNegros.size(); i++) {
            AgujeroNegro ah = agujerosNegros.get(i);
            ah.update(); // Mover y chequear si está fuera de pantalla

            if (ah.isDestroyed()) {
                agujerosNegros.remove(i);
                i--;
            } else {
                ah.draw(batch); // Dibujar si no está destruido
            }
        }

        //dibujar asteroides y manejar colision con nave
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 b=balls1.get(i);
            b.draw(batch);
            //perdió vida o game over
            if (nave.checkCollision(b)) {
                //asteroide se destruye con el choque
                balls1.remove(i);
                balls2.remove(i);
                i--;
            }
        }

        // --- NUEVO: Colisión Nave vs Agujero Negro ---
        for (int i = 0; i < agujerosNegros.size(); i++) {
            AgujeroNegro ah = agujerosNegros.get(i);

            // Usamos el nuevo método de colisión que creamos en Nave4
            if (nave.checkCollision(ah)) {
                // Si hay colisión, la nave se teletransporta (hecho en Nave4)
                // Y el agujero negro se destruye
                agujerosNegros.remove(i);
                i--;
                // (Opcional: reproducir sonido de warp)
                // soundWarp.play();
            }
        }

        if (nave.estaDestruido()) {
            // (Tu lógica de Game Over)
            if (score > game.getHighScore())
                game.setHighScore(score);
            Screen ss = new PantallaGameOver(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
        batch.end();
        //nivel completado
        if (balls1.size()==0) {
            // (Tu lógica de Siguiente Nivel)
            Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score,
                velXAsteroides+3, velYAsteroides+3, cantAsteroides+10);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }

    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        this.explosionSound.dispose();
        this.gameMusic.dispose();
        // --- NUEVO: Liberar la textura del agujero negro ---
        this.txAgujeroNegro.dispose();
    }

}