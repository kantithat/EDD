package sut.game01.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import sut.game01.core.MyGame01;

public class MyGame01Activity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new MyGame01());
  }
}
