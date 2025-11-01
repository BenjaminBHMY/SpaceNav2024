package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// --- MODIFICADO: Ahora 'hereda' de EntidadMovil ---
public class Bullet extends EntidadMovil {

    // --- MODIFICADO: El constructor ahora es más simple ---
    // Llama al constructor de la clase 'EntidadMovil' (el 'super')
    // para que él configure el sprite, la posición y la velocidad.
    public Bullet(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);
    }
    
    // --- MODIFICADO: Se añade @Override ---
    // Este es el método abstracto que ESTÁBAMOS OBLIGADOS a implementar.
    // La lógica de movimiento es la misma que ya tenías.
    @Override
    public void update() {
        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
        
        // Lógica de destrucción en los bordes
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true; // 'destroyed' es heredado de EntidadMovil
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true; // 'destroyed' es heredado de EntidadMovil
        }
    }
     
    // --- MÉTODOS ELIMINADOS ---
    // Ya no necesitamos 'draw()', 'getArea()' ni 'isDestroyed()'
    // porque los HEREDAMOS de EntidadMovil.
    
    /**
     * Esta lógica de colisión es ÚNICA de la bala,
     * así que se queda aquí.
     */
    public boolean checkCollision(Ball2 b2) {
        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
            // Se destruyen ambos
            this.destroyed = true; // Marcamos la bala para ser eliminada
            return true;
        }
        return false;
    }
}