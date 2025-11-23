package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoX implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        float anguloBase = nave.getRotation() + 90;
        
        float x = nave.getX() + nave.getWidth() / 2 - 5;
        float y = nave.getY() + nave.getHeight() / 2 - 5;
        float balaVel = 7.0f;

        // Dispara en 4 diagonales: -45, +45, +135, +225 grados relativos
        int[] offsets = {-45, 45, 135, 225};

        for (int offset : offsets) {
            float angulo = (float) Math.toRadians(anguloBase + offset);
            
            float velX = (float) Math.cos(angulo) * balaVel;
            float velY = (float) Math.sin(angulo) * balaVel;
            
            Bullet b = new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala());
            balas.add(b);
        }
    }
}