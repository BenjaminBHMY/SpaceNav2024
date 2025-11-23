package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoTriple implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        float anguloBase = nave.getRotation() + 90;
        float balaVel = 7.0f;
        
        float x = nave.getX() + nave.getWidth() / 2 - 5;
        float y = nave.getY() + nave.getHeight() / 2 - 5;

        // Disparar 3 balas con pequeña variación de ángulo (-10, 0, +10 grados)
        for (int i = -1; i <= 1; i++) {
            float angulo = (float) Math.toRadians(anguloBase + (i * 10));
            
            float velX = (float) Math.cos(angulo) * balaVel;
            float velY = (float) Math.sin(angulo) * balaVel;
            
            Bullet b = new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala());
            balas.add(b);
        }
    }
}