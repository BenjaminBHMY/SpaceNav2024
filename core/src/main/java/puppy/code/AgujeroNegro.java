package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AgujeroNegro extends EntidadMovil {

    private float velocidadRotacionPropia;

    public AgujeroNegro(float x, float y, float xSpeed, float ySpeed, Texture tx) {
        super(x, y, xSpeed, ySpeed, tx);
        this.spr.setSize(40, 40); 
        this.spr.setOriginCenter();
        this.velocidadRotacionPropia = 3.0f;
    }

    @Override
    public void update() {
        spr.translate(xSpeed, ySpeed);

        spr.rotate(velocidadRotacionPropia);

        if (spr.getX() > Gdx.graphics.getWidth() || spr.getX() + spr.getWidth() < 0 ||
            spr.getY() > Gdx.graphics.getHeight() || spr.getY() + spr.getHeight() < 0) {
            
            this.destroyed = true;
        }
    }
}