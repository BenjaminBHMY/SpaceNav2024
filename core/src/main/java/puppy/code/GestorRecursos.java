package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GestorRecursos {

    private static GestorRecursos instance;

    private Texture txNave;
    private Texture txBala;
    private Texture txBalaEnemiga;
    private Texture txAsteroide;
    private Texture txOvni;
    private Texture txAgujero;

    private Sound sndChocar;
    private Sound sndHerido;
    private Sound sndDisparo;
    private Sound sndExplosion;
    private Music musicaFondo;

    private GestorRecursos() {
        txNave = new Texture(Gdx.files.internal("MainShip3.png"));
        txBala = new Texture(Gdx.files.internal("Rocket2.png"));
        txBalaEnemiga = new Texture(Gdx.files.internal("Rocket2.png"));
        txAsteroide = new Texture(Gdx.files.internal("aGreyMedium4.png"));
        txOvni = new Texture(Gdx.files.internal("ufo.png"));
        txAgujero = new Texture(Gdx.files.internal("agujeronegro.png"));

        sndChocar = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        sndHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        sndDisparo = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));

        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(0.5f);
    }

    public static GestorRecursos getInstance() {
        if (instance == null) {
            instance = new GestorRecursos();
        }
        return instance;
    }

    public void reproducirMusica() {
        musicaFondo.play();
    }

    public void detenerMusica() {
        musicaFondo.stop();
    }

    public Texture getTxNave() {
        return txNave;
    }

    public Texture getTxBala() {
        return txBala;
    }
    
    public Texture getTxBalaEnemiga() {
        return txBalaEnemiga;
    }

    public Texture getTxAsteroide() {
        return txAsteroide;
    }

    public Texture getTxOvni() {
        return txOvni;
    }

    public Texture getTxAgujero() {
        return txAgujero;
    }

    public Sound getSndChocar() {
        return sndChocar;
    }

    public Sound getSndHerido() {
        return sndHerido;
    }

    public Sound getSndDisparo() {
        return sndDisparo;
    }

    public Sound getSndExplosion() {
        return sndExplosion;
    }

    public void dispose() {
        txNave.dispose();
        txBala.dispose();
        txBalaEnemiga.dispose();
        txAsteroide.dispose();
        txOvni.dispose();
        txAgujero.dispose();
        sndChocar.dispose();
        sndHerido.dispose();
        sndDisparo.dispose();
        sndExplosion.dispose();
        musicaFondo.dispose();
    }
}