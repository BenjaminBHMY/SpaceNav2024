package puppy.code;

import java.util.ArrayList;

public interface EstrategiaDisparo {
    // Actualizamos la firma para recibir la lista donde guardar las balas
    void disparar(ArrayList<Bullet> balas, Nave4 nave);
}