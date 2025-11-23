package puppy.code;
import java.util.ArrayList;

public class DisparoLinea implements EstrategiaDisparo 
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave) {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        float anguloFrontal = nave.getRotation() + 90; // frente
        float anguloTrasero = nave.getRotation() + 270; // atrás (90 + 180)
        
        float vel = 7.0f;
        float x = nave.getX() + nave.getWidth()/2 - 5;
        float y = nave.getY() + nave.getHeight()/2 - 5;

        // Bala Frontal
        float rad1 = (float) Math.toRadians(anguloFrontal);
        balas.add(new Bullet(x, y, (float)Math.cos(rad1)*vel, (float)Math.sin(rad1)*vel, GestorRecursos.getInstance().getTxBala()));
        
        // Bala Trasera
        float rad2 = (float) Math.toRadians(anguloTrasero);
        balas.add(new Bullet(x, y, (float)Math.cos(rad2)*vel, (float)Math.sin(rad2)*vel, GestorRecursos.getInstance().getTxBala()));

        GestorRecursos.getInstance().getSndDisparo().play();
        return balas;
    }
}
