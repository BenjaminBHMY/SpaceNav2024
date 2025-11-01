package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 {

    // --- NUEVO: Constantes para la física de la nave ---
    private final float VELOCIDAD_ROTACION = 4.0f; // Grados por frame
    private final float ACELERACION = 0.15f;       // Unidades de velocidad por frame
    private final float FRICCION = 0.99f;          // 1.0 = sin fricción, 0.9 = mucha fricción
    private final float VELOCIDAD_BALA = 7.0f;     // Velocidad de la bala

    private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 45, 45); // Mantenemos tu tamaño
        
        // --- MODIFICADO: ¡Esto es CRUCIAL para que rote sobre su centro! ---
        spr.setOriginCenter(); 
    }

    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();

        if (!herido) {
            
            // --- MODIFICADO: Lógica de Rotación y Aceleración (estilo Asteroids) ---
            
            // 1. Rotación: Usamos isKeyPressed para rotación continua
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                spr.rotate(VELOCIDAD_ROTACION); // Gira en sentido antihorario
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                spr.rotate(-VELOCIDAD_ROTACION); // Gira en sentido horario
            }

            // 2. Aceleración (Empuje): Usamos isKeyPressed para acelerar
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                // Asumimos que la textura de la nave apunta hacia ARRIBA (UP).
                // En LibGDX, 0° es DERECHA, así que añadimos 90° a la rotación.
                float anguloRadianes = (float) Math.toRadians(spr.getRotation() + 90);
                
                // Calculamos el empuje en X e Y usando trigonometría
                xVel += Math.cos(anguloRadianes) * ACELERACION;
                yVel += Math.sin(anguloRadianes) * ACELERACION;
            }

            // 3. Aplicar Fricción: La nave se frena lentamente en el espacio
            xVel *= FRICCION;
            yVel *= FRICCION;

            // 4. Actualizar posición basada en la velocidad
            spr.translate(xVel, yVel); // .translate() es mejor que setPosition para esto

            // --- MODIFICADO: Lógica de Pantalla Envolvente (Screen Wrapping) ---
            
            float anchoPantalla = Gdx.graphics.getWidth();
            float altoPantalla = Gdx.graphics.getHeight();
            
            // Obtenemos la nueva posición después de movernos
            x = spr.getX();
            y = spr.getY();

            // Envolver en eje X (Horizontal)
            if (x > anchoPantalla) {
                spr.setX(0 - spr.getWidth()); // Si se sale por la derecha, aparece en la izquierda
            } else if (x + spr.getWidth() < 0) {
                spr.setX(anchoPantalla); // Si se sale por la izquierda, aparece en la derecha
            }

            // Envolver en eje Y (Vertical)
            if (y > altoPantalla) {
                spr.setY(0 - spr.getHeight()); // Si se sale por arriba, aparece abajo
            } else if (y + spr.getHeight() < 0) {
                spr.setY(altoPantalla); // Si se sale por abajo, aparece arriba
            }

            spr.draw(batch); // Dibujamos la nave (SpriteBatch se encarga de la rotación)

        } else {
            // Lógica de "herido" (parpadeo o temblor)
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x); // Devolvemos la X a su posición original para el próximo frame
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        // --- MODIFICADO: Lógica de Disparo ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            
            // Calcular ángulo de la bala (igual que el empuje)
            float anguloRadianes = (float) Math.toRadians(spr.getRotation() + 90);
            
            // Calcular velocidad de la bala
            float balaVelX = (float) Math.cos(anguloRadianes) * VELOCIDAD_BALA;
            float balaVelY = (float) Math.sin(anguloRadianes) * VELOCIDAD_BALA;

            // Calcular posición inicial de la bala (en el centro/nariz de la nave)
            // (spr.getX() + spr.getOriginX()) es el centro X de la nave
            float spawnX = spr.getX() + spr.getOriginX();
            float spawnY = spr.getY() + spr.getOriginY();

            Bullet bala = new Bullet(spawnX, spawnY, balaVelX, balaVelY, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }
    }

    public boolean checkCollision(Ball2 b) {
        // ... (Tu lógica de colisión no ha cambiado) ...
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            // rebote
            if (xVel == 0) xVel += b.getXSpeed() / 2;
            if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int) xVel / 2);
            xVel = -xVel;
            b.setXSpeed(-b.getXSpeed());

            if (yVel == 0) yVel += b.getySpeed() / 2;
            if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int) yVel / 2);
            yVel = -yVel;
            b.setySpeed(-b.getySpeed());

            //actualizar vidas y herir
            vidas--;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            if (vidas <= 0)
                destruida = true;
            return true;
        }
        return false;
    }
    
    // --- AÑADE ESTE MÉTODO COMPLETO DENTRO DE TU CLASE Nave4.java ---

    /**
     * Comprueba la colisión con un AgujeroNegro.
     * Si colisiona, teletransporta la nave y devuelve true.
     */
    public boolean checkCollision(AgujeroNegro portal) {

        // Solo chequear si no estamos heridos (invulnerables)
        if (!herido && portal.getArea().overlaps(spr.getBoundingRectangle())) {

            // ¡Teletransportar!
            // Busca una nueva posición X/Y aleatoria en la pantalla
            float newX = MathUtils.random(0, Gdx.graphics.getWidth() - spr.getWidth());
            float newY = MathUtils.random(0, Gdx.graphics.getHeight() - spr.getHeight());

            // Establece la nueva posición
            spr.setPosition(newX, newY);

            // Resetea la velocidad para que la nave no salga disparada
            xVel = 0;
            yVel = 0;

            // Opcional: Puedes añadir un sonido de "warp" aquí
            // soundWarp.play();

            // Devuelve true para que PantallaJuego sepa que hubo colisión
            return true; 
        }
        return false;
    }

    // --- (El resto de tus métodos get/set no cambian) ---

    public boolean estaDestruido() {
        return !herido && destruida;
    }

    public boolean estaHerido() {
        return herido;
    }

    public int getVidas() {
        return vidas;
    }

    public int getX() {
        return (int) spr.getX();
    }

    public int getY() {
        return (int) spr.getY();
    }

    public void setVidas(int vidas2) {
        vidas = vidas2;
    }
}