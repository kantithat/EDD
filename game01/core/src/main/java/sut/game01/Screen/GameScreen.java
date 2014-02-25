package sut.game01.Screen;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.Image;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.sprite.Buddy;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;

/**
 * Created by kantithat on 21/1/2557.
 */
public class GameScreen extends Screen {

    public static float M_PER_PIXEL = 1 / 26.66667f;
//    size of world
        private static int width  = 24;
        private static int height = 18;

    private World world;
    private DebugDrawBox2D debugDraw;

    private final ScreenStack ss;
    public Buddy buddy;
    private boolean showDebugDraw = true;

    public GameScreen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
        buddy.update(delta);
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
        buddy.paint(clock);
}

    @Override
    public void wasAdded(){
        super.wasAdded();
        Image bgImage = assets().getImage("images/bg.png");
        bgImage.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {
            }

            @Override
            public void onFailure(Throwable cause) {
            }
        });
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        ////////////// Create World ////////////////////
        createWorld();
        ////////////// Add Character ///////////////////
        buddy = new Buddy (world, 500f,250f);
        layer.add(buddy.layer());


        buddy.layer().addListener(new Pointer.Adapter() {
            public void onPointerEnd(Pointer.Event event) {

                Buddy.body.applyLinearImpulse(new Vec2(-50f, 0f), Buddy.body.getPosition());
            }
        });

        Image backImage = assets().getImage("images/Arrow/arrow_left.png");
        ImageLayer backLayer = graphics().createImageLayer(backImage);
        backLayer.setSize(60,60);
        backLayer.setOrigin(30,30);
        backLayer.setTranslation(50,50);
        layer.add(backLayer);
        backLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(ss.top());
            }
        });

    }

    public void createWorld() {
        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity, true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / GameScreen.M_PER_PIXEL),
                    (int) (height / GameScreen.M_PER_PIXEL));

            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_aabbBit | DebugDraw.e_shapeBit | DebugDraw.e_jointBit);
            debugDraw.setCamera(0, 0, 1f / GameScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

            }

            ////////////// Create Ground /////////////////
            Body ground = world.createBody(new BodyDef());
            PolygonShape groundShape = new PolygonShape();
            groundShape.setAsEdge(new Vec2(0f, 16f), new Vec2(24f,16f));
            ground.createFixture(groundShape, 0.0f);
            ///////////// Create Edge-- Left /////////////
            Body leftEdge = world.createBody(new BodyDef());
            PolygonShape leftEdgeShape = new PolygonShape();
            leftEdgeShape.setAsEdge(new Vec2(3f, 0f), new Vec2(3f,24f));
            ground.createFixture(leftEdgeShape, 0.0f);
            ///////////// Create Edge-- Right /////////////
            Body rightEdge = world.createBody(new BodyDef());
            PolygonShape rightEdgeShape = new PolygonShape();
            rightEdgeShape.setAsEdge(new Vec2(24f, 0f), new Vec2(24f,24f));
            ground.createFixture(rightEdgeShape, 0.0f);

    }
}
