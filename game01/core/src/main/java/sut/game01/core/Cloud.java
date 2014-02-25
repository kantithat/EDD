package sut.game01.core;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.GroupLayer;
import playn.core.util.Callback;
import static playn.core.PlayN.*;

public class Cloud{
  public static String IMAGE = "images/cloud/Cloud02.png";
  private ImageLayer layer;
  private int elapsed;
  private final float angVel = ( tick() % 10 - 5) / 1000f;

    public Cloud(final GroupLayer cloudLayer, final float x, final float y){
      Image image = assets().getImage(IMAGE);
      layer = graphics().createImageLayer(image);

      image.addCallback(new Callback<Image>(){
          @Override
          public void onSuccess(Image image){
              //layer.setOrigin(image.width() / 2f, image.height() / 2f);
              layer.setOrigin(50,50);
              layer.setSize(100,100);
              layer.setTranslation(x, y);
              cloudLayer.add(layer);
          }

          @Override
          public void onFailure(Throwable err) {
            log().error("Error loading image!", err);
          }
      });
    }

  public void update(int delta) {
    elapsed += delta;
  }

  public void paint(float alpha) {
    float now = elapsed + alpha * MyGame01.UPDATE_RATE;
    layer.setRotation(now * angVel);
  }

}







// public class MyGame01 extends Game.Default {

//   public MyGame01() {
//     super(33); // call update every 33ms (30 times per second)
//   }

//   ImageLayer cloudLayer;
//   ImageLayer bgLayer;
//   ImageLayer ballLayer;
//   int cloudMove = 0;
//   int cloudStatus = 0;
//   int ballMove = 0;
//   int ballStatus = 0;
//   float ballRotate = 0;

//   @Override
//   public void init() {
//     // create and add background image layer
//     Image bgImage = assets().getImage("images/bg/bg02.png");
//     bgLayer = graphics().createImageLayer(bgImage);
//     graphics().rootLayer().add(bgLayer);

//     Image cloudImage = assets().getImage("images/cloud/Cloud02.png");
//     cloudLayer = graphics().createImageLayer(cloudImage);
//     cloudLayer.setSize(100,100);
//     graphics().rootLayer().add(cloudLayer);

//     Image ballImage = assets().getImage("images/ball/Ball01.png");
//     ballLayer = graphics().createImageLayer(ballImage);
//     ballLayer.setSize(50,50);
//     ballLayer.setTranslation(25,410);
//     ballLayer.setOrigin(25,25);
//     graphics().rootLayer().add(ballLayer);

//   }

//   @Override
//   public void update(int delta) {
//     // ----------------- cloud -------------------------
//     if(cloudMove <= 0)      cloudStatus = 0;
//     else if(cloudMove >= 540)   cloudStatus = 1;

//     if(cloudStatus == 0){
//       cloudMove += 2;
//     }
//     else if(cloudStatus == 1){
//       cloudMove -= 2;
//     }

//     //------------------- ball -----------------------
//     if(ballMove <= 25)      ballStatus = 0;
//     else if(ballMove >= 615)   ballStatus = 1;

//     if(ballStatus == 0){
//       ballMove += 3;
//       ballRotate = ballRotate + (float)0.3;
//     }
//     else if(ballStatus == 1){
//       ballMove -= 3;
//       ballRotate = ballRotate - (float)0.3;
//     }

    
//   }

  

//   @Override
//   public void paint(float alpha) {
//     // the background automatically paints itself, so no need to do anything here!
    
//     cloudLayer.setTranslation(cloudMove,(5*ballRotate));
//     ballLayer.setRotation(ballRotate);
//     ballLayer.setTranslation(ballMove,410);
    
//   }
// }
