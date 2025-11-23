package puppy.code;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
/**
 *
 * @author Hp
 */
public class DisparoCircular implements EstrategiaDisparo
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave) 
    {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        // Disparamos una bala cada 45 grados (360 / 8 = 45)
        for (int angulo = 0; angulo < 360; angulo += 45) {
            float radianes = (float) Math.toRadians(angulo);
            float balaVel = 6.0f; // un poquito mas lento

            float velX = (float) Math.cos(radianes) * balaVel;
            float velY = (float) Math.sin(radianes) * balaVel;
            
            // Desde la nave
            float x = nave.getX() + nave.getWidth()/2 - 5;
            float y = nave.getY() + nave.getHeight()/2 - 5;

            balas.add(new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala()));
        }

        GestorRecursos.getInstance().getSndDisparo().play();
        return balas;
    }
}
