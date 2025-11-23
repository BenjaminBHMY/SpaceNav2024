package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class AmenazasNivelAlto implements FabricaAmenazas {
    
    private GestorRecursos recursos;

    public AmenazasNivelAlto() {
        this.recursos = GestorRecursos.getInstance();
    }

    @Override
    public EntidadMovil crearAsteroide() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        float vx = MathUtils.random(-4f, 4f); 
        float vy = MathUtils.random(-4f, 4f);
        
        return new Ball2(x, y, vx, vy, recursos.getTxAsteroide());
    }

    @Override
    public EntidadMovil crearEnemigo() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        boolean esGrande = MathUtils.randomBoolean(0.6f); 
        
        return new Ovni(x, y, esGrande, recursos.getTxOvni());
    }
}