package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class GestorRecursos {

    private static GestorRecursos instance;

    private Texture txNave;
    private Texture txBala;
    private Texture txBalaEnemiga;
    private Texture txOvni;
    private ArrayList<Texture> texturasAsteroides;
    private Texture txAgujero;
    private Texture txFondo; 
    private Texture txInicio;
    private Texture txGameOver;
    private Texture txOvniSmall1;
    private Texture txOvniSmall2; 
    private Texture txCorazon;

    private Sound sndChocar;
    private Sound sndHerido;
    private Sound sndDisparo;
    private Sound sndExplosion;
    private Music musicaFondo;
    private Sound sndVida;

    private GestorRecursos() {
        txInicio = new Texture(Gdx.files.internal("inicio.jpeg")); 
        txGameOver = new Texture(Gdx.files.internal("gameover.jpeg")); 
        txFondo = new Texture(Gdx.files.internal("fondo.jpeg")); 
        txNave = new Texture(Gdx.files.internal("nave.png"));
        txBala = new Texture(Gdx.files.internal("balaRoja2.png"));
        txBalaEnemiga = new Texture(Gdx.files.internal("balaAzul.gif"));
        txOvni = new Texture(Gdx.files.internal("ufo.png"));
        txOvniSmall1 = new Texture(Gdx.files.internal("ovni_s1.png"));
        txOvniSmall2 = new Texture(Gdx.files.internal("ovni_s2.png"));
        txAgujero = new Texture(Gdx.files.internal("agujeronegro.png"));
        txCorazon = new Texture(Gdx.files.internal("heart2.png"));
        
        texturasAsteroides = new ArrayList<>();
        texturasAsteroides.add(new Texture(Gdx.files.internal("asteroide1.png")));
        texturasAsteroides.add(new Texture(Gdx.files.internal("asteroide2.png")));
        texturasAsteroides.add(new Texture(Gdx.files.internal("asteroide3.png")));
        texturasAsteroides.add(new Texture(Gdx.files.internal("asteroide4.png")));
        texturasAsteroides.add(new Texture(Gdx.files.internal("asteroide5.png")));
        

        sndChocar = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
        sndHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        sndDisparo = Gdx.audio.newSound(Gdx.files.internal("laserSound.mp3"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        sndVida = Gdx.audio.newSound(Gdx.files.internal("vida.mp3"));

        musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        musicaFondo.setLooping(true);
        musicaFondo.setVolume(1.3f);
    }

    public static GestorRecursos getInstance() {
        if (instance == null) {
            instance = new GestorRecursos();
        }
        return instance;
    }
    
    public Texture getTxFondo() {
        return txFondo;
    }
    
    public Texture getTxInicio() { return txInicio; }
    
    public Texture getTxGameOver() { return txGameOver; }

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

    public Texture getTxAsteroideAleatorio() {
        if (texturasAsteroides.isEmpty()) return null;
        // Elige uno al azar de la lista
        int index = MathUtils.random(0, texturasAsteroides.size() - 1);
        return texturasAsteroides.get(index);
    }

    public Texture getTxOvni() {
        return txOvni;
    }
    
    public Texture getTxOvniSmall1() { return txOvniSmall1; }
    public Texture getTxOvniSmall2() { return txOvniSmall2; }

    public Texture getTxAgujero() {
        return txAgujero;
    }
    
    public Texture getTxCorazon() { return txCorazon; }

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
    
    public Sound getSndVida() {
        return sndVida;
    }

    public void dispose() {
        txFondo.dispose(); 
        if (txInicio != null) txInicio.dispose();
        if (txGameOver != null) txGameOver.dispose();
        txNave.dispose();
        txBala.dispose();
        txBalaEnemiga.dispose();
        for (Texture t : texturasAsteroides) {
            t.dispose();
        }
        txCorazon.dispose();
        txOvni.dispose();
        txOvniSmall1.dispose();
        txOvniSmall2.dispose();
        txAgujero.dispose();
        sndChocar.dispose();
        sndHerido.dispose();
        sndDisparo.dispose();
        sndExplosion.dispose();
        musicaFondo.dispose();
        sndVida.dispose();
    }
}