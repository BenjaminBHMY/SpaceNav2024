package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class AmenazasNivelBajo implements FabricaAmenazas {
    
    private GestorRecursos recursos;

    public AmenazasNivelBajo() {
        this.recursos = GestorRecursos.getInstance();
    }

    @Override
    public EntidadMovil crearAsteroide() {
        // Asteroide MUY lento para empezar fácil
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        
        float vx = MathUtils.random(-0.5f, 0.5f); 
        float vy = MathUtils.random(-0.5f, 0.5f);
        
        return new Ball2(x, y, vx, vy, recursos.getTxAsteroide());
    }

    @Override
    public EntidadMovil crearEnemigo() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        return new Ovni(x, y, false, recursos.getTxOvni());
    }
}