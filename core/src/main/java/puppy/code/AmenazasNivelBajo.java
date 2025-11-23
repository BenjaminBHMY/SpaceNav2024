package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.math.MathUtils;

public class AmenazasNivelBajo implements FabricaAmenazas {
    
    private GestorRecursos recursos;

    public AmenazasNivelBajo() {
        this.recursos = GestorRecursos.getInstance();
    }

    @Override
    public EntidadMovil crearAsteroide() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        // Asteroides lentos para nivel bajo
        float vx = MathUtils.random(-0.5f, 0.5f); 
        float vy = MathUtils.random(-0.5f, 0.5f);
        
        return new Ball2(x, y, vx, vy, recursos.getTxAsteroideAleatorio());
    }

    @Override
    public EntidadMovil crearEnemigo() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        
        // 10% probabilidad de ser grande (Jefe sorpresa)
        boolean esGrande = MathUtils.randomBoolean(0.1f); 
        
        Texture texturaAUsar;
        
        if (esGrande) {
            texturaAUsar = recursos.getTxOvni();
        } else {
            if (MathUtils.randomBoolean()) {
                texturaAUsar = recursos.getTxOvniSmall1();
            } else {
                texturaAUsar = recursos.getTxOvniSmall2();
            }
        }

        return new Ovni(x, y, esGrande, texturaAUsar);
    }
}