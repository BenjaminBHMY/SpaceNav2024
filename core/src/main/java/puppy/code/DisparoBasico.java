package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoBasico implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        // Usamos los métodos getter de Nave4
        float anguloRadianes = (float) Math.toRadians(nave.getRotation() + 90);
        float balaVel = 7.0f;
        
        float velX = (float) Math.cos(anguloRadianes) * balaVel;
        float velY = (float) Math.sin(anguloRadianes) * balaVel;
        
        float x = nave.getX() + nave.getWidth()/2 - 5;
        float y = nave.getY() + nave.getHeight()/2 - 5;

        Bullet bala = new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala());
        
        // Añadimos la bala a la lista del juego
        balas.add(bala);
    }
}