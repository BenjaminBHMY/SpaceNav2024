package puppy.code;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
/**
 *
 * @author Hp
 */
public class DisparoAbanico implements EstrategiaDisparo
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave) 
    {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        float anguloCentral = nave.getRotation() + 90;
        float balaVel = 8.0f; // Balas rápidas
        
        // 5 balas con desviaciones -20, -10, 0, +10, +20 grados
        int[] offsets = {-20, -10, 0, 10, 20};

        for (int offset : offsets) {
            float anguloActual = (float) Math.toRadians(anguloCentral + offset);
            
            float velX = (float) Math.cos(anguloActual) * balaVel;
            float velY = (float) Math.sin(anguloActual) * balaVel;
            
            float x = nave.getX() + nave.getWidth()/2 - 5;
            float y = nave.getY() + nave.getHeight()/2 - 5;

            balas.add(new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala()));
        }

        GestorRecursos.getInstance().getSndDisparo().play();
        return balas;
    }
}
