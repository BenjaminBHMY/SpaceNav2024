package puppy.code;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
/**
 *
 * @author Hp
 */
public class DisparoTriple implements EstrategiaDisparo
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave) {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        float anguloBase = nave.getRotation() + 90;
        float balaVel = 7.0f;
        float x = nave.getX() + nave.getWidth()/2 - 5;
        float y = nave.getY() + nave.getHeight()/2 - 5;

        // Generamos 3 balas con ángulos distintos (-15, 0, +15 grados)
        for (int i = -1; i <= 1; i++) {
            float anguloActual = (float) Math.toRadians(anguloBase + (i * 15));
            
            float velX = (float) Math.cos(anguloActual) * balaVel;
            float velY = (float) Math.sin(anguloActual) * balaVel;
            
            balas.add(new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala()));
        }

        GestorRecursos.getInstance().getSndDisparo().play();
        return balas;
    }
}
