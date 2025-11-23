package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

public class DisparoLinea implements EstrategiaDisparo {

    @Override
    public void disparar(ArrayList<Bullet> balas, Nave4 nave) {
        float anguloFrontal = (float) Math.toRadians(nave.getRotation() + 90);
        float anguloTrasero = (float) Math.toRadians(nave.getRotation() + 270);
        float balaVel = 7.0f;
        
        float x = nave.getX() + nave.getWidth() / 2 - 5;
        float y = nave.getY() + nave.getHeight() / 2 - 5;

        // Bala Frontal
        float velX1 = (float) Math.cos(anguloFrontal) * balaVel;
        float velY1 = (float) Math.sin(anguloFrontal) * balaVel;
        balas.add(new Bullet(x, y, velX1, velY1, GestorRecursos.getInstance().getTxBala()));
        
        // Bala Trasera
        float velX2 = (float) Math.cos(anguloTrasero) * balaVel;
        float velY2 = (float) Math.sin(anguloTrasero) * balaVel;
        balas.add(new Bullet(x, y, velX2, velY2, GestorRecursos.getInstance().getTxBala()));
    }
}