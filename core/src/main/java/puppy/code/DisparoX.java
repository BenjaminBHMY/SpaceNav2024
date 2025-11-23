package puppy.code;
import java.util.ArrayList;

public class DisparoX implements EstrategiaDisparo 
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave) {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        float anguloBase = nave.getRotation() + 90;
        float vel = 6.0f; // mas lento
        float x = nave.getX() + nave.getWidth()/2 - 5;
        float y = nave.getY() + nave.getHeight()/2 - 5;

        // en 0, 90, 180 y 270 grados a la nave
        int[] direcciones = {0, 90, 180, 270};

        for (int dir : direcciones) {
            float rad = (float) Math.toRadians(anguloBase + dir);
            float velX = (float) Math.cos(rad) * vel;
            float velY = (float) Math.sin(rad) * vel;
            
            balas.add(new Bullet(x, y, velX, velY, GestorRecursos.getInstance().getTxBala()));
        }

        GestorRecursos.getInstance().getSndDisparo().play();
        return balas;
    }
}
