package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture; 
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
        // Asteroides rápidos para nivel alto
        float vx = MathUtils.random(-4f, 4f); 
        float vy = MathUtils.random(-4f, 4f);
        
        return new Ball2(x, y, vx, vy, recursos.getTxAsteroideAleatorio());
    }

    @Override
    public EntidadMovil crearEnemigo() {
        float x = MathUtils.random(0, Gdx.graphics.getWidth());
        float y = MathUtils.random(0, Gdx.graphics.getHeight());
        
        // 60% probabilidad de ser grande
        boolean esGrande = MathUtils.randomBoolean(0.6f); 
        
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