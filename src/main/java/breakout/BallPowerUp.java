package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class BallPowerUp extends PowerUp{
  public int type = 1;

  public BallPowerUp(double[] coordinates) {
    super(coordinates);
  }

  public Ball affectGame() {
    Ball newBall = new Ball(10, Color.BLUE,new double[]{200,200});
    return newBall;
  }
}
