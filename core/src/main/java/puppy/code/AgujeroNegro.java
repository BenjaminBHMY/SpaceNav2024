package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// --- MODIFICADO: Ahora 'hereda' de EntidadMovil ---
public class AgujeroNegro extends EntidadMovil {

    // --- MODIFICADO: El constructor llama a 'super' ---
    public AgujeroNegro(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);
        
        // --- IMPORTANTE: Ponemos el setSize DESPUÉS de 'super' ---
        // Esta es la lógica única del constructor de AgujeroNegro.
        spr.setSize(40, 40); 
    }

    // --- MODIFICADO: Se añade @Override ---
    // Este es el método abstracto que ESTÁBAMOS OBLIGADOS a implementar.
    @Override
    public void update() {
        // Mover el agujero negro
        spr.translate(xSpeed, ySpeed);

        // Lógica de destrucción en los bordes
        float x = spr.getX();
        float y = spr.getY();
        float anchoPantalla = Gdx.graphics.getWidth();
        float altoPantalla = Gdx.graphics.getHeight();
        
        if (x > anchoPantalla || x + spr.getWidth() < 0 ||
            y > altoPantalla || y + spr.getHeight() < 0) {
            
            destroyed = true; // 'destroyed' es heredado de EntidadMovil
        }
    }
    
    // --- MÉTODOS ELIMINADOS ---
    // Ya no necesitamos 'draw()', 'getArea()', 'isDestroyed()', 'getX()' ni 'getY()'
    // porque los HEREDAMOS de EntidadMovil.
}