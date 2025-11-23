package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoCircular implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        
        float balaVel = 7.0f;
        
        float x = nave.getX() + nave.getWidth() / 2 - 5;
        float y = nave.getY() + nave.getHeight() / 2 - 5;

        // 8 direcciones (cada 45 grados)
        for (int i = 0; i < 8; i++) {
            float angulo = (float) Math.toRadians(i * 45);
            
            float velX = (float) Math.cos(angulo) * balaVel;
            float velY = (float) Math.sin(angulo) * balaVel;
            
            Bullet b = new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala());
            balas.add(b);
        }
    }
}