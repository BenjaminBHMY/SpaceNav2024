package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Ball2 {
    
    // --- MODIFICADO: Cambiado a float para movimiento suave ---
    private float xSpeed;
    private float ySpeed;
    private Sprite spr;

    // --- MODIFICADO: Constructor actualizado para floats ---
    // He quitado 'size' ya que no se usaba para el tamaño del sprite
    public Ball2(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        
        // --- NUEVO: Buena práctica para futuras rotaciones de asteroides ---
        spr.setOriginCenter(); 
        
        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
        
        // La validación de bordes al crear el asteroide es mejor
        // hacerla en la clase PantallaJuego, para asegurar que no
        // aparezca encima del jugador.
    }

    public void update() {
        // --- MODIFICADO: Actualizar la posición del Sprite directamente ---
        // .translate() mueve el sprite basado en su estado actual
        spr.translate(xSpeed, ySpeed);

        // --- MODIFICADO: Reemplazar la lógica de rebote por la de "Screen Wrap" ---
        
        float anchoPantalla = Gdx.graphics.getWidth();
        float altoPantalla = Gdx.graphics.getHeight();
        
        // Obtenemos la posición actual del sprite
        float x = spr.getX();
        float y = spr.getY();

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
    }
     
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        spr.draw(batch);
    }
     
    // --- MODIFICADO: Lógica de colisión actualizada para floats ---
    // (La lógica interna de rebote entre asteroides sigue igual)
    public void checkCollision(Ball2 b2) {
        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
            // rebote
            if (getXSpeed() == 0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
            if (b2.getXSpeed() == 0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
            setXSpeed(-getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());
            
            if (getySpeed() == 0) setySpeed(getySpeed() + b2.getySpeed()/2);
            if (b2.getySpeed() == 0) b2.setySpeed(b2.getySpeed() + getySpeed()/2);
            setySpeed(-getySpeed());
            b2.setySpeed(-b2.getySpeed()); 
        }
    }

    // --- MODIFICADO: Getters y Setters ahora usan float ---
    public float getXSpeed() {
        return xSpeed;
    }
    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }
    public float getySpeed() {
        return ySpeed;
    }
    public void setySpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }
    
    // --- NUEVO: Getters para la posición (buena práctica) ---
    public float getX() {
        return spr.getX();
    }
    public float getY() {
        return spr.getY();
    }
}