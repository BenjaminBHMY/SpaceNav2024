package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoDoble implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        float anguloRadianes = (float) Math.toRadians(nave.getRotation() + 90);
        float balaVel = 7.0f;
        
        float velX = (float) Math.cos(anguloRadianes) * balaVel;
        float velY = (float) Math.sin(anguloRadianes) * balaVel;
        
        // Offset para que salgan dos balas paralelas (izquierda y derecha del centro)
        float offsetX = (float) Math.cos(anguloRadianes + Math.PI / 2) * 10;
        float offsetY = (float) Math.sin(anguloRadianes + Math.PI / 2) * 10;

        float xCentro = nave.getX() + nave.getWidth() / 2 - 5;
        float yCentro = nave.getY() + nave.getHeight() / 2 - 5;

        // Bala 1
        Bullet b1 = new Bullet(xCentro + offsetX, yCentro + offsetY, velX, velY, GestorRecursos.getInstance().getTxBala());
        balas.add(b1);
        
        // Bala 2
        Bullet b2 = new Bullet(xCentro - offsetX, yCentro - offsetY, velX, velY, GestorRecursos.getInstance().getTxBala());
        balas.add(b2);
    }
}