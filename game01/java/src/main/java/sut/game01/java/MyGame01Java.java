package sut.game01.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import sut.game01.core.MyGame01;

public class MyGame01Java {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new MyGame01());
  }
}