package puppy.code;
import java.util.ArrayList;

public class DisparoDoble implements EstrategiaDisparo 
{
    @Override
    public ArrayList<Bullet> disparar(Nave4 nave) {
        ArrayList<Bullet> balas = new ArrayList<>();
        
        float anguloRadianes = (float) Math.toRadians(nave.getRotation() + 90);
        float velBala = 7.0f;
        
        float velX = (float) Math.cos(anguloRadianes) * velBala;
        float velY = (float) Math.sin(anguloRadianes) * velBala;

        // posición central de la nave
        float xCentro = nave.getX() + nave.getWidth()/2 - 5;
        float yCentro = nave.getY() + nave.getHeight()/2 - 5;
        
        // desplazamiento lateral (20 pixeles a la izq y der)
        float offsetX = (float) Math.cos(anguloRadianes + Math.PI/2) * 20; 
        float offsetY = (float) Math.sin(anguloRadianes + Math.PI/2) * 20;

        // Bala Izquierda
        balas.add(new Bullet(xCentro - offsetX, yCentro - offsetY, velX, velY, GestorRecursos.getInstance().getTxBala()));
        // Bala Derecha
        balas.add(new Bullet(xCentro + offsetX, yCentro + offsetY, velX, velY, GestorRecursos.getInstance().getTxBala()));

        GestorRecursos.getInstance().getSndDisparo().play();
        return balas;
    }
}
