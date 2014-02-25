package sut.game01.sprite;

import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.Screen.GameScreen;

/**
 * Created by kantithat on 28/1/2557.
 */
public class Buddy {


    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public static Body body;

    public Layer layer() {
        return sprite.layer();
    }


    public int time, hp = 100;

    public enum State{
        IDLE, HURT, DIE
    };

    private State statePig = State.IDLE;

    private int e = 0;
    private int offset = 0;

    public Buddy(final World world, final float x_px, final float y_px){

      this.sprite = SpriteLoader.getSprite("images/Sprite/pig01.json");
      this.sprite.addCallback(new Callback<Sprite>() {

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px,y_px);

                body = initPhysicsBody(world, GameScreen.M_PER_PIXEL * x_px, GameScreen.M_PER_PIXEL * y_px);
                body.applyForce(new Vec2(0f, -1000f), body.getPosition());

                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!!", cause);
            }
        });

        sprite.layer().addListener(new Pointer.Listener() {
            @Override
            public void onPointerStart(Pointer.Event event) {

            }

            @Override
            public void onPointerEnd(Pointer.Event event) {
            }

            @Override
            public void onPointerDrag(Pointer.Event event) {

            }

            @Override
            public void onPointerCancel(Pointer.Event event) {

            }
        });

    }

    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL) ,
                body.getPosition().y / GameScreen.M_PER_PIXEL);
    }

    public void update(int delta){
        if (!hasLoaded)   return;
        e += delta;
        time += delta;

        if(hp == 100 && time >= 1000)
            hp = 50;
        else if(hp == 50 && time >= 2000)
            hp = 0;

        if(e > 200){
            switch (statePig){
                case IDLE:  offset = 0;
                    if(hp == 50)
                        statePig = State.HURT;
                    break;
                case HURT:  offset = 3;
                    if(hp == 0)
                        statePig = State.DIE;
                    break;
                case DIE:  offset = 6;
                    break;
            }
            if(spriteIndex == 8)
                spriteIndex = spriteIndex + 0;
            else{
            spriteIndex = offset + ((spriteIndex + 1) % 3);
            sprite.setSprite(spriteIndex);
            }
            e = 0;
        }
    }

    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.layer().width() * GameScreen.M_PER_PIXEL / 2,
                        sprite.layer().height() * GameScreen.M_PER_PIXEL / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.45f;
        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y),0f);
        return body;
    }

}