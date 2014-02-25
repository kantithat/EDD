package sut.game01.core;

import sut.game01.Screen.HomeScreen;
import sut.game01.sprite.Buddy;
import tripleplay.game.Screen;
import playn.core.Game;
import playn.core.ImageLayer;
import playn.core.GroupLayer;

import java.util.ArrayList;
import java.util.List;

import playn.core.util.Clock;
import tripleplay.game.ScreenStack;


public class MyGame01 extends Game.Default {

  GroupLayer cloudLayer;
  List<Cloud> clouds = new ArrayList<Cloud>(0);
  
  public static final int UPDATE_RATE = 25;

    private final ScreenStack ss = new ScreenStack();
    private final Clock.Source clock = new Clock.Source(33);


    public MyGame01() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {
      ss.push(new HomeScreen(ss));
  }

  @Override
  public void update(int delta) {
      ss.update(delta);
  }

  

  @Override
  public void paint(float alpha) {
      clock.paint(alpha);
      ss.paint(clock);
  }


}
