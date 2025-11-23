package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoAbanico implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        float anguloCentral = nave.getRotation() + 90;
        float balaVel = 7.0f;
        
        float x = nave.getX() + nave.getWidth() / 2 - 5;
        float y = nave.getY() + nave.getHeight() / 2 - 5;

        // Dispara 5 balas distribuidas en un arco de 60 grados (-30 a +30)
        for (int i = -2; i <= 2; i++) {
            float angulo = (float) Math.toRadians(anguloCentral + (i * 15));
            
            float velX = (float) Math.cos(angulo) * balaVel;
            float velY = (float) Math.sin(angulo) * balaVel;
            
            Bullet b = new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala());
            balas.add(b);
        }
    }
}