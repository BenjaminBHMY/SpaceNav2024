package puppy.code;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
/**
 *
 * @author Hp
 */
public class DisparoBasico implements EstrategiaDisparo
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave)
    {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        float anguloRadianes = (float) Math.toRadians(nave.getRotation() + 90);
        float balaVel = 7.0f;
        
        float velX = (float) Math.cos(anguloRadianes) * balaVel;
        float velY = (float) Math.sin(anguloRadianes) * balaVel;
        
        // Posición central
        float x = nave.getX() + nave.getWidth()/2 - 5;
        float y = nave.getY() + nave.getHeight()/2 - 5;
        
        balas.add(new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala()));
        
        // sonido
        GestorRecursos.getInstance().getSndDisparo().play();
        
        return balas;
    }  
}
